package com.w2m.spaceShips.model.dto;

import com.w2m.spaceShips.model.enums.Equipment;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author javiloguai
 */
@Data
@Builder
public class SpaceShipEquipmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private Long spaceShipId;

    private Equipment shipEquipment;

}
