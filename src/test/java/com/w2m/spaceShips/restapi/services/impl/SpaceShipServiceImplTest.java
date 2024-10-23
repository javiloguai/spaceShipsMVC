package com.w2m.spaceShips.restapi.services.impl;

import com.w2m.spaceShips.config.exception.AlreadyExistException;
import com.w2m.spaceShips.config.exception.BusinessRuleViolatedException;
import com.w2m.spaceShips.config.exception.NotFoundException;
import com.w2m.spaceShips.factories.SpaceShipFactory;
import com.w2m.spaceShips.model.domain.SpaceShipDomain;
import com.w2m.spaceShips.model.dto.SpaceShipDTO;
import com.w2m.spaceShips.model.enums.Equipment;
import com.w2m.spaceShips.restapi.persistence.entities.SpaceShipEntity;
import com.w2m.spaceShips.restapi.persistence.entities.SpaceShipEquipmentEntity;
import com.w2m.spaceShips.restapi.persistence.mappers.SpaceShipDataBaseMapper;
import com.w2m.spaceShips.restapi.persistence.repositories.SpaceShipEquipmentRepository;
import com.w2m.spaceShips.restapi.persistence.repositories.SpaceShipRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author javiloguai
 */
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration
public class SpaceShipServiceImplTest {

    private static final String ID_MANDATORY = "Id field is Mandatory";

    private static final String NAME_EMPTY = "SpaceShip's name cannot be empty";

    private static final String EQUIPMENT_EMPTY = "SpaceShip's equipment list cannot be empty";

    private static final String PAGE_MANDATORY = "page info is Mandatory";

    private static final String NAME_MANDATORY = "name field is Mandatory";

    private static final String EQUIPMENT_MANDATORY = "equipment field is Mandatory";

    private static final String SPACE_SHIP_MANDATORY = "The SpaceShip Object is Mandatory";

    @InjectMocks
    private SpaceShipServiceImpl spaceShipService;

    @MockBean
    private SpaceShipRepository spaceShipRepository;

    @MockBean
    private SpaceShipEquipmentRepository spaceShipEquipmentRepository;

    @Captor
    private ArgumentCaptor<SpaceShipEquipmentEntity> shipEquipmentEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<SpaceShipEntity> shipEntityArgumentCaptor;

    /**
     * Tests for getAllSpaceShips method
     */
    @Nested
    class getAllSpaceShipsTest {

        @Test
        void givenNullPage_thenThrowException() {

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.pageAllSpaceShips(null));
            Assertions.assertEquals(PAGE_MANDATORY, ex.getMessage());

        }

        @Test
        void givenNonExistingSpaceShips_thenReturnEmptyPage() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SpaceShipEntity> shipList = List.of();

            final Pageable pageable = PageRequest.of(0, 20);
            final Page<SpaceShipEntity> pageResult = new PageImpl<>(shipList, pageable, 1);
            Mockito.when(spaceShipRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

            // when
            final Page<SpaceShipDomain> pageResultDomain = spaceShipService.pageAllSpaceShips(pageable);

            // then
            Assertions.assertNotNull(pageResultDomain);
            Assertions.assertTrue(pageResultDomain.isEmpty());
            Assertions.assertEquals(shipList.size(), pageResultDomain.getTotalElements());

        }

        @Test
        void givenNonExistingSpaceShips_thenReturnEmptyList() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SpaceShipEntity> shipList = List.of();

            Mockito.when(spaceShipRepository.findAll()).thenReturn(shipList);

            // when
            final List<SpaceShipDomain> listResultDomain = spaceShipService.getAllSpaceShips();

