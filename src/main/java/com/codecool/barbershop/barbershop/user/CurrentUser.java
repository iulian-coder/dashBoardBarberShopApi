package com.codecool.barbershop.barbershop.user;



import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
/*
This is a meta-annotation that can be used to inject
the currently authenticated user principal in the controllers
DOCS: https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/method/annotation/AuthenticationPrincipalArgumentResolver.html
 */