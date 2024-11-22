package com.tk.profile.restservice;

import com.tk.profile.model.Doctor;
import com.tk.profile.repository.DoctorDb;
import com.tk.profile.restdto.response.DoctorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DoctorRestServiceImpl implements DoctorRestService {
    @Autowired
    DoctorDb doctorDb;

    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        Sort sortByName = Sort.by(Sort.Order.by("name").ignoreCase());
        var listDoctors = doctorDb.findAllByIsDeletedFalse(sortByName);
        var listDoctorResponseDTO = new ArrayList<DoctorResponseDTO>();
        listDoctors.forEach(doctor -> {
            var doctorResponseDTO = doctorToDoctorResponseDTO(doctor);
            listDoctorResponseDTO.add(doctorResponseDTO);
        });

        return listDoctorResponseDTO;
    }

    @Override
    public DoctorResponseDTO getDoctorById(UUID id) {
        Doctor doctor = doctorDb.findById(id).orElse(null);

        if (doctor == null) {
            return null;
        }

        return doctorToDoctorResponseDTO(doctor);
    }

    @Override
    public DoctorResponseDTO doctorToDoctorResponseDTO(Doctor doctor) {
        var doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setId(doctor.getId());
        doctorResponseDTO.setName(doctor.getName());
        doctorResponseDTO.setUsername(doctor.getUsername());
        doctorResponseDTO.setPassword(doctor.getPassword());
        doctorResponseDTO.setEmail(doctor.getEmail());
        doctorResponseDTO.setGender(doctor.assignGender());
        doctorResponseDTO.setCreatedAt(doctor.getCreatedAt());
        doctorResponseDTO.setUpdatedAt(doctor.getUpdatedAt());
        doctorResponseDTO.setDeleted(doctor.isDeleted());
        doctorResponseDTO.setSpecialist(doctor.getSpecializationCode(doctor.getSpecialist()));
        doctorResponseDTO.setYearsOfExperience(doctor.getYearsOfExperience());
        doctorResponseDTO.setFee(doctor.getFee());
        doctorResponseDTO.setSchedules(doctor.getSchedules());

        return doctorResponseDTO;
    }
}
