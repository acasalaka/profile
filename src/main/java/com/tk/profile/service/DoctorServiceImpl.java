package com.tk.profile.service;

import com.tk.profile.model.Doctor;
import com.tk.profile.repository.DoctorDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorDb doctorDb;

    @Override
    public void addDoctor(Doctor doctor) {
        doctorDb.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        Sort sortByName = Sort.by(Sort.Order.by("name").ignoreCase());
        return doctorDb.findAllByIsDeletedFalse(sortByName);
    };

    @Override
    public Doctor getDoctorById(UUID idDoctor) {

        return doctorDb.findById(idDoctor).orElse(null);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        Doctor getDoctor = getDoctorById(doctor.getId());
        if (getDoctor != null) {
            String name = getDoctor.sanitizeName(doctor.getName());
            getDoctor.setName(name);
            getDoctor.setUsername(doctor.getUsername());
            getDoctor.setPassword(doctor.getPassword());
            getDoctor.setEmail(doctor.getEmail());
            getDoctor.setGender(doctor.isGender());
            getDoctor.setSpecialist(doctor.getSpecialist());
            getDoctor.setYearsOfExperience(doctor.getYearsOfExperience());
            getDoctor.setSchedules(doctor.getSchedules());
            getDoctor.setFee(doctor.getFee());
            doctorDb.save(getDoctor);

            return getDoctor;
        }

        return null;
    }

    @Override
    public void deleteDoctor(Doctor doctor) {
        doctor.setDeleted(true);
        doctorDb.save(doctor);
    }
}
