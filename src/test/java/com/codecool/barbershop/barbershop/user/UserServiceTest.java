package com.codecool.barbershop.barbershop.user;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;



@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {


    @Autowired
    private UserService service;

    @MockBean
    private UserRepository mockRepository;


    @Test
    void saveUser() {
        User user = new User(
                1L,
                "jon",
                "jon@gmail.com",
                "http//image.com",
                false, "password",
                AuthProvider.local,
                "1234-Uid-=provider");
        when(mockRepository.save(user)).thenReturn(user);
        assertEquals(user, service.saveUser(user));

    }

    @Test
    void findUserById() {
        Long idTest = 1L;

        User user = new User(
                1L,
                "jon",
                "jon@gmail.com",
                "http//image.com",
                false,
                "password",
                AuthProvider.local,
                "1234-Uid-=provider");

        when(mockRepository.findById(idTest)).thenReturn(Optional.of(user));
        assertEquals(user, service.findUserById(idTest));
    }


    @Test
    void loadUserByUsername() {

    }

    @Test
    void existsByEmail() {
        String emailTest = "jon@gmail.com";

        User user = new User(
                1L,
                "jon",
                "jon@gmail.com",
                "http//image.com",
                false, "password",
                AuthProvider.local,
                "1234-Uid-=provider");

        when(mockRepository.existsByEmail(emailTest)).thenReturn(true);
        assertTrue(service.existsByEmail(user.getEmail()));

    }


    @Test
    void deleteUserById() {
        Long testId = 1L;
        service.deleteUserById(testId);
        verify(mockRepository, times(1)).deleteById(testId);

    }

    @Test
    void findUserByEmail() {
        String emailTest = "jon@gmail.com";

        User user = new User(
                1L,
                "jon",
                "jon@gmail.com",
                "http//image.com",
                false, "password",
                AuthProvider.local,
                "1234-Uid-=provider");

        when(mockRepository.findByEmail(emailTest)).thenReturn(Optional.of(user));
        assertEquals(Optional.of(user), service.findUserByEmail(emailTest));

    }
}