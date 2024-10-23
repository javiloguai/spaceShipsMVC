package com.w2m.spaceShips.model.domain;

import com.w2m.spaceShips.model.enums.Equipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author javiloguai
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceShipEquipmentDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private Long spaceShipId;

    private Equipment shipEquipment;

}
