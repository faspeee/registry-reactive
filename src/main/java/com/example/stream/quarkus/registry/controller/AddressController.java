package com.example.stream.quarkus.registry.controller;

import com.example.stream.quarkus.registry.model.request.AddressRequestDto;
import com.example.stream.quarkus.registry.model.response.AddressResponseDto;
import com.example.stream.quarkus.registry.service.AddressService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
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

@Path("/address")
public final class AddressController {


    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GET
    @Path("/getAddressById")
    @Operation(summary = "Get address by ID", description = "Fetch an address by its ID.")
    @APIResponse(responseCode = "200", description = "Successfully retrieved the address",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressResponseDto.class)))
    @APIResponse(responseCode = "404", description = "Address not found")
    public Uni<Response> getAddressById(@Parameter(description = "The ID of the address to retrieve")
                                        @QueryParam("addressId") String addressId) {
        return processTheResultFromService(addressService.getAddressById(addressId), Response.Status.OK);
    }

    @GET
    @Path("/getAllAddress")
    @ResponseStatus(200)
    @Operation(summary = "Get all addresses", description = "Fetch all addresses.")
    @APIResponse(responseCode = "200", description = "Successfully retrieved the list of addresses",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressResponseDto.class)))
    public Multi<Set<AddressResponseDto>> getAllAddress() {
        return addressService.getAllAddress();
    }

    @POST
    @Path("/createAddress")
    @Operation(summary = "Create a new address", description = "Create a new address record.")
    @APIResponse(responseCode = "201", description = "Address successfully created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressResponseDto.class)))
    @APIResponse(responseCode = "400", description = "Invalid input data")
    public Uni<Response> createAddress(AddressRequestDto addressRequestDto) {
        return processTheResultFromService(addressService.createAddress(addressRequestDto), Response.Status.CREATED);
    }

    @PUT
    @Path("/updateAddress")
    @Operation(summary = "Update an address", description = "Update the details of an existing address.")
    @APIResponse(responseCode = "200", description = "Successfully updated the address",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressResponseDto.class)))
    @APIResponse(responseCode = "404", description = "Address not found")
    public Uni<Response> updateAddress(@Parameter(description = "The ID of the address to update")
                                       @QueryParam("addressId") String addressId, AddressRequestDto addressRequestDto) {
        return processTheResultFromService(addressService.updateAddress(addressId, addressRequestDto), Response.Status.OK);
    }

    @DELETE
    @Path("/deleteAddress")
    @Operation(summary = "Delete an address", description = "Delete an address by its ID.")
    @APIResponse(responseCode = "200", description = "Address successfully deleted")
    @APIResponse(responseCode = "404", description = "Address not found")
    public Uni<Response> deleteAddress(@Parameter(description = "The ID of the address to delete")
                                       @QueryParam("addressId") String addressId) {
        return processEmptyResponse(addressService.deleteAddress(addressId));
    }


}
