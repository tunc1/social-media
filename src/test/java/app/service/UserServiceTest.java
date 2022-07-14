package app.service;

import app.entity.User;
import app.exception.EmailAlreadyInUseException;
import app.exception.UsernameAlreadyInUseException;
import app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    UserService userService;
    @BeforeEach
    void setUp()
    {
        userService=new UserService(userRepository,passwordEncoder);
    }
    @Test
    void register_usernameAlreadyInUse()
    {
        when(userRepository.existsByUsername(any())).thenReturn(true);
        User user=new User();
        user.setUsername("user");
        UsernameAlreadyInUseException exception=assertThrows(UsernameAlreadyInUseException.class,()->userService.register(user));
        assertEquals("This username is already in use",exception.getMessage());
    }
    @Test
    void register_emailAlreadyInUse()
    {
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(true);
        User user=new User();
        user.setUsername("user");
        user.setEmail("user@a.com");
        EmailAlreadyInUseException exception=assertThrows(EmailAlreadyInUseException.class,()->userService.register(user));
        assertEquals("This email is already in use",exception.getMessage());
    }
    @Test
    void register()
    {
        String encodedPassword="encodedPassword";
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn(encodedPassword);
        User user=new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setEmail("user@a.com");
        userService.register(user);
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isEnabled());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertEquals(encodedPassword,user.getPassword());
        verify(userRepository).save(user);
    }
}