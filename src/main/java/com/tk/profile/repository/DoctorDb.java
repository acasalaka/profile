package com.tk.profile.repository;

import com.tk.profile.model.Doctor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorDb extends JpaRepository<Doctor, UUID> {
    List<Doctor> findAllByIsDeletedFalse(Sort sort);
}
