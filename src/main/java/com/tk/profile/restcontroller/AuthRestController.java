package com.tk.profile.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.tk.profile.restdto.request.LoginJwtRequestDTO;
import com.tk.profile.restdto.response.BaseResponseDTO;
import com.tk.profile.restdto.response.LoginJwtResponseDTO;
import com.tk.profile.security.jwt.JwtUtils;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDTO<LoginJwtResponseDTO>> login(@RequestBody LoginJwtRequestDTO loginRequestDTO) {
        BaseResponseDTO<LoginJwtResponseDTO> response = new BaseResponseDTO<>();

        try {
            // Authenticate the user using email instead of username
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getPassword()));

            // Set the authentication in the SecurityContext for session consistency
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String email = authentication.getName(); // Extract email
            String token = jwtUtils.generateJwtToken(email); // Generate token

            // Build success response
            LoginJwtResponseDTO loginResponse = new LoginJwtResponseDTO(token);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Login berhasil!");
            response.setTimestamp(new Date());
            response.setData(loginResponse);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (AuthenticationException e) {
            // Build failure response
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Email atau Password salah!");
            response.setTimestamp(new Date());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

  

}
