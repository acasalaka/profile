package com.tk.profile.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tk.profile.repository.EndUserDb;


@Service
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    private EndUserDb endUserDb;

    @Override
    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}