package fr.axonic.avek.service;

import fr.axonic.avek.ArtifactType;
import fr.axonic.avek.engine.pattern.Pattern;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface JustificationPatternService {

    @POST
    @Path("/{justification_system_id}/pattern")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerPattern(@PathParam("justification_system_id") String justificationSystemId, Pattern pattern);

    @GET
    @Path("/{justification_system_id}/patterns")
    @Produces(MediaType.APPLICATION_JSON)
    Response getJustificationSystemPatterns(@PathParam("justification_system_id") String justificationSystemId);

    @GET
    @Path("/{justification_system_id}/patterns/{pattern_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getJustificationSystemPattern(@PathParam("justification_system_id") String justificationSystemId, @PathParam("pattern_id") String pattern);

    /**
     * @param artifact a valid ArtifactType
     * @return a response
     * @see ArtifactType
     */
    @GET
    @Path("/types/{justification_artifact}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArtifactTypes(@PathParam("justification_artifact") String artifact);

    @GET
    @Path("/{justification_system_id}/types")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArtifactTypesUsable(@PathParam("justification_system_id") String justificationSystem);

}
