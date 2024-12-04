package com.tk.profile.restservice;

import com.tk.profile.model.EndUser;
import com.tk.profile.model.Patient;
import com.tk.profile.model.EndUser.Role;
import com.tk.profile.model.Doctor;
import com.tk.profile.restdto.request.AddEndUserRequestDTO;
import com.tk.profile.restdto.response.AddEndUserResponseDTO;
import com.tk.profile.restdto.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tk.profile.repository.DoctorDb;
import com.tk.profile.repository.EndUserDb;
import com.tk.profile.repository.PatientDb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    private EndUserDb endUserDb;

    @Autowired
    DoctorDb doctorDb;

    @Autowired
    PatientDb patientDb;

    @Override
    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        var listUser = endUserDb.findAllByIsDeletedFalse();
        var listUserResponseDTO = new ArrayList<UserResponseDTO>();
        listUser.forEach(user -> {
            var userResponseDTO = userToUserResponseDTO(user);
            listUserResponseDTO.add(userResponseDTO);
        });

        return listUserResponseDTO;
    }

    @Override
    public List<UserResponseDTO> getUserByRole(EndUser.Role role) {
        var listUser = endUserDb.findAllByRoleAndIsDeletedFalse(role);
        var listUserResponseDTO = new ArrayList<UserResponseDTO>();
        listUser.forEach(user -> {
            var userResponseDTO = userToUserResponseDTO(user);
            listUserResponseDTO.add(userResponseDTO);
        });

        return listUserResponseDTO;
    }

    @Override
    public AddEndUserResponseDTO addUser(AddEndUserRequestDTO dto) {
        EndUser user;

        if (dto.getRole().equalsIgnoreCase("PATIENT")) {
            // Create and save Patient
            Patient patient = new Patient();
            patient.setName(dto.getName());
            patient.setUsername(dto.getUsername());
            patient.setPassword(hashPassword(dto.getPassword()));
            patient.setEmail(dto.getEmail());
            patient.setGender(dto.getGender());
            patient.setRole(Role.PATIENT);

            // Set Patient-specific attributes
            patient.setNik(dto.getNik());
            patient.setBirthPlace(dto.getBirthPlace());
            patient.setBirthDate(dto.getBirthDate());
            patient.setPClass(dto.getPatientClass());
            patient.setCreatedAt(new Date());
            patient.setUpdatedAt(new Date());
            patient.setDeleted(false);

            user = patient;
            patientDb.save(patient);

        } else if (dto.getRole().equalsIgnoreCase("DOCTOR")) {
            // Create and save Doctor
            Doctor doctor = new Doctor();
            doctor.setName(dto.getName());
            doctor.setUsername(dto.getUsername());
            doctor.setPassword(hashPassword(dto.getPassword()));
            doctor.setEmail(dto.getEmail());
            doctor.setGender(dto.getGender());
            doctor.setRole(Role.DOCTOR);

            // Set Doctor-specific attributes
            doctor.setSpecialist(dto.getSpecialist());
            doctor.setYearsOfExperience(dto.getYearsOfExperience());
            doctor.setFee(dto.getFee());
            doctor.setSchedules(dto.getSchedules());
            doctor.setCreatedAt(new Date());
            doctor.setUpdatedAt(new Date());
            doctor.setDeleted(false);

            user = doctor;
            doctorDb.save(doctor);

        } else {
            // Handle generic roles (e.g., ADMIN, NURSE, etc.)
            user = new EndUser();
            user.setName(dto.getName());
            user.setUsername(dto.getUsername());
            user.setPassword(hashPassword(dto.getPassword()));
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());
            user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user.setDeleted(false);

            endUserDb.save(user);
        }

        // Create response
        AddEndUserResponseDTO responseDTO = new AddEndUserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole().toString());

        return responseDTO;
    }

    @Override
    public UserResponseDTO getUserByEmail(String email){
        var user = endUserDb.findByEmail(email);

        if (user == null) {
            return null;
        }

        return userToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO getUserById(UUID id){
        var user = endUserDb.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        return userToUserResponseDTO(user);
    }
    
    @Override
    public UserResponseDTO upgradePatientById(UUID id, int patientClass){
        var user = patientDb.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        if (user.getPClass() >= patientClass){
            return null;
        }

        user.setPClass(patientClass);
        patientDb.save(user);
        return userToUserResponseDTO(user);

    }


    @Override
    public UserResponseDTO userToUserResponseDTO(EndUser user) {
        var userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setGender(user.assignGender());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setUpdatedAt(user.getUpdatedAt());
        userResponseDTO.setDeleted(user.isDeleted());
        userResponseDTO.setRole(user.getRole().toString());
        if (user.getRole().toString().equals("PATIENT")){
            Patient userPatient = (Patient) user;
            userResponseDTO.setNik(userPatient.getNik());
            userResponseDTO.setBirthPlace(userPatient.getBirthPlace());
            userResponseDTO.setBirthDate(userPatient.getBirthDate());
            userResponseDTO.setPClass(userPatient.getPClass());
        } else if (user.getRole().toString().equals("DOCTOR")){
            Doctor userDoctor = (Doctor) user;
            userResponseDTO.setSpecialist(userDoctor.getSpecialist());
            userResponseDTO.setYearsOfExperience(userDoctor.getYearsOfExperience());
            userResponseDTO.setFee(userDoctor.getFee());
            userResponseDTO.setSchedules(userDoctor.getSchedules());
        }

        return userResponseDTO;
    }
}