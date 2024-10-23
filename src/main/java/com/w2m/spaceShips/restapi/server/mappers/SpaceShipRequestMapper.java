package com.w2m.spaceShips.restapi.server.mappers;

import com.w2m.spaceShips.constants.MapperConstants;
import com.w2m.spaceShips.model.dto.SpaceShipDTO;
import com.w2m.spaceShips.model.dto.SpaceShipEquipmentDTO;
import com.w2m.spaceShips.model.enums.Equipment;
import com.w2m.spaceShips.restapi.server.requests.SpaceShipRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The SpaceShip request mapper.
 *
 * @author javiloguai
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SpaceShipRequestMapper extends RequestMapper<SpaceShipRequest, SpaceShipDTO> {

    SpaceShipRequestMapper INSTANCE = Mappers.getMapper(SpaceShipRequestMapper.class);

    /**
     * Gets mapper.
     *
     * @return the mapper
     */
    static SpaceShipRequestMapper getMapper() {
        return Mappers.getMapper(SpaceShipRequestMapper.class);
    }

    /**
     * From requests to dtos spaceships.
     *
     * @param requests the requests spaceships
     * @return the mapped result
     */
    List<SpaceShipDTO> fromRequestsToDtos(List<SpaceShipRequest> requests);

    default SpaceShipEquipmentDTO map(Equipment shipEquipment) {
        if (Objects.isNull(shipEquipment)) {
            return null;
        }

        return SpaceShipEquipmentDTO.builder().shipEquipment(shipEquipment).build();
    }

    default List<SpaceShipEquipmentDTO> mapList(List<Equipment> equipment) {
        if (Objects.isNull(equipment)) {
            return Collections.emptyList();
        } else if (equipment.isEmpty()) {
            return new ArrayList<SpaceShipEquipmentDTO>();
        }

        return equipment.stream().map(this::map).toList();
    }

}
