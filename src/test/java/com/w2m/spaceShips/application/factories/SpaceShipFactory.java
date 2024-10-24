package com.w2m.spaceShips.application.factories;

import com.w2m.spaceShips.application.model.SpaceShipDTO;
import com.w2m.spaceShips.application.model.SpaceShipEquipmentDTO;
import com.w2m.spaceShips.domain.enums.Equipment;
import com.w2m.spaceShips.domain.model.SpaceShipDomain;
import com.w2m.spaceShips.domain.model.SpaceShipEquipmentDomain;
import com.w2m.spaceShips.infrastructure.persistence.entities.SpaceShipEntity;
import com.w2m.spaceShips.infrastructure.persistence.entities.SpaceShipEquipmentEntity;
import com.w2m.spaceShips.infrastructure.restapi.model.requests.SpaceShipRequest;

import java.util.List;

/**
 * @author javiloguai
 * <p>
 * The Class SpaceShipFactory.
 */
public final class SpaceShipFactory {

    public static Long SPACE_SHIP_ID = 100L;

    public static Long EQUPMENT_ID = 288L;

    public static String NAME = "X-wing";

    public static SpaceShipEntity getEntity() {
        return getEntity(SPACE_SHIP_ID);
    }

    public static SpaceShipEntity getEntity(Long spaceShipId) {
        List<SpaceShipEquipmentEntity> equipment = getSpaceShipEquipmentList(spaceShipId);
        SpaceShipEntity mySpaceShipEntity = SpaceShipEntity.builder()
                .id(spaceShipId)
                .name(NAME)
                .mediaShow("none")
                .equipment(equipment).build();
        return mySpaceShipEntity;
    }

    public static List<SpaceShipEquipmentEntity> getSpaceShipEquipmentList(Long spaceShipId) {
        return List.of(getSpaceShipEquipment(spaceShipId));
    }

    public static SpaceShipEquipmentEntity getSpaceShipEquipment(Long spaceShipId) {
        return getSpaceShipEquipment(spaceShipId, EQUPMENT_ID);
    }

    public static SpaceShipEquipmentEntity getSpaceShipEquipment(Long spaceShipId, Long equipmentId) {
        return SpaceShipEquipmentEntity.builder().id(equipmentId).spaceShipId(spaceShipId).shipEquipment(Equipment.INVULNERABILITY_SHIELD).build();
    }

    public static SpaceShipDTO getDTO() {
        return getDTO(SPACE_SHIP_ID);
    }

    public static SpaceShipDTO getDTO(Long spaceShipId) {
        List<SpaceShipEquipmentDTO> equipment = getSpaceShipEquipmentListDTO(spaceShipId);
        SpaceShipDTO spaceShipDto = SpaceShipDTO.builder()
                .id(spaceShipId)
                .name(NAME)
                .mediaShow("none")
                .equipment(equipment).build();
        return spaceShipDto;
    }

    public static List<SpaceShipEquipmentDTO> getSpaceShipEquipmentListDTO(Long spaceShipId) {
        return List.of(getSpaceShipEquipmentDTO(spaceShipId));
    }

    public static SpaceShipEquipmentDTO getSpaceShipEquipmentDTO(Long spaceShipId) {
        return getSpaceShipEquipmentDTO(spaceShipId, EQUPMENT_ID);
    }

    public static SpaceShipEquipmentDTO getSpaceShipEquipmentDTO(Long spaceShipId, Long equipmentId) {
        return SpaceShipEquipmentDTO.builder().id(equipmentId).spaceShipId(spaceShipId).shipEquipment(Equipment.INVULNERABILITY_SHIELD).build();
    }

    public static SpaceShipDomain getDO() {
        return getDO(SPACE_SHIP_ID);
    }

    public static SpaceShipDomain getDO(Long spaceShipId) {
        List<SpaceShipEquipmentDomain> equipment = getSpaceShipEquipmentListDO(spaceShipId);
        SpaceShipDomain mySpaceShipDomain = SpaceShipDomain.builder()
                .id(spaceShipId)
                .name(NAME)
                .mediaShow("none")
                .equipment(equipment).build();
        return mySpaceShipDomain;
    }

    public static List<SpaceShipEquipmentDomain> getSpaceShipEquipmentListDO(Long spaceShipId) {
        return List.of(getSpaceShipEquipmentDO(spaceShipId));
    }

    public static SpaceShipEquipmentDomain getSpaceShipEquipmentDO(Long spaceShipId) {
        return getSpaceShipEquipmentDO(spaceShipId, EQUPMENT_ID);
    }

    public static SpaceShipEquipmentDomain getSpaceShipEquipmentDO(Long spaceShipId, Long equipmentId) {
        return SpaceShipEquipmentDomain.builder().id(equipmentId).spaceShipId(spaceShipId).shipEquipment(Equipment.INVULNERABILITY_SHIELD).build();
    }

    public static SpaceShipRequest getRequest() {
        List<Equipment> equipment = getSpaceShipEquipmentList();
        SpaceShipRequest ship = SpaceShipRequest.builder()
                .name(NAME)
                .mediaShow("none")
                .equipment(equipment).build();
        return ship;
    }

    public static List<Equipment> getSpaceShipEquipmentList() {
        return List.of(Equipment.INVULNERABILITY_SHIELD);
    }


}