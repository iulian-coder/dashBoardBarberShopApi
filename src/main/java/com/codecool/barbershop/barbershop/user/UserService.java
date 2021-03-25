package com.codecool.barbershop.barbershop.user;


import com.codecool.barbershop.barbershop.exception.BadRequestException;
import com.codecool.barbershop.barbershop.exception.RecordNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserDetailsById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("User id " + id)
        );
        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).orElseThrow(() ->
                new UsernameNotFoundException("User " + s));
        return UserPrincipal.create(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User id" + userId));
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }
}