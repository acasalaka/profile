package com.tk.profile;

import com.github.javafaker.Faker;
import com.tk.profile.model.Doctor;
import com.tk.profile.model.EndUser;
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
	CommandLineRunner run(DoctorService doctorService, PatientService patientService) {
		return args -> {
			var faker = new Faker(new Locale("in-ID"));
			var random = new Random();

			var doctor = new Doctor();

			int specialization = random.nextInt(17);

			doctor.setId(UUID.randomUUID());
			doctor.setName(faker.name().fullName());
			doctor.setUsername(faker.name().username());
			doctor.setPassword(faker.internet().password());
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

			var patient = new Patient();

			patient.setId(UUID.randomUUID());
			patient.setName(faker.name().fullName());
			patient.setUsername(faker.name().username());
			patient.setPassword(faker.internet().password());
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
			patient.setBirthDate(faker.date().birthday());
			patient.setPClass(random.nextInt(1, 3));
			patientService.createPatient(patient);
		};
	}
}
