package app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import app.entity.User;
import app.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenFilterTest
{
    @Mock
    TokenService tokenService;
    @Mock
    UserRepository userRepository;
    @Mock
    HttpServletRequest request;
    TokenFilter tokenFilter;
    @BeforeEach
    void setUp()
    {
        tokenFilter=new TokenFilter(tokenService,userRepository);
    }
    @Test
    void testDoFilterInternal_noAuthorizationHeader() throws ServletException,IOException
    {
        when(request.getHeader(eq("Authorization"))).thenReturn(null);
        assertDoesNotThrow(()->tokenFilter.doFilterInternal(request,null,null));
    }
    @Test
    void testDoFilterInternal_noBearer()
    {
        when(request.getHeader(eq("Authorization"))).thenReturn("Token");
        assertDoesNotThrow(()->tokenFilter.doFilterInternal(request,null,null));
    }
    @Test
    void testDoFilterInternal_notValid() throws ServletException,IOException
    {
        when(request.getHeader(eq("Authorization"))).thenReturn("Bearer Token");
        when(tokenService.validate(anyString())).thenReturn(false);
        tokenFilter.doFilterInternal(request,null,null);
        verify(tokenService,times(0)).get(anyString(),eq("username"));
    }
    @Test
    void testDoFilterInternal_userNotExists() throws ServletException,IOException
    {
        when(request.getHeader(eq("Authorization"))).thenReturn("Bearer Token");
        when(tokenService.validate(anyString())).thenReturn(true);
        when(tokenService.get(anyString(),eq("username"))).thenReturn("username");
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        tokenFilter.doFilterInternal(request,null,null);
        verify(userRepository,times(0)).findByUsername(anyString());
    }
    @Test
    void testDoFilterInternal_userExists() throws ServletException,IOException
    {
        User user=new User();
        when(request.getHeader(eq("Authorization"))).thenReturn("Bearer Token");
        when(tokenService.validate(anyString())).thenReturn(true);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(tokenService.get(anyString(),eq("username"))).thenReturn("username");
        tokenFilter.doFilterInternal(request,null,null);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        assertEquals(authentication.getPrincipal(),user);
    }
}