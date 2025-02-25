package com.tk.profile;

import com.github.javafaker.Faker;
import com.tk.profile.model.Doctor;
import com.tk.profile.model.EndUser;
import com.tk.profile.repository.EndUserDb;
import com.tk.profile.restservice.UserRestService;
import com.tk.profile.model.Patient;
import com.tk.profile.service.DoctorService;
import com.tk.profile.service.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class ProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner run(EndUserDb endUserDb, DoctorService doctorService, PatientService patientService, UserRestService userRestService) {
        return args -> {

            var faker = new Faker(new Locale("in-ID"));
            var random = new Random();

            for (int i = 0; i < 5; i++) {
                var doctor = new Doctor();
                int specialization = random.nextInt(17);
                String nama = faker.name().fullName();
                doctor.setName(doctor.setDoctorName(nama, specialization));
                doctor.setUsername(faker.name().username());
                doctor.setPassword(userRestService.hashPassword("password"));
                doctor.setRole(EndUser.Role.DOCTOR);
                doctor.setEmail(faker.internet().emailAddress());
                doctor.setGender(random.nextBoolean());
                doctor.setSpecialist(specialization);
                doctor.setYearsOfExperience(random.nextInt(30));

                List<Integer> schedules = new ArrayList<>();
                int scheduleCount = random.nextInt(7) + 1;
                for (int j = 0; j < scheduleCount; j++) {
                    schedules.add(random.nextInt(7) + 1);
                }
                List<Integer> uniqueSchedules = schedules.stream().distinct().collect(Collectors.toList());
                doctor.setSchedules(uniqueSchedules);

                long min = 250000;
                long max = 1000000;
                long randomNumber = (long) (Math.random() * (max - min + 1)) + min;
                doctor.setFee(randomNumber);

                doctorService.addDoctor(doctor);
            }

            for (int i = 0; i < 5; i++) {
                var patient = new Patient();

                patient.setName(faker.name().fullName());
                patient.setUsername(faker.name().username());
                patient.setPassword(userRestService.hashPassword("password"));
                patient.setRole(EndUser.Role.PATIENT);
                patient.setEmail(faker.internet().emailAddress());
                patient.setGender(random.nextBoolean());

                StringBuilder number = new StringBuilder();
                number.append(random.nextInt(9) + 1);

                for (int j = 0; j < 15; j++) {
                    number.append(random.nextInt(10));
                }

                String nik = number.toString();
                patient.setNik(nik);
                patient.setBirthPlace(faker.address().city());
                patient.setBirthDate(faker.date().birthday(18, 65));
                patient.setPClass(random.nextInt(1, 4));

                patientService.createPatient(patient);

                if (endUserDb.findByRole(EndUser.Role.ADMIN) == null) {
                    var admin = new EndUser();
                    admin.setName("Admin");
                    admin.setUsername("admin");
                    admin.setPassword(userRestService.hashPassword("admin"));
                    admin.setRole(EndUser.Role.ADMIN);
                    admin.setEmail("admin@example.com");
                    admin.setGender(true);

                    endUserDb.save(admin);
                }
            }
        };
    }
}

