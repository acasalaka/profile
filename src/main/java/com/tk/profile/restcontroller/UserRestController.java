package com.tk.profile.restcontroller;

import com.tk.profile.model.EndUser;
import com.tk.profile.restdto.response.BaseResponseDTO;
import com.tk.profile.restdto.response.UserResponseDTO;
import com.tk.profile.restservice.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    UserRestService userRestService;

    @GetMapping("/viewall")
    public ResponseEntity<BaseResponseDTO<List<UserResponseDTO>>> listUser() {
        List<UserResponseDTO> listUser = userRestService.getUsers();

        var baseResponseDTO = new BaseResponseDTO<List<UserResponseDTO>>();
        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setData(listUser);
        baseResponseDTO.setMessage(String.format("List End Users berhasil diambil"));
        baseResponseDTO.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{role}")
    public ResponseEntity<BaseResponseDTO<List<UserResponseDTO>>> listUserByRole(@PathVariable("role") String role) {
        var baseResponseDTO = new BaseResponseDTO<List<UserResponseDTO>>();
        try {
            EndUser.Role roleEnum = EndUser.Role.valueOf(role.toUpperCase());

            List<UserResponseDTO> listUser = userRestService.getUserByRole(roleEnum);

            baseResponseDTO.setStatus(HttpStatus.OK.value());
            baseResponseDTO.setData(listUser);
            baseResponseDTO.setMessage("List End Users dengan role " + role + " berhasil diambil");
            baseResponseDTO.setTimestamp(new Date());
            return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            baseResponseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponseDTO.setMessage("Invalid role: " + role + ". Valid roles are: " + Arrays.toString(EndUser.Role.values()));
            baseResponseDTO.setTimestamp(new Date());
            return new ResponseEntity<>(baseResponseDTO, HttpStatus.BAD_REQUEST);
        }
    }
}