package com.tk.profile.DTO.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDoctorRequestDTO extends AddDoctorRequestDTO {
    @Valid
    private String id;
}
