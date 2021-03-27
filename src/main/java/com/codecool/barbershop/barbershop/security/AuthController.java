package com.codecool.barbershop.barbershop.security;

import com.codecool.barbershop.barbershop.exception.BadRequestException;
import com.codecool.barbershop.barbershop.security.payload.*;
import com.codecool.barbershop.barbershop.user.AuthProvider;
import com.codecool.barbershop.barbershop.user.User;
import com.codecool.barbershop.barbershop.user.UserPrincipal;
import com.codecool.barbershop.barbershop.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenService jwtTokenServices;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
//        Create token
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = Long.toString(userPrincipal.getId());

        String accessToken = jwtTokenServices.createToken(userId);

        return ResponseEntity.ok(new LoginResponse(accessToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
//            TODO user already exists
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail().toLowerCase(Locale.ROOT));
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);

        return ResponseEntity.ok(new SignupResponse(true, "User registered successfully"));
    }

}
