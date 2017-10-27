package com.sciencom.osd.controller;

import com.sciencom.osd.entity.Response;
import com.sciencom.osd.entity.User;
import com.sciencom.osd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user){
        Response created = userService.register(user);
        return getHttpResponse(created);
    }

    @PostMapping("/activation")
    public ResponseEntity activation(@RequestBody Map map){
        return getHttpResponse(userService.activation(map));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        Response status = userService.login(user);
        return getHttpResponse(status);
    }
}

