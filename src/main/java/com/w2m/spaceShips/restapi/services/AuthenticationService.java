package com.w2m.spaceShips.restapi.services;

import com.w2m.spaceShips.restapi.server.requests.AuthenticationRequest;
import com.w2m.spaceShips.restapi.server.requests.RegisterRequest;
import com.w2m.spaceShips.restapi.server.responses.AuthenticationResponse;

/**
 * @author javiloguai
 */
public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse register(RegisterRequest request);

}
