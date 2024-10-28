package com.w2m.spaceShips.infrastructure.restapi.mappers.response;

import com.w2m.spaceShips.domain.enums.Equipment;
import com.w2m.spaceShips.domain.model.SpaceShipDomain;
import com.w2m.spaceShips.domain.model.SpaceShipEquipmentDomain;
import com.w2m.spaceShips.infrastructure.constants.MapperConstants;
import com.w2m.spaceShips.infrastructure.restapi.model.response.SpaceShipResponse;
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
