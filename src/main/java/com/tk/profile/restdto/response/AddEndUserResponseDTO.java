package com.tk.profile.restdto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddEndUserResponseDTO {
    private UUID id;
    private String email;
    private String username;
    private String password;
    private String name;
    private String role;
}
