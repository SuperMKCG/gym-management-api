package com.management.gym.service;

import com.management.gym.dto.request.login.LoginRequestDTO;
import com.management.gym.dto.response.AuthResponse;
import com.management.gym.exception.InvalidCredentialsException;
import com.management.gym.mapper.AuthMapper;
import com.management.gym.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //Authenticate the user
    public AuthResponse login(LoginRequestDTO loginRequestDTO){
        try{

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.email(),
                            loginRequestDTO.password()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String jwtToken = jwtService.generateToken(userDetails);

            return AuthMapper.toResponse(jwtToken);
        }catch (AuthenticationException ex){
            throw new InvalidCredentialsException("The user or password is invalid... try with other credentials");
        }
    }
}
