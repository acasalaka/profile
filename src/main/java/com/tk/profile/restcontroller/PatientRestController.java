package com.tk.profile.restcontroller;

import com.tk.profile.restdto.response.PatientResponseDTO;
import com.tk.profile.restdto.response.BaseResponseDTO;
import com.tk.profile.restservice.PatientRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patient")
public class PatientRestController {
    @Autowired
    PatientRestService patientRestService;

    @GetMapping("/viewall")
    public ResponseEntity<BaseResponseDTO<List<PatientResponseDTO>>> listPatient() {
        List<PatientResponseDTO> listPatient = patientRestService.getAllPatients();

        var baseResponseDTO = new BaseResponseDTO<List<PatientResponseDTO>>();
        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setData(listPatient);
        baseResponseDTO.setMessage(String.format("List Patient berhasil diambil"));
        baseResponseDTO.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-nik/{nik}")
    public ResponseEntity<?> detailPatientByNik(@PathVariable("nik") String nik) {
        var baseResponseDTO = new BaseResponseDTO<PatientResponseDTO>();

        PatientResponseDTO patient = patientRestService.getPatientByNik(nik);

        if (patient == null) {
            baseResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
            baseResponseDTO.setMessage("Data patient tidak ditemukan");
            baseResponseDTO.setTimestamp(new Date());
            return new ResponseEntity<>(baseResponseDTO, HttpStatus.NOT_FOUND);
        }

        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setData(patient);
        baseResponseDTO.setMessage(String.format("Patient dengan NIK %s berhasil ditemukan", patient.getNik()));
        baseResponseDTO.setTimestamp(new Date());

        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detailPatientWithId(@PathVariable("id") String id) {
        var baseResponseDTO = new BaseResponseDTO<PatientResponseDTO>();

        PatientResponseDTO patient = patientRestService.getPatientById(UUID.fromString(id));

        if (patient == null) {
            baseResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
            baseResponseDTO.setMessage("Data patient tidak ditemukan");
            baseResponseDTO.setTimestamp(new Date());
            return new ResponseEntity<>(baseResponseDTO, HttpStatus.NOT_FOUND);
        }

        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setData(patient);
        baseResponseDTO.setMessage(String.format("Patient dengan NIK %s berhasil ditemukan", patient.getNik()));
        baseResponseDTO.setTimestamp(new Date());

        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

}
