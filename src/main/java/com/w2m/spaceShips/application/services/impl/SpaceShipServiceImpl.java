package com.w2m.spaceShips.application.services.impl;

import com.w2m.spaceShips.application.exception.AlreadyExistException;
import com.w2m.spaceShips.application.exception.BusinessRuleViolatedException;
import com.w2m.spaceShips.application.exception.NotFoundException;
import com.w2m.spaceShips.application.mappers.SpaceShipDomainMapper;
import com.w2m.spaceShips.application.model.SpaceShipDTO;
import com.w2m.spaceShips.application.services.BasicService;
import com.w2m.spaceShips.application.services.SpaceShipService;
import com.w2m.spaceShips.domain.enums.Equipment;
import com.w2m.spaceShips.domain.model.SpaceShipDomain;
import com.w2m.spaceShips.domain.model.SpaceShipEquipmentDomain;
import com.w2m.spaceShips.infrastructure.persistence.entities.SpaceShipEntity;
import com.w2m.spaceShips.infrastructure.persistence.entities.SpaceShipEquipmentEntity;
import com.w2m.spaceShips.infrastructure.persistence.mappers.SpaceShipDataBaseMapper;
import com.w2m.spaceShips.infrastructure.persistence.repositories.SpaceShipEquipmentRepository;
import com.w2m.spaceShips.infrastructure.persistence.repositories.SpaceShipRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author javiloguai
 * <p>
 * I put some examples of other kind of call I will not implement anything on other examples
 */
@Service
@Validated
@Transactional
public class SpaceShipServiceImpl extends BasicService implements SpaceShipService {

    private static final String ID_MANDATORY = "Id field is Mandatory";

    private static final String NAME_EMPTY = "SpaceShip's name cannot be empty";

    private static final String EQUIPMENT_EMPTY = "SpaceShip's equipment list cannot be empty";

    private static final String PAGE_MANDATORY = "page info is Mandatory";

    private static final String NAME_MANDATORY = "name field is Mandatory";

    private static final String EQUIPMENT_MANDATORY = "equipment field is Mandatory";

    private static final String SPACE_SHIP_MANDATORY = "The SpaceShip Object is Mandatory";

    private final SpaceShipRepository spaceShipRepository;

    private final SpaceShipEquipmentRepository spaceShipEquipmentRepository;

    public SpaceShipServiceImpl(final ApplicationContext applicationContext,
                                final SpaceShipRepository spaceShipRepository,
                                final SpaceShipEquipmentRepository spaceShipEquipmentRepository) {
        super(applicationContext);
        this.spaceShipRepository = spaceShipRepository;
        this.spaceShipEquipmentRepository = spaceShipEquipmentRepository;
    }

    @Override
    @Cacheable(cacheNames = "allships")
    public List<SpaceShipDomain> getAllSpaceShips() {

        return SpaceShipDataBaseMapper.INSTANCE.entityToDomain(spaceShipRepository.findAll());
    }

    @Override
    @Cacheable(cacheNames = "pagedallships")
    public Page<SpaceShipDomain> pageAllSpaceShips(final Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new BusinessRuleViolatedException(PAGE_MANDATORY);
        }
        Page<SpaceShipEntity> pagedResults = spaceShipRepository.findAll(pageable);

