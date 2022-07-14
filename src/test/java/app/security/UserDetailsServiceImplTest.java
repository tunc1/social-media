package app.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import app.entity.User;
import app.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest
{
    @Mock
    UserRepository userRepository;
    UserDetailsService userDetailsService;

    @BeforeEach
    void setUp()
    {
        userDetailsService=new UserDetailsServiceImpl(userRepository);
    }
    @Test
    void testLoadUserByUsername_returnsUserDetails()
    {
        User user=new User();
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        UserDetails userDetails=userDetailsService.loadUserByUsername("username");
        assertEquals(user,userDetails);
    }
    @Test
    void testLoadUserByUsername_throwsUsernameNotFoundException()
    {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        UsernameNotFoundException exception=assertThrows(UsernameNotFoundException.class,()->userDetailsService.loadUserByUsername("username"));
        assertEquals("No User Found by this Username",exception.getMessage());
    }
}