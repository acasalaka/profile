package com.tk.profile.DTO.request;

import com.tk.profile.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddPatientRequestDTO {
    Patient patient;
    String doctorId;
    Date date;
}
