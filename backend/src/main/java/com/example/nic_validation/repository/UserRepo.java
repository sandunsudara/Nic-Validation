package com.example.nic_validation.repository;

import com.example.nic_validation.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);
}
