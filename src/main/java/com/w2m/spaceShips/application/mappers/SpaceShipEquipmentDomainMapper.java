package com.w2m.spaceShips.application.mappers;

import com.w2m.spaceShips.infrastructure.constants.MapperConstants;
import com.w2m.spaceShips.domain.model.SpaceShipEquipmentDomain;
import com.w2m.spaceShips.application.model.SpaceShipEquipmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author javiloguai
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SpaceShipEquipmentDomainMapper extends DomainMapper<SpaceShipEquipmentDTO, SpaceShipEquipmentDomain> {

    SpaceShipEquipmentDomainMapper INSTANCE = Mappers.getMapper(SpaceShipEquipmentDomainMapper.class);

}
