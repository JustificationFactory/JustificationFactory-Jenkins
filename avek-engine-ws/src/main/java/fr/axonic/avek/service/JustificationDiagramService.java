package fr.axonic.avek.service;

import fr.axonic.avek.engine.StepToCreate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface JustificationDiagramService {

    @POST
    @Path("/{justification_system_id}/{pattern_id}/step")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response constructStep(@PathParam("justification_system_id") String justificationSystem, @PathParam("pattern_id") String pattern, StepToCreate step);

    @DELETE
    @Path("/{justification_system_id}/justificationDiagram")
    Response clearSteps(@PathParam("justification_system_id") String justificationSystemId);


    @GET
    @Path("/type")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTypeContent(@QueryParam("type") String type);


}
