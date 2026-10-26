package com.management.gym.service;

import com.management.gym.dto.request.user.UserCreateRequestDTO;
import com.management.gym.dto.response.UserResponse;
import com.management.gym.entity.User;
import com.management.gym.mapper.UserMapper;
import com.management.gym.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //View the elements of a user
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found: " + email
                ));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole())
                .build();
    }

    //Register a user
    public UserResponse registerUser(UserCreateRequestDTO userCreateRequestDTO){
        User user = new User();

        user.setEmail(userCreateRequestDTO.email());
        user.setPassword(passwordEncoder.encode(userCreateRequestDTO.password()));
        user.setRole("ROLE_" + userCreateRequestDTO.role());

        return UserMapper.toResponse(userRepository.save(user));
    }

}