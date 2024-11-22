package com.tk.profile.service;

import com.tk.profile.model.Patient;
import com.tk.profile.repository.PatientDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientDb patientDb;

    @Override
    public void createPatient(Patient patient) {
        patientDb.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        Sort sortByName = Sort.by(Sort.Order.by("name").ignoreCase());
        return patientDb.findAllByIsDeletedFalse(sortByName);
    };

    @Override
    public Patient getPatientByNIK(String NIK) {
        List<Patient> activePatients = getAllPatients();

        for (Patient patient : activePatients) {
            if (patient.getNik().equals(NIK)) {
                return patient;
            }
        }

        return null;
    }
}
