package com.example.nic_validation.repository;

import com.example.nic_validation.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepo extends JpaRepository<PersonEntity, UUID> {
}
