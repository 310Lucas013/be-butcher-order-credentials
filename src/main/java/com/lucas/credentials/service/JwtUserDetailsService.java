package com.lucas.credentials.service;

import com.lucas.credentials.models.CredentialStatus;
import com.lucas.credentials.models.UserDao;
import com.lucas.credentials.models.UserDto;
import com.lucas.credentials.models.UserType;
import com.lucas.credentials.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDao user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public UserDao save(UserDto user) {
        UserDao newUser = new UserDao();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setStatus(CredentialStatus.UNVERIFIED);
        newUser.setType(UserType.CUSTOMER);
        return userRepo.save(newUser);
    }

    public List<UserDao> getAll() {
        return userRepo.findAll();
    }
}
