package com.example.nic_validation.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
public class Person {
    private UUID id;
    private String nic;
    private LocalDate dOB;
    private Gender gender;
    private String fileName;
    private boolean isValidNic;

}
