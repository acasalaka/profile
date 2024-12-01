package com.tk.profile.restcontroller;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tk.profile.restdto.response.BaseResponseDTO;
import com.tk.profile.restdto.response.UserResponseDTO;
import com.tk.profile.restservice.UserRestService;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
 
    @Autowired
    UserRestService userRestService;

    @PostMapping("/upgrade_patient")
    public ResponseEntity<BaseResponseDTO<UserResponseDTO>> upgradePatient(@RequestParam("id") String id, @RequestParam("patientClass") String patientClass){
        BaseResponseDTO<UserResponseDTO> baseResponseDTO = new BaseResponseDTO<>();
        
        try {
            UserResponseDTO userResponseDTO = userRestService.upgradePatientById(UUID.fromString(id), Integer.valueOf(patientClass));
            
            if (userResponseDTO == null){
                baseResponseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                baseResponseDTO.setTimestamp(new Date());
                baseResponseDTO.setMessage("Pasien tidak ditemukan atau patient class tidak dapat diturunkan");
                return new ResponseEntity<>(baseResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
            baseResponseDTO.setStatus(HttpStatus.OK.value());
            baseResponseDTO.setData(userResponseDTO);
            baseResponseDTO.setTimestamp(new Date());
            baseResponseDTO.setMessage(String.format(
                    "Patient %s dengan id %s berhasil dinaikan menjadi kelas %s!",
                    userResponseDTO.getUsername(),
                    userResponseDTO.getId().toString(), patientClass));
            return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
        } catch (Exception e){
            baseResponseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            baseResponseDTO.setTimestamp(new Date());
            baseResponseDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(baseResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
