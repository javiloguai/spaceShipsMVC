package com.w2m.spaceShips.infrastructure.restapi.model.requests;

import com.w2m.spaceShips.domain.enums.Equipment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author javiloguai
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceShipRequest {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private String mediaShow;

    @NotNull
    private List<Equipment> equipment;

}

