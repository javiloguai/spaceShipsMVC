package com.w2m.spaceShips.restapi.server.responses;

import com.w2m.spaceShips.model.enums.Equipment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

/**
 * @author javiloguai
 */
@Data
@NoArgsConstructor
public class SpaceShipResponse extends RepresentationModel<SpaceShipResponse> {

    private String id;

    private String name;

    private String mediaShow;

    private List<Equipment> equipment;

}

