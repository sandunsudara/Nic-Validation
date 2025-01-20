package com.example.nic_validation.service;

import com.example.nic_validation.model.Respond;
import com.example.nic_validation.model.User;

public interface UserService {

    Respond<Object> login(User user);

}
