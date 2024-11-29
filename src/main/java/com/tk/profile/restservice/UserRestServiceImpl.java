package com.tk.profile.restservice;

import com.tk.profile.model.EndUser;
import com.tk.profile.restdto.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tk.profile.repository.EndUserDb;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    private EndUserDb endUserDb;

    @Override
    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
    
    @Override
    public List<UserResponseDTO> getUsers(){
        var listUser = endUserDb.findAllByIsDeletedFalse();
        var listUserResponseDTO = new ArrayList<UserResponseDTO>();
        listUser.forEach(user -> {
            var userResponseDTO = userToUserResponseDTO(user);
            listUserResponseDTO.add(userResponseDTO);
        });

        return listUserResponseDTO;
    }

    @Override
    public List<UserResponseDTO> getUserByRole(EndUser.Role role){
        var listUser = endUserDb.findAllByRoleAndIsDeletedFalse(role);
        var listUserResponseDTO = new ArrayList<UserResponseDTO>();
        listUser.forEach(user -> {
            var userResponseDTO = userToUserResponseDTO(user);
            listUserResponseDTO.add(userResponseDTO);
        });

        return listUserResponseDTO;
    }

    @Override
    public UserResponseDTO userToUserResponseDTO(EndUser user) {
        var userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setPassword(user.getPassword());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setGender(user.assignGender());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setUpdatedAt(user.getUpdatedAt());
        userResponseDTO.setDeleted(user.isDeleted());
        userResponseDTO.setRole(user.getRole().toString());

        return userResponseDTO;
    }
    
}