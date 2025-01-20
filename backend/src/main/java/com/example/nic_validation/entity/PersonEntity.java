package com.example.nic_validation.entity;

import com.example.nic_validation.model.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nic;
    private LocalDate dOB;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String fileName;
}
