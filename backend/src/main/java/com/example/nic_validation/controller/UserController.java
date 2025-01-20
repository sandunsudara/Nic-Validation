package com.example.nic_validation.controller;


import com.example.nic_validation.model.Respond;
import com.example.nic_validation.model.User;
import com.example.nic_validation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Respond<Object> Login(@RequestBody User user) {
        return userService.login(user);
    }


}
