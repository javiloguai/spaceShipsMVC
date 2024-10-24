package com.w2m.spaceShips.infrastructure.restapi.model.requests;

import com.w2m.spaceShips.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author javiloguai
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    private String password;

    private Role role;
}
