package com.example.springrest.auth;

import com.example.springrest.Exception.ResourceAlreadyExistException;
import com.example.springrest.role.Role;
import com.example.springrest.role.RoleRepository;
import com.example.springrest.user.UserEntity;
import com.example.springrest.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new ResourceAlreadyExistException(registerDto.getUsername());
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_" + registerDto.getRole()).get();
        user.setRoles(Set.of(roles));


        userRepository.save(user);
    }
}
