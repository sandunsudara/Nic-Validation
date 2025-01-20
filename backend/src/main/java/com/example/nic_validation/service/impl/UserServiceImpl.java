package com.example.nic_validation.service.impl;

import com.example.nic_validation.entity.UserEntity;
import com.example.nic_validation.model.Respond;
import com.example.nic_validation.model.User;
import com.example.nic_validation.repository.UserRepo;
import com.example.nic_validation.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Respond<Object> login(User user) {
        Optional<UserEntity> getUser = userRepository.findByUsername(user.getUsername());

        if (getUser.isEmpty()) {
            return new Respond<>(false, "User not found", null);
        }
        UserEntity foundUser = getUser.get();

        if (!foundUser.getPassword().equals(user.getPassword())) {
            return new Respond<>(false, "Invalid password", null);
        }
        return new Respond<>(true, "Login successful", foundUser.getUsername());
    }

}
