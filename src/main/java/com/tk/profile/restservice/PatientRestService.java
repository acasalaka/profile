package com.tk.profile.restservice;

import com.tk.profile.model.Patient;
import com.tk.profile.restdto.response.PatientResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PatientRestService {
    List<PatientResponseDTO> getAllPatients();
    PatientResponseDTO getPatientByNik(String nik);
    PatientResponseDTO patientToPatientResponseDTO(Patient patient);
}
