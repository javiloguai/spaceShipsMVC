package com.w2m.spaceShips.application.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author javiloguai
 */
@Data
@Builder
public class SpaceShipDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String mediaShow;

    private List<SpaceShipEquipmentDTO> equipment;

}
