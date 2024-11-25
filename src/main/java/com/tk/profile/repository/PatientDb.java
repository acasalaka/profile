package com.tk.profile.repository;

import com.tk.profile.model.Patient;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientDb extends JpaRepository<Patient, UUID> {
    List<Patient> findAllByIsDeletedFalse(Sort sort);
    Patient findByNik(String nik);
}
