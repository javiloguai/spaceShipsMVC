package com.w2m.spaceShips.restapi.persistence.mappers;

import com.w2m.spaceShips.constants.MapperConstants;
import com.w2m.spaceShips.model.domain.SpaceShipEquipmentDomain;
import com.w2m.spaceShips.restapi.persistence.entities.SpaceShipEquipmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The Interface SpaceShipEquipmentDataBaseMapper.
 *
 * @author javiloguai
 */

@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SpaceShipEquipmentDataBaseMapper extends DatabaseMapper<SpaceShipEquipmentDomain, SpaceShipEquipmentEntity> {

    /**
     * Gets the mapper.
     *
     * @return the mapper
     */
    SpaceShipEquipmentDataBaseMapper INSTANCE = Mappers.getMapper(SpaceShipEquipmentDataBaseMapper.class);

}
