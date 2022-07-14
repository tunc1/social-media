package app.controller;

import app.entity.User;
import app.response.MessageResponse;
import app.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterTest
{
    Register register;
    @Mock
    UserService userService;
    @Test
    void register()
    {
        register=new Register(userService);
        User user=new User();
        MessageResponse messageResponse=register.register(user);
        verify(userService).register(user);
        assertEquals("Successful",messageResponse.getMessage());
    }
}