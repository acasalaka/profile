package com.tk.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tk.profile.model.EndUser;

import java.util.UUID;

@Repository
public interface EndUserDb extends JpaRepository<EndUser, UUID> {
    EndUser findByUsername(String username);
}