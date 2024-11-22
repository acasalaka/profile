package com.tk.profile.service;

import com.tk.profile.model.Doctor;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    void addDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(UUID id);
    Doctor updateDoctor(Doctor doctor);
    void deleteDoctor(Doctor doctor);
}
