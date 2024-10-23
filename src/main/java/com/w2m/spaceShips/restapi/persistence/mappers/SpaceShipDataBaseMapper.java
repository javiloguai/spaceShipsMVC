package com.w2m.spaceShips.restapi.persistence.mappers;

import com.w2m.spaceShips.constants.MapperConstants;
import com.w2m.spaceShips.model.domain.SpaceShipDomain;
import com.w2m.spaceShips.model.domain.SpaceShipEquipmentDomain;
import com.w2m.spaceShips.restapi.persistence.entities.SpaceShipEntity;
import com.w2m.spaceShips.restapi.persistence.entities.SpaceShipEquipmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The Interface SpaceShipDataBaseMapper.
 *
 * @author javiloguai
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SpaceShipDataBaseMapper extends DatabaseMapper<SpaceShipDomain, SpaceShipEntity> {

    /**
     * Gets the mapper.
     *
     * @return the mapper
     */
    SpaceShipDataBaseMapper INSTANCE = Mappers.getMapper(SpaceShipDataBaseMapper.class);

    default SpaceShipEquipmentDomain map(SpaceShipEquipmentEntity shipEquipment) {

        return SpaceShipEquipmentDataBaseMapper.INSTANCE.entityToDomain(shipEquipment);
    }

    default List<SpaceShipEquipmentDomain> mapList(List<SpaceShipEquipmentEntity> shipEquipment) {
        return SpaceShipEquipmentDataBaseMapper.INSTANCE.entityToDomain(shipEquipment);
    }

    @Mapping(target = "id", ignore = true)
    void copyToEntity(SpaceShipDomain domain, @MappingTarget SpaceShipEntity entity);

}
