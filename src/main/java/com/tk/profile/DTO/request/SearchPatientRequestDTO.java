package com.tk.profile.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchPatientRequestDTO {
    @NotBlank(message = "NIK column cannot be empty.")
    private String nik;
}