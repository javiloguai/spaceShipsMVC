package com.w2m.spaceShips.application.services;

import com.w2m.spaceShips.infrastructure.restapi.model.requests.AuthenticationRequest;
import com.w2m.spaceShips.infrastructure.restapi.model.requests.RegisterRequest;
import com.w2m.spaceShips.infrastructure.restapi.model.response.AuthenticationResponse;

/**
 * @author javiloguai
 */
public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse register(RegisterRequest request);

}
