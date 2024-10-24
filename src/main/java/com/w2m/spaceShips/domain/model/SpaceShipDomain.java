package com.w2m.spaceShips.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author javiloguai
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceShipDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String mediaShow;

    private List<SpaceShipEquipmentDomain> equipment;

}
