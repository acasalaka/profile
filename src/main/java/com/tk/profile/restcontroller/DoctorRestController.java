package com.tk.profile.restcontroller;

import com.tk.profile.restdto.response.DoctorResponseDTO;
import com.tk.profile.restdto.response.BaseResponseDTO;
import com.tk.profile.restservice.DoctorRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
public class DoctorRestController {
    @Autowired
    DoctorRestService doctorRestService;

    @GetMapping("/viewall")
    public ResponseEntity<BaseResponseDTO<List<DoctorResponseDTO>>> listDoctor() {
        List<DoctorResponseDTO> listDoctor = doctorRestService.getAllDoctors();

        var baseResponseDTO = new BaseResponseDTO<List<DoctorResponseDTO>>();
        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setData(listDoctor);
        baseResponseDTO.setMessage(String.format("List Doctor berhasil diambil"));
        baseResponseDTO.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{idDoctor}")
    public ResponseEntity<?> detailDoctor(@PathVariable("idDoctor") UUID id) {
        var baseResponseDTO = new BaseResponseDTO<DoctorResponseDTO>();

        DoctorResponseDTO doctor = doctorRestService.getDoctorById(id);

        if (doctor == null) {
            baseResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
            baseResponseDTO.setMessage("Data doctor tidak ditemukan");
            baseResponseDTO.setTimestamp(new Date());
            return new ResponseEntity<>(baseResponseDTO, HttpStatus.NOT_FOUND);
        }

        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setData(doctor);
        baseResponseDTO.setMessage(String.format("Doctor dengan ID %s berhasil ditemukan", doctor.getId()));
        baseResponseDTO.setTimestamp(new Date());

        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }


}
