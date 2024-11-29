package com.tk.profile.restservice;

import com.tk.profile.restdto.response.UserResponseDTO;
import com.tk.profile.model.EndUser;
import com.tk.profile.restdto.response.UserResponseDTO;

import java.util.List;

public interface UserRestService {
    String hashPassword(String password);
    List<UserResponseDTO> getUsers();
    List<UserResponseDTO> getUserByRole(EndUser.Role role);
    UserResponseDTO userToUserResponseDTO(EndUser endUser);
}
