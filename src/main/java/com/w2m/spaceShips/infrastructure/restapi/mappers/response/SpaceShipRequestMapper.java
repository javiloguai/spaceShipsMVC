package com.w2m.spaceShips.infrastructure.restapi.mappers.response;

import com.w2m.spaceShips.infrastructure.constants.MapperConstants;
import com.w2m.spaceShips.application.model.SpaceShipDTO;
import com.w2m.spaceShips.application.model.SpaceShipEquipmentDTO;
import com.w2m.spaceShips.domain.enums.Equipment;
import com.w2m.spaceShips.infrastructure.restapi.mappers.request.RequestMapper;
import com.w2m.spaceShips.infrastructure.restapi.model.requests.SpaceShipRequest;
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
