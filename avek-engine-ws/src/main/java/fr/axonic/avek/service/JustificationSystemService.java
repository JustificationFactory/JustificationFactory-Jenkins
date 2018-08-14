package fr.axonic.avek.service;

import fr.axonic.avek.ArtifactType;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.StepToCreate;
import fr.axonic.avek.engine.pattern.Pattern;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by cduffau on 16/01/17.
 */
public interface JustificationSystemService {

    /**
     * Add a new justification system
     * @param name a justification system name
     * @param justificationSystem a justification system
     * @return registered status
     */
    @POST
    @Path("/system/{justification_system_name}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerArgumentationSystem(@PathParam("justification_system_name") String name, JustificationSystem justificationSystem);

    @POST
    @Path("/system")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerArgumentationSystem(JustificationSystem justificationSystem);

    @POST
    @Path("/{justification_system_id}/pattern")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerPattern(@PathParam("justification_system_id") String justificationSystemId, Pattern pattern);

    @DELETE
    @Path("/{justification_system_id}")
    Response removeArgumentationSystem(@PathParam("justification_system_id") String justificationSystemId);

    @DELETE
    @Path("/{justification_system_id}/justificationDiagram")
    Response removeStepsInArgumentationSystem(@PathParam("justification_system_id") String justificationSystemId);

    @GET
    @Path("/systems")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArgumentationSystems();

    @GET
    @Path("/{justification_system_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArgumentationSystem(@PathParam("justification_system_id") String justificationSystemId);

    @GET
    @Path("/{justification_system_id}/patterns")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArgumentationSystemPatterns(@PathParam("justification_system_id") String justificationSystemId);

    @GET
    @Path("/{justification_system_id}/patterns/{pattern_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArgumentationSystemPattern(@PathParam("justification_system_id") String justificationSystemId, @PathParam("pattern_id") String pattern);

    @POST
    @Path("/{justification_system_id}/{pattern_id}/step")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response constructStep(@PathParam("justification_system_id") String justificationSystem, @PathParam("pattern_id") String pattern, StepToCreate step);

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

    @GET
    @Path("/type")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTypeContent(@QueryParam("type") String type);
}
