package com.tk.profile.restdto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginJwtRequestDTO {
    private String email;
    private String password;
}
