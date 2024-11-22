package com.tk.profile.service;

import com.tk.profile.model.Patient;

import java.util.List;

public interface PatientService {
    void createPatient(Patient patient);
    List<Patient> getAllPatients();
    Patient getPatientByNIK(String NIK);
}
