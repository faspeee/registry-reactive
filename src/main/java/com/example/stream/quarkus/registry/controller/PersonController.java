package com.example.stream.quarkus.registry.controller;

import com.example.stream.quarkus.registry.model.request.PersonRequestDto;
import com.example.stream.quarkus.registry.model.response.PersonResponseDto;
import com.example.stream.quarkus.registry.service.PersonService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.Set;

import static com.example.stream.quarkus.registry.utility.ProcessResponses.processEmptyResponse;
import static com.example.stream.quarkus.registry.utility.ProcessResponses.processTheResultFromService;

@Path("/person")
public final class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GET
    @Path("/getPersonById")
    @Operation(summary = "Get person by ID", description = "Fetch a person by their ID.")
    @APIResponse(responseCode = "200", description = "Successfully retrieved the person",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponseDto.class)))
    @APIResponse(responseCode = "404", description = "Person not found")
    public Uni<Response> getPersonById(@Parameter(description = "The ID of the person to retrieve")
                                       @QueryParam("personId") String personId) {
        return processTheResultFromService(personService.getPersonById(personId), Response.Status.OK);
    }

    @GET
    @Path("/getAllPeople")
    @ResponseStatus(200)
    @Operation(summary = "Get all people", description = "Fetch all people.")
    @APIResponse(responseCode = "200", description = "Successfully retrieved the list of people",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponseDto.class)))
    public Multi<Set<PersonResponseDto>> getAllPeople() {
        return personService.getAllPeople();
    }

    @POST
    @Path("/createPerson")
    @Operation(summary = "Create a new person", description = "Create a new person record.")
    @APIResponse(responseCode = "201", description = "Person successfully created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponseDto.class)))
    @APIResponse(responseCode = "400", description = "Invalid input data")
    public Uni<Response> createPerson(@Valid PersonRequestDto personRequestDto) {
        return processTheResultFromService(personService.createPerson(personRequestDto), Response.Status.CREATED);
    }

    @PUT
    @Path("/updatePerson")
    @Operation(summary = "Update a person", description = "Update the details of an existing person.")
    @APIResponse(responseCode = "200", description = "Successfully updated the person",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponseDto.class)))
    @APIResponse(responseCode = "404", description = "Person not found")
    public Uni<Response> updatePerson(@Parameter(description = "The ID of the person to update")
                                      @QueryParam("personId") String personId, PersonRequestDto personRequestDto) {
        return processTheResultFromService(personService.updatePerson(personId, personRequestDto), Response.Status.OK);
    }

    @DELETE
    @Path("/deletePerson")
    @Operation(summary = "Delete a person", description = "Delete a person by their ID.")
    @APIResponse(responseCode = "200", description = "Person successfully deleted")
    @APIResponse(responseCode = "404", description = "Person not found")
    public Uni<Response> deletePerson(@Parameter(description = "The ID of the person to delete")
                                      @QueryParam("personId") String personId) {
        return processEmptyResponse(personService.deletePerson(personId));
    }
}
