package com.tk.profile.restservice;

import com.tk.profile.model.Patient;
import com.tk.profile.repository.PatientDb;
import com.tk.profile.restdto.response.PatientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PatientRestServiceImpl implements PatientRestService {
    @Autowired
    PatientDb patientDb;

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        Sort sortByName = Sort.by(Sort.Order.by("name").ignoreCase());
        var listPatients = patientDb.findAllByIsDeletedFalse(sortByName);
        var listPatientResponseDTO = new ArrayList<PatientResponseDTO>();
        listPatients.forEach(patient -> {
            var patientResponseDTO = patientToPatientResponseDTO(patient);
            listPatientResponseDTO.add(patientResponseDTO);
        });

        return listPatientResponseDTO;
    }

    @Override
    public PatientResponseDTO getPatientByNik(String nik) {
        Patient patient = patientDb.findByNik(nik);

        if (patient == null || patient.isDeleted()) {
            return null;
        }

        return patientToPatientResponseDTO(patient);
    }

    @Override
    public PatientResponseDTO getPatientByID(UUID id) {
        Patient patient = patientDb.findById(id).orElse(null);

        if (patient == null || patient.isDeleted()) {
            return null;
        }

        return patientToPatientResponseDTO(patient);
    }

    @Override
    public PatientResponseDTO getPatientById(UUID id) {
        Patient patient = patientDb.findById(id).orElse(null);

        if (patient == null) {
            return null;
        }

        return patientToPatientResponseDTO(patient);
    }

    @Override
    public PatientResponseDTO getPatientById(UUID id) {
        Patient patient = patientDb.findById(id).orElse(null);

        if (patient == null) {
            return null;
        }

        return patientToPatientResponseDTO(patient);
    }

    @Override
    public PatientResponseDTO patientToPatientResponseDTO(Patient patient) {
        var patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setUsername(patient.getUsername());
        patientResponseDTO.setPassword(patient.getPassword());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setGender(patient.assignGender());
        patientResponseDTO.setCreatedAt(patient.getCreatedAt());
        patientResponseDTO.setUpdatedAt(patient.getUpdatedAt());
        patientResponseDTO.setDeleted(patient.isDeleted());
        patientResponseDTO.setBirthDate(patient.getBirthDate());
        patientResponseDTO.setBirthPlace(patient.getBirthPlace());
        patientResponseDTO.setNik(patient.getNik());
        patientResponseDTO.setPClass(patient.getPClass());

        return patientResponseDTO;
    }
}
