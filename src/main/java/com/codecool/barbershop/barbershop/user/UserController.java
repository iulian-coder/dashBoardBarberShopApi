package com.codecool.barbershop.barbershop.user;

import com.codecool.barbershop.barbershop.exception.RecordNotFoundException;
import com.codecool.barbershop.barbershop.security.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    
    private final UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RecordNotFoundException("User id" + userPrincipal.getId()));
    }
}
