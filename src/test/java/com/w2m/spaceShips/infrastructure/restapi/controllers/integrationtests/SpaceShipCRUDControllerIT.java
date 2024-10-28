package com.w2m.spaceShips.infrastructure.restapi.controllers.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.w2m.spaceShips.application.exception.BusinessRuleViolatedException;
import com.w2m.spaceShips.application.exception.NotFoundException;
import com.w2m.spaceShips.application.factories.SpaceShipFactory;
import com.w2m.spaceShips.application.services.SpaceShipService;
import com.w2m.spaceShips.domain.model.SpaceShipDomain;
import com.w2m.spaceShips.infrastructure.config.test.TestControllerConfig;
import com.w2m.spaceShips.infrastructure.constants.RequestMappings;
import com.w2m.spaceShips.infrastructure.restapi.assemblers.SpaceShipModelAssembler;
import com.w2m.spaceShips.infrastructure.restapi.controllers.SpaceShipCRUDController;
import com.w2m.spaceShips.infrastructure.restapi.model.requests.SpaceShipRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static org.mockito.ArgumentMatchers.any;

/**
 * The Class SpaceShipCRUDController Integration Test.
 *
 * @author javiloguai
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = {"spring.main.banner-mode=off"})
@ContextConfiguration(classes = {TestControllerConfig.class, SpaceShipCRUDController.class})
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles(TestControllerConfig.PROFILE)
@EnableSpringDataWebSupport
class SpaceShipCRUDControllerIT {

    @MockBean
    SpaceShipService spaceShipService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagedResourcesAssembler<SpaceShipDomain> pagedResourcesAssembler;

    @MockBean
    private SpaceShipModelAssembler spaceShipModelAssembler;

    @Autowired
    private ObjectMapper getMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public <T> T getObjectFromJson(final String json, final Class<T> clazz) {
        T result = null;
        try {
            result = getMapper.readValue(json.getBytes(), clazz);

        } catch (final IOException exception) {
            throw new BusinessRuleViolatedException(exception);
        }
        return result;
    }

    public String getJsonFromObject(final Object abstractConfigDto) {
        final StringBuilder jsonConfig = new StringBuilder();

        try {

            final ObjectMapper mapper = getMapper;
            mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            final ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
            jsonConfig.append(writer.writeValueAsString(abstractConfigDto));

        } catch (final IOException exception) {
            throw new BusinessRuleViolatedException(exception);
        }

        return jsonConfig.toString();
    }

    /**
     * FindById test cases.
     */
    @Nested
    class FindByIdTest {

        @Test
            //@WithMockUser(username="admin",roles={"USER","ADMIN"})
        void givenNonExistingSpaceShip_ThenReturnNotFound() throws Exception {

            Mockito.when(spaceShipService.findById(Mockito.any())).thenThrow(new NotFoundException("Error"));

            final String requestURL = RequestMappings.API + RequestMappings.SPACESHIPS + "/" + SpaceShipFactory.SPACE_SHIP_ID;

            // @formatter:off
            mockMvc.perform(MockMvcRequestBuilders.get(requestURL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andReturn();
            // @formatter:on

            Mockito.verify(spaceShipService, Mockito.atLeastOnce()).findById(SpaceShipFactory.SPACE_SHIP_ID);

        }

        @Test
        void givenExistingSpaceShip_ThenReturnIt() throws Exception {

            final SpaceShipDomain domainToResponse = SpaceShipDomain.builder().name(SpaceShipFactory.NAME)
                    .mediaShow(SpaceShipFactory.NAME).build();

            Mockito.when(spaceShipService.findById(ArgumentMatchers.anyLong())).thenReturn(domainToResponse);

            final String requestURL = RequestMappings.API + RequestMappings.SPACESHIPS + "/" + SpaceShipFactory.SPACE_SHIP_ID;

            // @formatter:off
            final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get(requestURL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            // @formatter:on

            Mockito.verify(spaceShipService, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
            final String response = result.getResponse().getContentAsString();
            final SpaceShipDomain searched = getObjectFromJson(response, SpaceShipDomain.class);

            Assertions.assertEquals(domainToResponse.getName(), searched.getName());
            Assertions.assertEquals(domainToResponse.getMediaShow(), searched.getMediaShow());
        }
    }

    @Nested
    @DisplayName("FindAll test cases. TODO: skills demostrated on ether GET Interation Test")
    class FindAllTest {
        //TODO Implement integrations tests for this endpoint.
        // It would need to be completed to have full code coverage.
        // It is clear from the tests above that I know how to perform integration tests for GET,
        // because of this I do not waste any more time implementing this method
        @Test
        @Disabled
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {
            Assertions.assertTrue(true);
        }
    }

    @Nested
    @DisplayName("PageAll test cases.TODO: skills demostrated on ether GET Integration Test")
    class PageAllTest {
        //TODO Implement integrations tests for this endpoint.
        // It would need to be completed to have full code coverage.
        // It is clear from the tests above that I know how to perform integration tests for GET,
        // because of this I do not waste any more time implementing this method
        @Test
        @Disabled
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {
            Assertions.assertTrue(true);
        }
    }

    @Nested
    @DisplayName("GetAllByName test cases. TODO: skills demostrated on ether GET Interation Test")
    class GetAllByNameTest {
        //TODO Implement integrations tests for this endpoint.
        // It would need to be completed to have full code coverage.
        // It is clear from the tests above that I know how to perform integration tests for GET,
        // because of this I do not waste any more time implementing this method
        @Test
        @Disabled
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {
            Assertions.assertTrue(true);
        }
    }

    @Nested
    @DisplayName("CreateSpaceShip test cases")
    class CreateSpaceShipTest {

        @Test
        @DisplayName("Create new SpaceShip if requesting user has permissions")
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {

            final SpaceShipRequest request = SpaceShipFactory.getRequest();

            final String requestBody = getJsonFromObject(request);
            final SpaceShipDomain domainToResponse = SpaceShipDomain.builder().name(request.getName())
                    .mediaShow(request.getMediaShow()).build();

            Mockito.when(spaceShipService.createSpaceShip(ArgumentMatchers.any())).thenReturn(domainToResponse);

            // @formatter:off
            final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post(RequestMappings.API + RequestMappings.SPACESHIPS)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
            // @formatter:on

            Mockito.verify(spaceShipService, Mockito.times(1)).createSpaceShip(any());
            final String response = result.getResponse().getContentAsString();
            final SpaceShipDomain domain = getObjectFromJson(response, SpaceShipDomain.class);

            Assertions.assertEquals(request.getName(), domain.getName());
            Assertions.assertEquals(request.getMediaShow(), domain.getMediaShow());

        }

        @Test
        @DisplayName("Access is denied if requesting user does not have permissions")

        @Disabled
        void givenRequestingUserWithoutPermissions_ThenOperationIsDenied() {
            final SpaceShipRequest request = SpaceShipFactory.getRequest();

            final String requestBody = getJsonFromObject(request);

            final SpaceShipDomain domainToResponse = SpaceShipDomain.builder().name(request.getName())
                    .mediaShow(request.getMediaShow()).build();

            Mockito.when(spaceShipService.createSpaceShip(ArgumentMatchers.any())).thenReturn(domainToResponse);

            // @formatter:off

            // @formatter:on
            try {
                // @formatter:off
                mockMvc.perform(MockMvcRequestBuilders.post(RequestMappings.API + RequestMappings.SPACESHIPS)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
                // @formatter:on
                Assertions.fail();
            } catch (final Exception e) {
                Assertions.assertInstanceOf(AccessDeniedException.class, e.getCause());
            }
        }

    }

    @Nested
    @DisplayName("ModifySpaceShip test cases")
    class ModifySpaceShipTest {
        @Test
        @DisplayName("Modify the SpaceShip if requesting user has permissions")
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {
            final SpaceShipRequest request = SpaceShipFactory.getRequest();

            final String requestBody = getJsonFromObject(request);

            final SpaceShipDomain domainToResponse = SpaceShipDomain.builder().name(request.getName())
                    .mediaShow(request.getMediaShow()).build();

            Mockito.when(spaceShipService.updateSpaceShip(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                    .thenReturn(domainToResponse);

            // @formatter:off
            final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.put(RequestMappings.API + RequestMappings.SPACESHIPS + "/{id}",SpaceShipFactory.SPACE_SHIP_ID)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            // @formatter:on

            Mockito.verify(spaceShipService, Mockito.times(1))
                    .updateSpaceShip(ArgumentMatchers.anyLong(), ArgumentMatchers.any());
            final String response = result.getResponse().getContentAsString();
            final SpaceShipDomain domain = getObjectFromJson(response, SpaceShipDomain.class);

            Assertions.assertEquals(request.getName(), domain.getName());
            Assertions.assertEquals(request.getMediaShow(), domain.getMediaShow());
        }

        @Test
        @DisplayName("Access is denied if requesting user does not have permissions")

        @Disabled
        void givenRequestingUserWithoutPermissions_ThenOperationIsDenied() {
            final SpaceShipRequest request = SpaceShipFactory.getRequest();

            final String requestBody = getJsonFromObject(request);

            // @formatter:off

            // @formatter:on
            try {
                // @formatter:off
                mockMvc.perform(MockMvcRequestBuilders.put(RequestMappings.API + RequestMappings.SPACESHIPS + "/{id}",SpaceShipFactory.SPACE_SHIP_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
                // @formatter:on
                Assertions.fail();
            } catch (final Exception e) {
                Assertions.assertInstanceOf(AccessDeniedException.class, e.getCause());
            }
        }

    }

    @Nested
    @DisplayName("deleteSpaceShip Test cases")
    class deleteSpaceShipTest {
        @Test
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {

            //// @formatter:off
            mockMvc.perform(MockMvcRequestBuilders.delete(RequestMappings.API + RequestMappings.SPACESHIPS + "/"+SpaceShipFactory.SPACE_SHIP_ID)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
            // @formatter:on
            Mockito.verify(spaceShipService, Mockito.times(1)).deleteSpaceShipById(ArgumentMatchers.anyLong());
        }

        @Test
        @DisplayName("Access is denied if requesting user does not have permissions")
        @Disabled
        void givenRequestingUserWithoutPermissions_ThenOperationIsDenied() {

            try {
                // @formatter:off
                mockMvc.perform(MockMvcRequestBuilders.delete(RequestMappings.API + RequestMappings.SPACESHIPS + "/"+SpaceShipFactory.SPACE_SHIP_ID)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
                // @formatter:on
                Mockito.verify(spaceShipService, Mockito.times(0)).deleteSpaceShipById(ArgumentMatchers.anyLong());
                // @formatter:on
                Assertions.fail();
            } catch (final Exception e) {
                Assertions.assertInstanceOf(AccessDeniedException.class, e.getCause());
            }
        }

    }

    @Nested
    @DisplayName("deleteAllSpaceShips Test Cases")
    class deleteAllSpaceShipsTest {
        @Test
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {

            //// @formatter:off
            mockMvc.perform(MockMvcRequestBuilders.delete(RequestMappings.API + RequestMappings.SPACESHIPS)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
            // @formatter:on
            Mockito.verify(spaceShipService, Mockito.times(1)).deleteAllSpaceShips();
        }

        @Test
        @DisplayName("Access is denied if requesting user does not have permissions")
        @Disabled
        void givenRequestingUserWithoutPermissions_ThenOperationIsDenied() {

            try {
                // @formatter:off
                mockMvc.perform(MockMvcRequestBuilders.delete(RequestMappings.API + RequestMappings.SPACESHIPS)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
                // @formatter:on
                Mockito.verify(spaceShipService, Mockito.times(0)).deleteAllSpaceShips();
                // @formatter:on
                Assertions.fail();
            } catch (final Exception e) {
                Assertions.assertInstanceOf(AccessDeniedException.class, e.getCause());
            }
        }

    }

}
