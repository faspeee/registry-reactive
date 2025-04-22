package com.example.stream.quarkus.registry.controller;

import com.example.stream.quarkus.registry.model.request.AddressRequestDto;
import com.example.stream.quarkus.registry.model.response.AddressResponseDto;
import com.example.stream.quarkus.registry.service.AddressService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
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
    public Uni<Response> getAddressById(@QueryParam("addressId") String addressId) {
        return processTheResultFromService(addressService.getAddressById(addressId), Response.Status.OK);
    }

    @GET
    @Path("/getAllAddress")
    @ResponseStatus(200)
    public Multi<Set<AddressResponseDto>> getAllAddress() {
        return addressService.getAllAddress();
    }

    @POST
    @Path("/createAddress")
    public Uni<Response> createAddress(AddressRequestDto addressRequestDto) {
        return processTheResultFromService(addressService.createAddress(addressRequestDto), Response.Status.CREATED);
    }

    @PUT
    @Path("/updateAddress")
    public Uni<Response> updateAddress(@QueryParam("addressId") String addressId, AddressRequestDto addressRequestDto) {
        return processTheResultFromService(addressService.updateAddress(addressId, addressRequestDto), Response.Status.OK);
    }

    @DELETE
    @Path("/deleteAddress")
    public Uni<Response> deleteAddress(@QueryParam("addressId") String addressId) {
        return processEmptyResponse(addressService.deleteAddress(addressId));
    }
}
