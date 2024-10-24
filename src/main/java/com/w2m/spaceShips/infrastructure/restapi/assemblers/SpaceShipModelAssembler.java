package com.w2m.spaceShips.infrastructure.restapi.assemblers;

import com.w2m.spaceShips.domain.model.SpaceShipDomain;
import com.w2m.spaceShips.domain.model.SpaceShipEquipmentDomain;
import com.w2m.spaceShips.domain.enums.Equipment;
import com.w2m.spaceShips.infrastructure.restapi.controllers.SpaceShipCRUDController;
import com.w2m.spaceShips.infrastructure.restapi.model.response.SpaceShipResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author javiloguai
 */
@Component
public class SpaceShipModelAssembler extends RepresentationModelAssemblerSupport<SpaceShipDomain, SpaceShipResponse> {

    public SpaceShipModelAssembler() {
        super(SpaceShipCRUDController.class, SpaceShipResponse.class);
    }

    @Override
    public SpaceShipResponse toModel(SpaceShipDomain entity) {
        SpaceShipResponse spaceShipModel = instantiateModel(entity);

        spaceShipModel.setId(String.valueOf(entity.getId()));
        spaceShipModel.setName(entity.getName());
        spaceShipModel.setMediaShow(entity.getMediaShow());
        spaceShipModel.setEquipment(toSpaceShipModel(entity.getEquipment()));
        return spaceShipModel;
    }

    @Override
    public CollectionModel<SpaceShipResponse> toCollectionModel(Iterable<? extends SpaceShipDomain> entities) {

        return super.toCollectionModel(entities);
    }

    private List<Equipment> toSpaceShipModel(List<SpaceShipEquipmentDomain> equipment) {
        if (equipment.isEmpty())
            return Collections.emptyList();

        return equipment.stream().map(ship -> ship.getShipEquipment()).toList();
    }
}