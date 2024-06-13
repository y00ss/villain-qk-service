package io.quarkus.workshop.superheroes.villain;

import jakarta.inject.Inject;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/villains")
@Tag(name = "Villains")
public class VillainResource {

    @Inject
    VillainService villainService;

    @Inject
    Logger log;

    @GET
    @Produces(APPLICATION_JSON)
    @Operation(summary = "Returns all villain")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class, required = true))
    )
    @APIResponse(responseCode = "204", description = "There is no Villain on the database")
    public RestResponse<List<Villain>> getAllVillains() {
        List<Villain> villains = villainService.findAll();
        return villains.isEmpty() ? RestResponse.noContent() : RestResponse.ok(villains);
    }

    @GET
    @Path("/random")
    @Operation(summary = "Returns a random villain")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class, required = true))
    )
    public RestResponse<Villain> getRandomVillain() {
        Villain villain = villainService.findRandomVillain();
        log.debug("Found random villain " + villain);
        return RestResponse.ok(villain);
    }

    @GET
    @Path("id/{id}")
    @Produces(APPLICATION_JSON)
    @Operation(summary = "Returns villain by id")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class, required = true))
    )
    @APIResponse(responseCode = "204", description = "The villain is not found for a given identifier")
    public RestResponse<Villain> getVillainById(@RestPath("id") long id) {
        log.debug("request get by id : " + id);
        Villain villain = villainService.findVillainById(id);
        return villain == null ? RestResponse.noContent() : RestResponse.ok(villain);
    }

    @GET
    @Path("name/{name}")
    @Produces(APPLICATION_JSON)
    @Operation(summary = "Returns villain by name")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class, type = SchemaType.ARRAY, required = true))
    )
    @APIResponse(responseCode = "204", description = "The villain is not found for a given name")
    public RestResponse<List<Villain>> getVillainByName(@RestPath("name") String name) {
        log.debug("request get by name : " + name);
        List<Villain> villain = villainService.findVillainsByName(name);
        return villain.isEmpty() ? RestResponse.noContent() : RestResponse.ok(villain);
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Operation(summary = "Save a new villain")
    @APIResponse(
        responseCode = "201",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class, required = true))
    )
    @RequestBody(description = "Valid Villain ", required = true)
    public RestResponse<Villain> createVillain(@Valid Villain villain, @Context UriInfo uriInfo) {
        villain = villainService.createVillain(villain);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(villain.id));
        log.debug("New villain created with URI " + builder.build().toString());
        return RestResponse.created(builder.build());
    }

    @PUT
    @Produces(APPLICATION_JSON)
    @Operation(summary = "Update villain ")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class, required = true))
    )
    public RestResponse<Villain> updateVillain(@Valid Villain villain) {
        Villain villainUpd = villainService.updateVillain(villain);
        return RestResponse.ok(villainUpd);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete villain by name")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class, required = true))
    )
    public RestResponse<Void> deleteVillain(@PathParam("id") long id) {
        Villain villain = villainService.findVillainById(id);
        if (villain == null) {
            return RestResponse.notFound();
        }
        villainService.deleteVillainById(id);
        return RestResponse.ok(); //back the obj ?
    }

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Check the health of the service")
    public RestResponse<Void> hello() {
        return RestResponse.ok();
    }
}
