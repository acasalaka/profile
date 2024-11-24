package com.tk.profile.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tk.profile.model.EndUser;
import com.tk.profile.repository.EndUserDb;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EndUserDb EndUserDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EndUser user = EndUserDb.findByUsername(username);

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
    }
}