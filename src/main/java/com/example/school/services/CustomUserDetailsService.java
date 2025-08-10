package com.example.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.school.models.StaffSigninAccount;
import com.example.school.repositories.StaffLoginRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StaffLoginRepository staffLoginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        StaffSigninAccount login = staffLoginRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Login not found for: " + email));

        return new org.springframework.security.core.userdetails.User(
            login.getEmail(),
            login.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + login.getRole()))
        );
    }
}
