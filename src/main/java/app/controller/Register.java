package app.controller;

import app.entity.User;
import app.repository.UserRepository;
import app.response.ExceptionResponse;
import app.response.MessageResponse;
import app.response.TokenResponse;
import app.security.TokenService;
import app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class Register
{
    private UserService userService;
    public Register(UserService userService)
    {
        this.userService=userService;
    }
    @PostMapping
    public MessageResponse register(@RequestBody User user)
    {
        userService.register(user);
        return new MessageResponse("Successful");
    }
}