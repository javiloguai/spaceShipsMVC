package com.w2m.spaceShips.restapi.services;

import com.w2m.spaceShips.model.domain.SpaceShipDomain;
import com.w2m.spaceShips.model.dto.SpaceShipDTO;
import com.w2m.spaceShips.model.enums.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author javiloguai
 */
public interface SpaceShipService {

    List<SpaceShipDomain> getAllSpaceShips();

    Page<SpaceShipDomain> pageAllSpaceShips(final Pageable pageable);

    List<SpaceShipDomain> getAllSpaceShipsByName(final String name);

    Page<SpaceShipDomain> pageAllSpaceShipsByName(final String name, final Pageable pageable);

    SpaceShipDomain findById(final Long id);

    List<SpaceShipDomain> getAllSpaceShipsByEquipment(final Equipment equipment);

    SpaceShipDomain createSpaceShip(final SpaceShipDTO spaceShipDTO);

    SpaceShipDomain addEquipment(final Long id, final Equipment equipment);

    SpaceShipDomain updateSpaceShip(final Long id, final SpaceShipDTO spaceShipDTO);

    void deleteSpaceShipById(final Long id);

    void deleteAllSpaceShips();

}
