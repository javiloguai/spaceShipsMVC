package com.w2m.spaceShips.restapi.services.mappers;

import com.w2m.spaceShips.constants.MapperConstants;
import com.w2m.spaceShips.model.domain.SpaceShipEquipmentDomain;
import com.w2m.spaceShips.model.dto.SpaceShipEquipmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author javiloguai
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SpaceShipEquipmentDomainMapper extends DomainMapper<SpaceShipEquipmentDTO, SpaceShipEquipmentDomain> {

    SpaceShipEquipmentDomainMapper INSTANCE = Mappers.getMapper(SpaceShipEquipmentDomainMapper.class);

}