        return SpaceShipDataBaseMapper.INSTANCE.entityToDomain(pagedResults);
    }

    @Override
    @Cacheable(cacheNames = "pagedships", key = "#name")
    public Page<SpaceShipDomain> pageAllSpaceShipsByName(final String name, final Pageable pageable) {
        if (!StringUtils.hasText(name)) {
            throw new BusinessRuleViolatedException(NAME_MANDATORY);
        }
        if (Objects.isNull(pageable)) {
            throw new BusinessRuleViolatedException(PAGE_MANDATORY);
        }

        return SpaceShipDataBaseMapper.INSTANCE.entityToDomain(
                spaceShipRepository.findByNameContainingIgnoreCase(name, pageable));
    }

    @Override
    @Cacheable(cacheNames = "equipment", key = "#shipEquipment")
    public List<SpaceShipDomain> getAllSpaceShipsByEquipment(final Equipment shipEquipment) {
        if (shipEquipment == null) {
            throw new BusinessRuleViolatedException(EQUIPMENT_MANDATORY);

        }
        List<SpaceShipEquipmentEntity> equipmentList = spaceShipEquipmentRepository.findByShipEquipment(shipEquipment);
        List<Long> shipsIds = equipmentList.stream().map(p -> p.getSpaceShipId()).distinct().toList();

        return SpaceShipDataBaseMapper.INSTANCE.entityToDomain(spaceShipRepository.findAllById(shipsIds));
    }

    @Override
    @Cacheable(cacheNames = "ship", key = "#id")
    public SpaceShipDomain findById(final Long id) {
        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        return SpaceShipDataBaseMapper.INSTANCE.entityToDomain(getEntityById(id));
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "ship", allEntries = true),
            @CacheEvict(value = "pagedships", allEntries = true), @CacheEvict(value = "allships", allEntries = true),
            @CacheEvict(value = "pagedallships", allEntries = true),
            @CacheEvict(value = "equipment", allEntries = true)})
    public SpaceShipDomain createSpaceShip(final SpaceShipDTO spaceShipDTO) {
        SpaceShipDomain shipDO = this.validateShipToCreate(spaceShipDTO);

        SpaceShipEntity shipE = SpaceShipDataBaseMapper.INSTANCE.domainToEntity(shipDO);

        spaceShipRepository.saveAndFlush(shipE);

        final Long shipId = shipE.getId();

        List<SpaceShipEquipmentEntity> equipment = shipE.getEquipment();

        equipment.forEach(h -> h.setSpaceShipId(shipId));
        spaceShipEquipmentRepository.saveAllAndFlush(equipment);

        return SpaceShipDataBaseMapper.INSTANCE.entityToDomain(this.getEntityById(shipId));
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "ship", allEntries = true),
            @CacheEvict(value = "pagedships", allEntries = true), @CacheEvict(value = "allships", allEntries = true),
            @CacheEvict(value = "pagedallships", allEntries = true),
            @CacheEvict(value = "equipment", allEntries = true)})
    public SpaceShipDomain updateSpaceShip(final Long id, final SpaceShipDTO spaceShipDTO) {

        SpaceShipDomain shipDO = this.validateShipToUpdate(id, spaceShipDTO);

        SpaceShipEntity shipToUpdate = this.getEntityById(id);
        SpaceShipDataBaseMapper.INSTANCE.copyToEntity(shipDO, shipToUpdate);
        List<SpaceShipEquipmentEntity> equipment = new ArrayList<>();
        equipment.addAll(shipToUpdate.getEquipment());

        spaceShipRepository.saveAndFlush(shipToUpdate);

        spaceShipEquipmentRepository.deleteAllBySpaceShipId(id);
        spaceShipEquipmentRepository.flush();

        equipment.forEach(h -> h.setSpaceShipId(id));
        spaceShipEquipmentRepository.saveAllAndFlush(equipment);

        return SpaceShipDataBaseMapper.INSTANCE.entityToDomain(this.getEntityById(id));
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "ship", allEntries = true),
            @CacheEvict(value = "pagedships", allEntries = true), @CacheEvict(value = "allships", allEntries = true),
            @CacheEvict(value = "pagedallships", allEntries = true),
            @CacheEvict(value = "equipment", allEntries = true)})
    public SpaceShipDomain addEquipment(final Long id, final Equipment equipment) {

        validateAddEquipment(id, equipment);

        SpaceShipEquipmentEntity equipmentToAdd = SpaceShipEquipmentEntity.builder().spaceShipId(id).shipEquipment(equipment).build();

        spaceShipEquipmentRepository.saveAndFlush(equipmentToAdd);

        return SpaceShipDataBaseMapper.INSTANCE.entityToDomain(this.getEntityById(id));
    }

    private void validateAddEquipment(final Long id, final Equipment shipEquipment) {

        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        if (shipEquipment == null) {
            throw new BusinessRuleViolatedException(EQUIPMENT_MANDATORY);
        }

        SpaceShipDomain shipDomain = this.findById(id);

        List<SpaceShipEquipmentDomain> equipmentList = shipDomain.getEquipment();
        List<Equipment> equipmentL = equipmentList.stream().map(p -> p.getShipEquipment()).distinct().toList();

        if (!equipmentL.isEmpty() && equipmentL.contains(shipEquipment)) {
            throw new AlreadyExistException("This SpaceShip already has that ship equipment: " + shipEquipment);
        }
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "ship", allEntries = true),
            @CacheEvict(value = "pagedships", allEntries = true), @CacheEvict(value = "allships", allEntries = true),
            @CacheEvict(value = "pagedallships", allEntries = true),
            @CacheEvict(value = "equipment", allEntries = true)})
    public void deleteSpaceShipById(final Long id) {

        this.findById(id);

        spaceShipEquipmentRepository.deleteAllBySpaceShipId(id);
        spaceShipEquipmentRepository.flush();

        spaceShipRepository.deleteById(id);
        spaceShipRepository.flush();

    }

    @Override
    @Caching(evict = {@CacheEvict(value = "ship", allEntries = true),
            @CacheEvict(value = "pagedships", allEntries = true), @CacheEvict(value = "allships", allEntries = true),
            @CacheEvict(value = "pagedallships", allEntries = true),
            @CacheEvict(value = "equipment", allEntries = true)})
    public void deleteAllSpaceShips() {
        if (!spaceShipEquipmentRepository.findAll().isEmpty()) {
            spaceShipEquipmentRepository.deleteAll();
            spaceShipEquipmentRepository.flush();
        }
        if (!spaceShipRepository.findAll().isEmpty()) {
            spaceShipRepository.deleteAll();
            spaceShipRepository.flush();
        }

    }

    private SpaceShipEntity getEntityById(final Long id) {
        return spaceShipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Not found SpaceShip with id %s", id)));
    }

    private SpaceShipDomain validateShipToCreate(final SpaceShipDTO spaceShipDTO) {
        this.validateShipData(spaceShipDTO);
        this.checkIfShipAlreadyExists(spaceShipDTO.getName());
        return SpaceShipDomainMapper.INSTANCE.dtoToDomain(spaceShipDTO);
    }

    private SpaceShipDomain validateShipToUpdate(final Long id, final SpaceShipDTO spaceShipDTO) {
        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        this.validateShipData(spaceShipDTO);
        this.checkIfShipAlreadyExists(id, spaceShipDTO.getName());
        return SpaceShipDomainMapper.INSTANCE.dtoToDomain(spaceShipDTO);
    }

    private void validateShipData(final SpaceShipDTO spaceShipDTO) {
        if (spaceShipDTO == null) {
            throw new BusinessRuleViolatedException(SPACE_SHIP_MANDATORY);
        }
        if (!StringUtils.hasText(spaceShipDTO.getName())) {
            throw new BusinessRuleViolatedException(NAME_EMPTY);
        }
        if (CollectionUtils.isEmpty(spaceShipDTO.getEquipment())) {
            throw new BusinessRuleViolatedException(EQUIPMENT_EMPTY);
        }
    }

    private void checkIfShipAlreadyExists(final String shipName) {
        spaceShipRepository.findFirstByNameIgnoreCase(shipName).ifPresent(this::throwAlreadyExistException);
    }

    private void checkIfShipAlreadyExists(final Long id, final String shipName) {
        spaceShipRepository.findFirstByNameIgnoreCase(shipName).ifPresent(h -> {
            if (h.getId() != id.longValue()) {
                throwAlreadyExistException(h);
            }
        });
    }

    private void throwAlreadyExistException(final SpaceShipEntity spaceShipEntity) {
        throw new AlreadyExistException("This SpaceShip already exists: " + spaceShipEntity.toString());
    }

}
