package com.codecool.barbershop.barbershop.user;


import com.codecool.barbershop.barbershop.exception.RecordNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService{

    private final UserRepository userRepository;



    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("User id" + id)
        );
        return UserPrincipal.create(user);
    }
}