            // then
            Assertions.assertNotNull(listResultDomain);
            Assertions.assertTrue(listResultDomain.isEmpty());
            Assertions.assertEquals(shipList.size(), listResultDomain.size());
        }

        @Test
        void givenExistingSpaceShips_thenReturnAllPagedSpaceShips() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SpaceShipEntity chip1 = SpaceShipFactory.getEntity(1L);
            final SpaceShipEntity chip2 = SpaceShipFactory.getEntity(2L);

            final List<SpaceShipEntity> shipList = List.of(chip1, chip2);

            final Pageable pageable = PageRequest.of(0, 20);
            final Page<SpaceShipEntity> pageResult = new PageImpl<>(shipList, pageable, 1);
            Mockito.when(spaceShipRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

            // when
            final Page<SpaceShipDomain> pageResultDomain = spaceShipService.pageAllSpaceShips(pageable);

            // then
            Assertions.assertEquals(shipList.size(), pageResultDomain.getTotalElements());
            Assertions.assertEquals(shipList.get(0).getId(), pageResultDomain.toList().get(0).getId());
            Assertions.assertEquals(shipList.get(1).getId(), pageResultDomain.toList().get(1).getId());
        }

        @Test
        void givenExistingSpaceShips_thenReturnAllSpaceShips() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SpaceShipEntity chip1 = SpaceShipFactory.getEntity(1L);
            final SpaceShipEntity chip2 = SpaceShipFactory.getEntity(2L);

            final List<SpaceShipEntity> shipList = List.of(chip1, chip2);

            Mockito.when(spaceShipRepository.findAll()).thenReturn(shipList);

            // when
            final List<SpaceShipDomain> listResultDomain = spaceShipService.getAllSpaceShips();

            // then
            Assertions.assertEquals(shipList.size(), listResultDomain.size());
            Assertions.assertEquals(shipList.get(0).getId(), listResultDomain.get(0).getId());
            Assertions.assertEquals(shipList.get(1).getId(), listResultDomain.get(1).getId());
        }

    }

    /**
     * Tests for getAllSpaceShipsByName method
     */
    @Nested
    class getAllSpaceShipsByNameTest {

        @Test
        void givenNullName_thenThrowException() {

            final List<SpaceShipEntity> shipList = List.of();

            final Pageable pageable = PageRequest.of(0, 20);

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.getAllSpaceShipsByName(null));
            Assertions.assertEquals(NAME_MANDATORY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.pageAllSpaceShipsByName(null, pageable));
            Assertions.assertEquals(NAME_MANDATORY, ex2.getMessage());

        }

        @Test
        void givenNullPage_thenThrowException() {

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.pageAllSpaceShipsByName("name", null));
            Assertions.assertEquals(PAGE_MANDATORY, ex.getMessage());

        }

        @Test
        void givenNonMatchingSpaceShips_thenReturnEmptyPage() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SpaceShipEntity> shipList = List.of();

            final Pageable pageable = PageRequest.of(0, 20);
            final Page<SpaceShipEntity> pageResult = new PageImpl<>(shipList, pageable, 1);
            Mockito.when(spaceShipRepository.findByNameContainingIgnoreCase(ArgumentMatchers.any(String.class),
                    ArgumentMatchers.any(Pageable.class))).thenReturn(pageResult);

            // when
            final Page<SpaceShipDomain> pageResultDomain = spaceShipService.pageAllSpaceShipsByName("XXX", pageable);

            // then
            Assertions.assertNotNull(pageResultDomain);
            Assertions.assertTrue(pageResultDomain.isEmpty());
            Assertions.assertEquals(shipList.size(), pageResultDomain.getTotalElements());

        }

        @Test
        void givenNonMatchingSpaceShips_thenReturnEmptyList() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SpaceShipEntity> shipList = List.of();

            Mockito.when(spaceShipRepository.findByNameContaining(ArgumentMatchers.any(String.class)))
                    .thenReturn(shipList);

            // when
            final List<SpaceShipDomain> listResultDomain = spaceShipService.getAllSpaceShipsByName("XXX");

            // then
            Assertions.assertNotNull(listResultDomain);
            Assertions.assertTrue(listResultDomain.isEmpty());
            Assertions.assertEquals(shipList.size(), listResultDomain.size());
        }

        @Test
        void givenMatchingSpaceShips_thenReturnAllMatchingPagedSpaceShips() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SpaceShipEntity chip1 = SpaceShipFactory.getEntity(1L);
            final SpaceShipEntity chip2 = SpaceShipFactory.getEntity(2L);

            final List<SpaceShipEntity> shipList = List.of(chip1, chip2);

            final Pageable pageable = PageRequest.of(0, 20);
            final Page<SpaceShipEntity> pageResult = new PageImpl<>(shipList, pageable, 1);
            Mockito.when(spaceShipRepository.findByNameContainingIgnoreCase(chip1.getName(), pageable))
                    .thenReturn(pageResult);

            // when
            final Page<SpaceShipDomain> pageResultDomain = spaceShipService.pageAllSpaceShipsByName(chip1.getName(),
                    pageable);

            // then
            Assertions.assertEquals(shipList.size(), pageResultDomain.getTotalElements());
            Assertions.assertEquals(shipList.get(0).getId(), pageResultDomain.toList().get(0).getId());
            Assertions.assertEquals(shipList.get(1).getId(), pageResultDomain.toList().get(1).getId());
        }

        @Test
        void givenMatchingSpaceShips_thenReturnMatchingSpaceShips() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SpaceShipEntity chip1 = SpaceShipFactory.getEntity(1L);
            final SpaceShipEntity chip2 = SpaceShipFactory.getEntity(2L);

            final List<SpaceShipEntity> shipList = List.of(chip1, chip2);

            Mockito.when(spaceShipRepository.findByNameContaining(chip1.getName())).thenReturn(shipList);

            // when
            final List<SpaceShipDomain> listResultDomain = spaceShipService.getAllSpaceShipsByName(chip1.getName());

            // then
            Assertions.assertEquals(shipList.size(), listResultDomain.size());
            Assertions.assertEquals(shipList.get(0).getId(), listResultDomain.get(0).getId());
            Assertions.assertEquals(shipList.get(1).getId(), listResultDomain.get(1).getId());
        }

    }

    /**
     * Tests for getAllSpaceShipsByEquipment method
     */
    @Nested
    class getAllSpaceShipsByEquipmentTest {

        @Test
        void givenNullShipEquipment_thenThrowException() {

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.getAllSpaceShipsByEquipment(null));
            Assertions.assertEquals(EQUIPMENT_MANDATORY, ex.getMessage());

        }

        @Test
        void givenNonMatchingShipEquipment_thenReturnEmptyList() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final List<SpaceShipEquipmentEntity> plist = List.of();
            final List<SpaceShipEntity> shipList = List.of();

            List<Long> shipsIds = plist.stream().map(p -> p.getSpaceShipId()).distinct().toList();

            Mockito.when(spaceShipEquipmentRepository.findByShipEquipment(ArgumentMatchers.any(Equipment.class)))
                    .thenReturn(plist);

            Mockito.when(spaceShipRepository.findAllById(shipsIds)).thenReturn(shipList);

            // when
            final List<SpaceShipDomain> listResultDomain = spaceShipService.getAllSpaceShipsByEquipment(
                    Equipment.LASER_BLASTER);

            // then
            Assertions.assertNotNull(listResultDomain);
            Assertions.assertTrue(listResultDomain.isEmpty());
            Assertions.assertEquals(shipList.size(), listResultDomain.size());
        }

        @Test
        void givenMatchingShipEquipment_thenReturnMatchingSpaceShips() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SpaceShipEntity chip1 = SpaceShipFactory.getEntity(1L);
            final SpaceShipEntity chip2 = SpaceShipFactory.getEntity(2L);

            Equipment equipment = SpaceShipFactory.getSpaceShipEquipment(1L).getShipEquipment();

            SpaceShipEquipmentEntity p1 = SpaceShipFactory.getSpaceShipEquipment(1L, SpaceShipFactory.EQUPMENT_ID);
            SpaceShipEquipmentEntity p2 = SpaceShipFactory.getSpaceShipEquipment(2L, SpaceShipFactory.EQUPMENT_ID + 1);

            final List<SpaceShipEquipmentEntity> plist = List.of(p1, p2);
            final List<SpaceShipEntity> shipList = List.of(chip1, chip2);

            List<Long> shipsIds = plist.stream().map(p -> p.getSpaceShipId()).distinct().toList();

            Mockito.when(spaceShipEquipmentRepository.findByShipEquipment(equipment)).thenReturn(plist);

            Mockito.when(spaceShipRepository.findAllById(shipsIds)).thenReturn(shipList);

            // when
            final List<SpaceShipDomain> listResultDomain = spaceShipService.getAllSpaceShipsByEquipment(equipment);

            // then
            Assertions.assertNotNull(listResultDomain);
            Assertions.assertEquals(shipList.size(), listResultDomain.size());
            Assertions.assertEquals(shipList.get(0).getId(), listResultDomain.get(0).getId());
            Assertions.assertEquals(shipList.get(1).getId(), listResultDomain.get(1).getId());
            Assertions.assertEquals(listResultDomain.get(0).getEquipment().get(0).getShipEquipment(), equipment);
            Assertions.assertEquals(listResultDomain.get(1).getEquipment().get(0).getShipEquipment(), equipment);
        }

    }

    /**
     * Tests for findById method
     */
    @Nested
    class findByIdTest {

        @Test
        void givenNullId_thenThrowException() {

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.findById(null));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

        }

        @Test
        void givenNotMatchingId_thenThrowNotFoundException() {

            Mockito.when(spaceShipRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

            final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                    () -> spaceShipService.findById(SpaceShipFactory.SPACE_SHIP_ID));
            Assertions.assertEquals("Not found SpaceShip with id " + SpaceShipFactory.SPACE_SHIP_ID, ex.getMessage());

        }

        @Test
        void givenMatchingId_thenReturnsTheSpaceShip() {
            // given
            //MockSecurity.setMockUserInTest(MockSecurity.getUser(Role.USER));

            final SpaceShipEntity chip1 = SpaceShipFactory.getEntity();

            Mockito.when(spaceShipRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(chip1));

            SpaceShipDomain hd = SpaceShipDataBaseMapper.INSTANCE.entityToDomain(chip1);

            // when
            final SpaceShipDomain resultDomain = spaceShipService.findById(SpaceShipFactory.SPACE_SHIP_ID);

            // then
            Assertions.assertNotNull(resultDomain);
            Assertions.assertEquals(chip1.getId(), resultDomain.getId());
            Assertions.assertEquals(chip1.getName(), resultDomain.getName());
            Assertions.assertEquals(chip1.getEquipment().size(), resultDomain.getEquipment().size());
            Assertions.assertEquals(chip1.getEquipment().get(0).getShipEquipment(),
                    resultDomain.getEquipment().get(0).getShipEquipment());

        }

    }

    @Nested
    class addEquipmentTest {

        @Test
        void givenNullParameters_ThenThrowsException() {
            //given

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.addEquipment(null, Equipment.INVULNERABILITY_SHIELD));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.addEquipment(SpaceShipFactory.SPACE_SHIP_ID, null));
            Assertions.assertEquals(EQUIPMENT_MANDATORY, ex2.getMessage());

        }

        @Test
        void givenNonExistingSpaceShip_ThenThrowsNotFoundException() {
            //given

            Mockito.when(spaceShipRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

            final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                    () -> spaceShipService.addEquipment(SpaceShipFactory.SPACE_SHIP_ID, Equipment.INVULNERABILITY_SHIELD));
            Assertions.assertEquals("Not found SpaceShip with id " + SpaceShipFactory.SPACE_SHIP_ID, ex.getMessage());

        }

        @Test
        void givenExistingSpaceShip_WhenAlreadyHasThatShipEquipment_ThenThrowsAlreadyExistException() {
            //given

            final SpaceShipEntity chip1 = SpaceShipFactory.getEntity();

            Mockito.when(spaceShipRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(chip1));

            final AlreadyExistException ex = Assertions.assertThrows(AlreadyExistException.class,
                    () -> spaceShipService.addEquipment(SpaceShipFactory.SPACE_SHIP_ID, Equipment.INVULNERABILITY_SHIELD));
            Assertions.assertEquals("This SpaceShip already has that ship equipment: " + Equipment.INVULNERABILITY_SHIELD,
                    ex.getMessage());

        }

        @Test
        void givenExistingSpaceShip_WhenDoesNotHaveThatShipEquipment_ThenAddShipEquipment() {
            //given
            final SpaceShipEntity chip1 = SpaceShipFactory.getEntity();

            Mockito.when(spaceShipRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(chip1));

            //when
            SpaceShipDomain result = spaceShipService.addEquipment(SpaceShipFactory.SPACE_SHIP_ID,
                    Equipment.EMP_ELECTROMAGNETIC_PULSE_GENERATOR);

            //then
            Mockito.verify(spaceShipEquipmentRepository, Mockito.times(1))
                    .saveAndFlush(shipEquipmentEntityArgumentCaptor.capture());
            Assertions.assertEquals(Equipment.EMP_ELECTROMAGNETIC_PULSE_GENERATOR,
                    shipEquipmentEntityArgumentCaptor.getValue().getShipEquipment());
            Assertions.assertNotNull(result);
            List<Equipment> equipList = result.getEquipment().stream().map(p -> p.getShipEquipment()).distinct()
                    .toList();
            //TODO
            // Has to Mock spaceShipRepository.findById the second time to return an updated spaceShip
            //Assertions.assertTrue(equipList.contains(Equipment.EXTRAORDINARY_INTELLIGENCE));
        }

    }

    @Nested
    class createSpaceShipTest {

        @Test
        void givenNullParameters_ThenThrowsException() {
            //given
            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.createSpaceShip(null));
            Assertions.assertEquals(SPACE_SHIP_MANDATORY, ex.getMessage());

        }

        @Test
        void givenNotNullDto_WhenDtoIsNotValid_ThenThrowsException() {
            //given
            SpaceShipDTO noName = SpaceShipFactory.getDTO();
            noName.setName(null);
            SpaceShipDTO noShipEquipment = SpaceShipFactory.getDTO();
            noShipEquipment.setEquipment(null);
            SpaceShipDTO alreadyExisting = SpaceShipFactory.getDTO();
            SpaceShipEntity alreadyExistingE = SpaceShipFactory.getEntity();
            alreadyExisting.setName("alreadyExisting");
            alreadyExistingE.setName("alreadyExisting");

            Mockito.when(spaceShipRepository.findFirstByNameIgnoreCase("alreadyExisting"))
                    .thenReturn(Optional.of(alreadyExistingE));
            Mockito.when(spaceShipRepository.findFirstByNameIgnoreCase(noShipEquipment.getName())).thenReturn(Optional.empty());

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.createSpaceShip(noName));
            Assertions.assertEquals(NAME_EMPTY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.createSpaceShip(noShipEquipment));
            Assertions.assertEquals(EQUIPMENT_EMPTY, ex2.getMessage());

            final AlreadyExistException ex3 = Assertions.assertThrows(AlreadyExistException.class,
                    () -> spaceShipService.createSpaceShip(alreadyExisting));
            Assertions.assertTrue(ex3.getMessage().contains("This SpaceShip already exists"));

        }

        @Test
        void givenValidatedDto_ThenCreateSpaceShip() {
            //given

            SpaceShipDTO validDto = SpaceShipFactory.getDTO();
            SpaceShipEntity validEntity = SpaceShipFactory.getEntity();

            Mockito.when(spaceShipRepository.findFirstByNameIgnoreCase(ArgumentMatchers.anyString()))
                    .thenReturn(Optional.empty());
            Mockito.when(spaceShipRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(validEntity));

            //when
            spaceShipService.createSpaceShip(validDto);

            //then
            Mockito.verify(spaceShipRepository, Mockito.times(1)).saveAndFlush(shipEntityArgumentCaptor.capture());
            Mockito.verify(spaceShipEquipmentRepository, Mockito.times(1)).saveAllAndFlush(ArgumentMatchers.any());

            Assertions.assertEquals(validDto.getId(), shipEntityArgumentCaptor.getValue().getId());
            Assertions.assertEquals(validDto.getName(), shipEntityArgumentCaptor.getValue().getName());
            Assertions.assertEquals(validDto.getMediaShow(), shipEntityArgumentCaptor.getValue().getMediaShow());
        }

    }

    @Nested
    class updateSpaceShipTest {

        @Test
        void givenNullParameters_ThenThrowsException() {
            //given
            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.updateSpaceShip(null, SpaceShipFactory.getDTO()));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.updateSpaceShip(SpaceShipFactory.SPACE_SHIP_ID, null));
            Assertions.assertEquals(SPACE_SHIP_MANDATORY, ex2.getMessage());

        }

        @Test
        void givenNotNullDto_WhenDtoIsNotValid_ThenThrowsException() {
            //given
            SpaceShipDTO noName = SpaceShipFactory.getDTO();
            noName.setName(null);
            SpaceShipDTO noShipEquipment = SpaceShipFactory.getDTO();
            noShipEquipment.setEquipment(null);
            SpaceShipDTO alreadyExisting = SpaceShipFactory.getDTO();
            SpaceShipEntity alreadyExistingE = SpaceShipFactory.getEntity();
            alreadyExisting.setName("alreadyExisting");
            alreadyExistingE.setName("alreadyExisting");

            Mockito.when(spaceShipRepository.findFirstByNameIgnoreCase("alreadyExisting"))
                    .thenReturn(Optional.of(alreadyExistingE));
            Mockito.when(spaceShipRepository.findFirstByNameIgnoreCase(noShipEquipment.getName())).thenReturn(Optional.empty());
            Mockito.when(spaceShipRepository.findById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.of(alreadyExistingE));

            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.updateSpaceShip(null, SpaceShipFactory.getDTO()));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

            final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.updateSpaceShip(SpaceShipFactory.SPACE_SHIP_ID, noName));
            Assertions.assertEquals(NAME_EMPTY, ex2.getMessage());

            final BusinessRuleViolatedException ex3 = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.updateSpaceShip(SpaceShipFactory.SPACE_SHIP_ID, noShipEquipment));
            Assertions.assertEquals(EQUIPMENT_EMPTY, ex3.getMessage());

            //TODO Must fix inmutable collection when Mock
			/*final AlreadyExistException ex4 = Assertions.assertThrows(AlreadyExistException.class,
					() -> spaceShipsService.updateSpaceShip(SpaceShipFactory.SPACE_SHIP_ID,alreadyExisting));
			Assertions.assertTrue(ex4.getMessage().contains("This SpaceShip already exists"));*/

        }

        @Test
        @Disabled
        void givenValidatedDto_ThenUpdatesSpaceShip() {
            //given

            SpaceShipDTO validDto = SpaceShipFactory.getDTO();
            SpaceShipEntity validEntity = SpaceShipFactory.getEntity();

            Mockito.when(spaceShipRepository.findFirstByNameIgnoreCase(ArgumentMatchers.anyString()))
                    .thenReturn(Optional.empty());
            Mockito.when(spaceShipRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(validEntity));
            //TODO Must fix inmutable collection when Mock
            //when
            spaceShipService.updateSpaceShip(SpaceShipFactory.SPACE_SHIP_ID, validDto);

            //then
            Mockito.verify(spaceShipRepository, Mockito.times(1)).saveAndFlush(shipEntityArgumentCaptor.capture());
            Mockito.verify(spaceShipEquipmentRepository, Mockito.times(1)).saveAllAndFlush(ArgumentMatchers.any());

            Assertions.assertEquals(validDto.getId(), shipEntityArgumentCaptor.getValue().getId());
            Assertions.assertEquals(validDto.getName(), shipEntityArgumentCaptor.getValue().getName());
            Assertions.assertEquals(validDto.getMediaShow(), shipEntityArgumentCaptor.getValue().getMediaShow());
        }

    }

    /**
     * deleteSpaceShipById test cases
     */
    @Nested
    @DisplayName("deleteSpaceShipById test cases")
    class deleteSpaceShipByIdTest {
        @Test
        @DisplayName("Throws exception when spaceShip ID is null")
        void givenNullId_thenThrowsException() {
            final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
                    () -> spaceShipService.deleteSpaceShipById(null));
            Assertions.assertEquals(ID_MANDATORY, ex.getMessage());
        }

        @Test
        @DisplayName("Throws exception when spaceShip ID does not exist")
        void givenNonExistingId_thenThrowsException() {

            Mockito.when(spaceShipRepository.findById(SpaceShipFactory.SPACE_SHIP_ID)).thenReturn(Optional.empty());

            final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                    () -> spaceShipService.deleteSpaceShipById(SpaceShipFactory.SPACE_SHIP_ID));
            Assertions.assertEquals("Not found SpaceShip with id " + SpaceShipFactory.SPACE_SHIP_ID, ex.getMessage());
        }

        @Test
        void givenExistingSpaceShip_thenDeletesIt() {

            final SpaceShipEntity spaceShipToDelete = SpaceShipFactory.getEntity();

            Mockito.when(spaceShipRepository.findById(SpaceShipFactory.SPACE_SHIP_ID)).thenReturn(Optional.of(spaceShipToDelete));

            spaceShipService.deleteSpaceShipById(SpaceShipFactory.SPACE_SHIP_ID);

            Mockito.verify(spaceShipEquipmentRepository, Mockito.times(1)).deleteAllBySpaceShipId(SpaceShipFactory.SPACE_SHIP_ID);
            Mockito.verify(spaceShipRepository, Mockito.times(1)).deleteById(SpaceShipFactory.SPACE_SHIP_ID);

        }

    }

    /**
     * deleteAllSpaceShips test cases
     */
    @Nested
    @DisplayName("deleteAllSpaceShips test cases")
    class deleteAllSpaceShips {
        @Test
        @DisplayName("Does nothing when no existing registers")
        void givenNonExistingRegisters_thenDoNothing() {

            Mockito.when(spaceShipEquipmentRepository.findAll()).thenReturn(List.of());
            Mockito.when(spaceShipRepository.findAll()).thenReturn(List.of());

            spaceShipService.deleteAllSpaceShips();

            Mockito.verify(spaceShipEquipmentRepository, Mockito.times(0)).deleteAll();
            Mockito.verify(spaceShipRepository, Mockito.times(0)).deleteAll();
        }

        @Test
        @DisplayName("Deletes all when existing registers")
        void givenExistingRegisters_thenDeletesAll() {

            final SpaceShipEntity spaceShipToDelete = SpaceShipFactory.getEntity();
            final SpaceShipEquipmentEntity equipmentToDelete = SpaceShipFactory.getSpaceShipEquipment(SpaceShipFactory.SPACE_SHIP_ID);

            Mockito.when(spaceShipEquipmentRepository.findAll()).thenReturn(List.of(equipmentToDelete));
            Mockito.when(spaceShipRepository.findAll()).thenReturn(List.of(spaceShipToDelete));

            spaceShipService.deleteAllSpaceShips();

            Mockito.verify(spaceShipEquipmentRepository, Mockito.times(1)).deleteAll();
            Mockito.verify(spaceShipRepository, Mockito.times(1)).deleteAll();

        }

    }

}
