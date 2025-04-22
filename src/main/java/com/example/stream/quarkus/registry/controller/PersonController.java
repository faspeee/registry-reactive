package com.example.stream.quarkus.registry.controller;

import com.example.stream.quarkus.registry.model.request.PersonRequestDto;
import com.example.stream.quarkus.registry.model.response.PersonResponseDto;
import com.example.stream.quarkus.registry.service.PersonService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
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
    public Uni<Response> getPersonById(@QueryParam("personId") String personId) {
        return processTheResultFromService(personService.getPersonById(personId), Response.Status.OK);
    }

    @GET
    @Path("/getAllPeople")
    @ResponseStatus(200)
    public Multi<Set<PersonResponseDto>> getAllPeople() {
        return personService.getAllPeople();
    }

    @POST
    @Path("/createPerson")
    public Uni<Response> createPerson(@Valid PersonRequestDto personRequestDto) {
        return processTheResultFromService(personService.createPerson(personRequestDto), Response.Status.CREATED);
    }

    @PUT
    @Path("/updatePerson")
    public Uni<Response> updatePerson(@QueryParam("personId") String personId, PersonRequestDto personRequestDto) {
        return processTheResultFromService(personService.updatePerson(personId, personRequestDto), Response.Status.OK);
    }

    @DELETE
    @Path("/deletePerson")
    public Uni<Response> deletePerson(@QueryParam("personId") String personId) {
        return processEmptyResponse(personService.deletePerson(personId));
    }
}
