package com.w2m.spaceShips.restapi.services.impl;

import com.w2m.spaceShips.restapi.persistence.entities.AuthUser;
import com.w2m.spaceShips.restapi.persistence.repositories.UserRepository;
import com.w2m.spaceShips.restapi.server.requests.AuthenticationRequest;
import com.w2m.spaceShips.restapi.server.requests.RegisterRequest;
import com.w2m.spaceShips.restapi.server.responses.AuthenticationResponse;
import com.w2m.spaceShips.restapi.services.AuthenticationService;
import com.w2m.spaceShips.restapi.services.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author javiloguai
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(final UserRepository userRepository,
                                     final PasswordEncoder passwordEncoder,
                                     final JwtService jwtService,
                                     final AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthenticationResponse.builder().token(token).build();

    }

    public AuthenticationResponse register(RegisterRequest request) {
        AuthUser user = AuthUser.builder().username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())).role(request.getRole()).build();

        userRepository.save(user);

        return AuthenticationResponse.builder().token(jwtService.getToken(user)).build();

    }

}
