package com.tk.profile.restdto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddEndUserRequestDTO {

    // Common attributes for all roles
    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotBlank(message = "Username tidak boleh kosong")
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotNull(message = "Gender tidak boleh null")
    private Boolean gender; // Nullable to avoid issues

    @NotBlank(message = "Role cannot be blank.")
    @Pattern(regexp = "ADMIN|NURSE|PATIENT|DOCTOR|PHARMACIST",
             message = "Role must be one of the following: ADMIN, NURSE, PATIENT, DOCTOR, PHARMACIST.")
    private String role;

    // Patient-specific attributes (nullable)
    private String nik;
    private String birthPlace;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private Integer patientClass;

    // Doctor-specific attributes (nullable)
    private Integer specialist;
    private Integer yearsOfExperience;
    private Long fee;
    private List<Integer> schedules;
}