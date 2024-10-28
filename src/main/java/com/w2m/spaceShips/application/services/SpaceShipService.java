package com.w2m.spaceShips.application.services;

import com.w2m.spaceShips.application.model.SpaceShipDTO;
import com.w2m.spaceShips.domain.enums.Equipment;
import com.w2m.spaceShips.domain.model.SpaceShipDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author javiloguai
 */
public interface SpaceShipService {

    List<SpaceShipDomain> getAllSpaceShips();

    Page<SpaceShipDomain> pageAllSpaceShips(final Pageable pageable);

    Page<SpaceShipDomain> pageAllSpaceShipsByName(final String name, final Pageable pageable);

    SpaceShipDomain findById(final Long id);

    List<SpaceShipDomain> getAllSpaceShipsByEquipment(final Equipment equipment);

    SpaceShipDomain createSpaceShip(final SpaceShipDTO spaceShipDTO);

    SpaceShipDomain addEquipment(final Long id, final Equipment shipEquipment);

    SpaceShipDomain updateSpaceShip(final Long id, final SpaceShipDTO spaceShipDTO);

    void deleteSpaceShipById(final Long id);

    void deleteAllSpaceShips();

}
