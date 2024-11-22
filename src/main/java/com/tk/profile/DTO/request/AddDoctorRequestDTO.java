package com.tk.profile.DTO.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddDoctorRequestDTO {
    @Valid
    @NotBlank(message = "Name column cannot be empty.")
    private String name;

    @NotNull(message = "Specialist column cannot be empty.")
    private int specialist;

    @NotBlank(message = "Email column cannot be empty.")
    private String email;

    @NotNull(message = "Gender column cannot be empty.")
    private boolean gender;

    @Min(value = 0, message = "Years of experience must be a positive number.")
    @NotNull(message = "Years of Experience column cannot be empty.")
    private int yearsOfExperience;

    @NotNull(message = "Schedule column cannot be empty.")
    private List<Integer> schedules;

    @Min(value = 0, message = "Fee must be a positive number.")
    @NotNull(message = "Fee column cannot be empty.")
    private Long fee;
}