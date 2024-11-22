package com.tk.profile.restservice;

import com.tk.profile.model.Doctor;
import com.tk.profile.restdto.response.DoctorResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DoctorRestService {
    List<DoctorResponseDTO> getAllDoctors();
    DoctorResponseDTO getDoctorById(UUID id);
    DoctorResponseDTO doctorToDoctorResponseDTO(Doctor doctor);
}
