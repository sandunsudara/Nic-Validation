package com.example.nic_validation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respond<T> {
    private boolean success;
    private String message;
    private T data;
}
