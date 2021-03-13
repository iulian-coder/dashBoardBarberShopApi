package com.codecool.barbershop.barbershop.user;

import com.codecool.barbershop.barbershop.security.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findUserById(userPrincipal.getId());
    }

    @PostMapping("/user/me")
    public ResponseEntity<?> deleteUser(@CurrentUser UserPrincipal userPrincipal) {

        userService.deleteUserById(userPrincipal.getId());
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
