package com.tk.profile.restservice;

import com.tk.profile.restdto.request.AddEndUserRequestDTO;
import com.tk.profile.restdto.response.AddEndUserResponseDTO;
import com.tk.profile.restdto.response.UserResponseDTO;
import com.tk.profile.model.EndUser;
import com.tk.profile.restdto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserRestService {
    String hashPassword(String password);
    List<UserResponseDTO> getUsers();
    List<UserResponseDTO> getUserByRole(EndUser.Role role);
    UserResponseDTO userToUserResponseDTO(EndUser endUser);
    <T extends AddEndUserRequestDTO> AddEndUserResponseDTO addUser(T dto);
    UserResponseDTO getUserById(UUID id);
    UserResponseDTO upgradePatientById(UUID id, int patientClass);
}
