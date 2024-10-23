package com.w2m.spaceShips.restapi.server.mappers;

import com.w2m.spaceShips.constants.MapperConstants;
import com.w2m.spaceShips.model.domain.SpaceShipDomain;
import com.w2m.spaceShips.model.domain.SpaceShipEquipmentDomain;
import com.w2m.spaceShips.model.enums.Equipment;
import com.w2m.spaceShips.restapi.server.responses.SpaceShipResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

/**
 * The SpaceShipResponseMapper
 *
 * @author javiloguai
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SpaceShipResponseMapper extends ResponseMapper<SpaceShipDomain, SpaceShipResponse> {

    SpaceShipResponseMapper INSTANCE = Mappers.getMapper(SpaceShipResponseMapper.class);

    default Equipment map(SpaceShipEquipmentDomain spaceShipEquipmentDomain) {
        if (Objects.isNull(spaceShipEquipmentDomain)) {
            return null;
        }

        return spaceShipEquipmentDomain.getShipEquipment();
    }

}
