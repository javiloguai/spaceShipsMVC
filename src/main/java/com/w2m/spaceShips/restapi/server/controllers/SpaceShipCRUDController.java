package com.w2m.spaceShips.restapi.server.controllers;

import com.w2m.spaceShips.config.aspects.annotations.LogExecutionTime;
import com.w2m.spaceShips.constants.RequestMappings;
import com.w2m.spaceShips.model.domain.SpaceShipDomain;
import com.w2m.spaceShips.model.enums.Equipment;
import com.w2m.spaceShips.restapi.server.assemblers.SpaceShipModelAssembler;
import com.w2m.spaceShips.restapi.server.mappers.SpaceShipRequestMapper;
import com.w2m.spaceShips.restapi.server.mappers.SpaceShipResponseMapper;
import com.w2m.spaceShips.restapi.server.requests.SpaceShipRequest;
import com.w2m.spaceShips.restapi.server.responses.SpaceShipResponse;
import com.w2m.spaceShips.restapi.services.SpaceShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author javiloguai
 */
@EqualsAndHashCode(callSuper = true)
@RestController
@RequestMapping(RequestMappings.API + RequestMappings.SPACESHIPS)
public class SpaceShipCRUDController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(SpaceShipCRUDController.class);

    private final SpaceShipService spaceShipService;

    private final PagedResourcesAssembler<SpaceShipDomain> pagedResourcesAssembler;

    private final SpaceShipModelAssembler spaceShipModelAssembler;

    public SpaceShipCRUDController(final SpaceShipService spaceShipService, PagedResourcesAssembler<SpaceShipDomain> pagedResourcesAssembler, final SpaceShipModelAssembler spaceShipModelAssembler, final MessageSource messageSource) {
        super(messageSource);
        this.spaceShipService = spaceShipService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.spaceShipModelAssembler = spaceShipModelAssembler;
    }

    /**
     * Some javadoc here
     *
     * @return List
     */
    @GetMapping
    //@ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Gets All the spaceships", description = "Gets All the spaceships.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of spaceships", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SpaceShipResponse.class)))),
            @ApiResponse(responseCode = "204", description = "EmptyList. Any spaceship found.", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema())})})
    @LogExecutionTime
    public ResponseEntity<List<SpaceShipResponse>> getAllSpaceShips() {
        LOGGER.debug("We can log whatever we need...");
        List<SpaceShipDomain> allships = this.spaceShipService.getAllSpaceShips();
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. " + "I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allships.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(SpaceShipResponseMapper.INSTANCE.toResponses(allships), HttpStatus.OK);

    }

    @GetMapping("/page")
    @Operation(summary = "Pages All the spaceships", description = "Gets All the spaceships paginated.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of spaceships", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SpaceShipResponse.class)))),
            @ApiResponse(responseCode = "204", description = "EmptyList. Any spaceship found.", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema())})})
    @LogExecutionTime
    public ResponseEntity<PagedModel<SpaceShipResponse>> pageAllSpaceShips(@ParameterObject final Pageable pageable) {
        LOGGER.debug("We can log whatever we need...");

        Page<SpaceShipDomain> allships = this.spaceShipService.pageAllSpaceShips(pageable);
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. " + "I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allships.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        final PagedModel<SpaceShipResponse> response = pagedResourcesAssembler.toModel(allships, spaceShipModelAssembler);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * Some javadoc here
     *
     * @return List
     */
    @GetMapping("/byName/page")
    @Operation(summary = "Page By Name", description = "Pages All the spaceships by name. The name ignores case sensitive.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of spaceships", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SpaceShipResponse.class)))),
            @ApiResponse(responseCode = "204", description = "EmptyList. Any spaceship found.", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema())})})
    @LogExecutionTime
    public ResponseEntity<PagedModel<SpaceShipResponse>> pageAllSpaceShipsByName(@RequestParam(required = true) String name,
                                                                                 @ParameterObject final Pageable pageable) {
        LOGGER.debug("We can log whatever we need...");
        Page<SpaceShipDomain> allships = this.spaceShipService.pageAllSpaceShipsByName(name, pageable);

        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allships.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        final PagedModel<SpaceShipResponse> response = pagedResourcesAssembler.toModel(allships, spaceShipModelAssembler);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/byShipEquipment")
    @Operation(summary = "Gets by shipEquipment", description = "Gets All the spaceships with a particular Ship Equipment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of spaceships", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SpaceShipResponse.class)))),
            @ApiResponse(responseCode = "204", description = "EmptyList. Any spaceship found.", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema())})})
    @LogExecutionTime
    public ResponseEntity<List<SpaceShipResponse>> getAllSpaceShipsByShipEquipment(
            @RequestParam(required = true) Equipment shipEquipment) {
        LOGGER.debug("We can log whatever we need...");
        List<SpaceShipDomain> allships = this.spaceShipService.getAllSpaceShipsByEquipment(shipEquipment);
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allships.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(SpaceShipResponseMapper.INSTANCE.toResponses(allships), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id", description = "Gets a spaceship by its id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of the spaceship", content = {
                    @Content(schema = @Schema(implementation = SpaceShipResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Any spaceship found with the given Id.", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema())})})
    @LogExecutionTime
    public ResponseEntity<SpaceShipResponse> getSpaceShipById(@PathVariable(value = "id", required = true) Long id) {
        LOGGER.debug("We can log whatever we need...");
        SpaceShipDomain ship = spaceShipService.findById(id);
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        return new ResponseEntity<>(SpaceShipResponseMapper.INSTANCE.toResponse(ship), HttpStatus.OK);
    }

    /**
     * Creates a new spaceship
     *
     * @param spaceShipRequest the spaceship request data
     * @return the user created
     */
    @Operation(summary = "Create SpaceShip", description = "This api is used to Creates a new ship into DB")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "SpaceShip successfully added to the DB", content = {
            @Content(schema = @Schema(implementation = SpaceShipResponse.class))}),
            @ApiResponse(responseCode = "406", description = "SpaceShip equipment list cannot be empty", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "409", description = "SpaceShip Already exist with given name", content = {
                    @Content(schema = @Schema())})})
    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    @LogExecutionTime
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SpaceShipResponse> createSpaceShip(@Valid @RequestBody final SpaceShipRequest spaceShipRequest) {

        LOGGER.debug(String.format("Init - Create new spaceship %s", spaceShipRequest.toString()));
        SpaceShipDomain spaceship = spaceShipService.createSpaceShip(
                SpaceShipRequestMapper.INSTANCE.fromRequestToDto(spaceShipRequest));

        LOGGER.debug(String.format("End - Create new spaceship %s", Objects.isNull(spaceship) ? null : spaceship.toString()));
        return new ResponseEntity<>(SpaceShipResponseMapper.INSTANCE.toResponse(spaceship), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Update SpaceShip", description = "Updates an existing spaceship details by providing the id.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "SpaceShip details are successfully updated into the DB", content = {
                    @Content(schema = @Schema(implementation = SpaceShipResponse.class))}),
            @ApiResponse(responseCode = "409", description = "SpaceShip Already exist with given name", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "406", description = "SpaceShip equipment list cannot be empty", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "SpaceShip not found for the given id", content = {
                    @Content(schema = @Schema())})})
    @LogExecutionTime
    public ResponseEntity<SpaceShipResponse> updateSpaceShip(@PathVariable(value = "id", required = true) Long id,
                                                             @RequestBody SpaceShipRequest spaceShipRequest) {
        LOGGER.debug(String.format("Init - Update existing spaceship %s", spaceShipRequest.toString()));
        SpaceShipDomain spaceship = spaceShipService.updateSpaceShip(id,
                SpaceShipRequestMapper.INSTANCE.fromRequestToDto(spaceShipRequest));

        LOGGER.debug(String.format("End - Update existing spaceship %s", Objects.isNull(spaceship) ? null : spaceship.toString()));
        return new ResponseEntity<>(SpaceShipResponseMapper.INSTANCE.toResponse(spaceship), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Add ShipEquipment", description = "Adds a equipment to an existing spaceship by providing its id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "SpaceShip equipment is added", content = {
            @Content(schema = @Schema(implementation = SpaceShipResponse.class))}),
            @ApiResponse(responseCode = "404", description = "SpaceShip not found for the given id", content = {
                    @Content(schema = @Schema())})})
    @LogExecutionTime
    public ResponseEntity<SpaceShipResponse> addShipEquipmentToSpaceShip(@PathVariable(value = "id", required = true) Long id,
                                                                         @RequestParam(required = true) Equipment shipEquipment) {
        LOGGER.debug(String.format("Init - Add extra equipment to spaceship with id %s", id));
        SpaceShipDomain spaceship = spaceShipService.addEquipment(id, shipEquipment);

        LOGGER.debug(String.format("End - Extra equipment to spaceship %s", Objects.isNull(spaceship) ? null : spaceship.toString()));
        return new ResponseEntity<>(SpaceShipResponseMapper.INSTANCE.toResponse(spaceship), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Deletes SpaceShip", description = "Deletes a spaceship by its id")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "SpaceShip is deleted", content = {
            @Content(schema = @Schema(implementation = SpaceShipResponse.class))}),
            @ApiResponse(responseCode = "404", description = "SpaceShip not found for the given id", content = {
                    @Content(schema = @Schema())})})
    @LogExecutionTime
    public ResponseEntity<HttpStatus> deleteSpaceShip(@PathVariable("id") long id) {

        LOGGER.debug(String.format("Init - Delete spaceship with id %s", id));
        spaceShipService.deleteSpaceShipById(id);

        LOGGER.debug(String.format("End -  Delete spaceship with id %s", id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Delete all spaceships", description = "Deletes all spaceships")
    @ApiResponse(responseCode = "204", description = "SpaceShip is deleted or does nothing if SpaceShip's list is empty", content = {
            @Content(schema = @Schema())})
    @LogExecutionTime
    public ResponseEntity<HttpStatus> deleteAllSpaceShips() {
        LOGGER.debug("Init - Delete All ships");
        spaceShipService.deleteAllSpaceShips();

        LOGGER.debug("End - Delete All ships");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
