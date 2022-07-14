package app.controller;

import app.response.ExceptionResponse;
import app.response.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import app.entity.User;
import app.security.TokenService;
import app.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthenticateTest
{
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    TokenService tokenService;
    @Mock
    UserRepository userRepository;
    User user;
    Authenticate authenticate;

    @BeforeEach
    void setUp()
    {
        authenticate=new Authenticate(authenticationManager,tokenService,userRepository);
        user=new User();
        user.setUsername("username");
        user.setPassword("password");
    }
    @Test
    void testAuthenticate_successful()
    {
        String token="jwt.token";
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenAnswer(i->i.getArgument(0,Authentication.class));
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(tokenService.create(user)).thenReturn(token);
        ResponseEntity<?> responseEntity=authenticate.authenticate(user);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(token,((TokenResponse)responseEntity.getBody()).getToken());
    }
    @Test
    void testAuthenticate_throwsException()
    {
        String exceptionMessage="Exception";
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenThrow(new BadCredentialsException(exceptionMessage));
        ResponseEntity<?> responseEntity=authenticate.authenticate(user);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.UNAUTHORIZED);
        assertEquals(((ExceptionResponse)responseEntity.getBody()).getException(),exceptionMessage);
    }
}