package com.codecool.barbershop.barbershop.configuration;

import com.codecool.barbershop.barbershop.security.*;
import com.codecool.barbershop.barbershop.user.UserOAuth2Service;
import com.codecool.barbershop.barbershop.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.codecool.barbershop.barbershop.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.codecool.barbershop.barbershop.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.codecool.barbershop.barbershop.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

/*   @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled=true)
     The prePostEnabled property enables Spring Security pre/post annotations
     The securedEnabled property determines if the @Secured annotation should be enabled
     The jsr250Enabled property allows us to use the @RoleAllowed annotation
*/
//  TUTORIAL https://www.callicoder.com/spring-boot-security-oauth2-social-login-part-1/

    private final UserService userService;
    private final UserOAuth2Service userOAuth2Service;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. Since the service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                    .antMatchers("/","/error").permitAll()
                    .antMatchers("/auth/**", "/oauth2/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(userOAuth2Service)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);
        // Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
/*
OAuth2 Login Flow
The OAuth2 login flow will be initiated by the frontend client by sending the user
to the endpoint http://localhost:8080/oauth2/authorize/{provider}?redirect_uri=<redirect_uri_after_login>.
The provider path parameter is one of google, facebook, The redirect_uri is the URI to which the user will be redirected
once the authentication with the OAuth2 provider is successful.

On receiving the authorization request, Spring Security’s OAuth2 client will redirect the user to the
AuthorizationUrl of the supplied provider.All the state related to the authorization request is saved using the
authorizationRequestRepository specified in the SecurityConfig.

The user now allows/denies permission to the app on the provider’s page. If the user allows permission to the app,
the provider will redirect the user to the callback url http://localhost:8080/oauth2/callback/{provider}
with an authorization code. If the user denies the permission, he will be redirected to the same callbackUrl but with an error.

If the OAuth2 callback results in an error, Spring security will invoke the oAuth2AuthenticationFailureHandler
specified in the above SecurityConfig.

If the OAuth2 callback is successful and it contains the authorization code, Spring Security will exchange the
authorization_code for an access_token and invoke the UserOAuth2Service specified in the above SecurityConfig.

The UserOAuth2Service retrieves the details of the authenticated user and creates a new entry in the database or
updates the existing entry with the same email.

Finally, the oAuth2AuthenticationSuccessHandler is invoked. It creates a JWT authentication token for the user
and sends the user to the redirect_uri along with the JWT token in a query string.

 */