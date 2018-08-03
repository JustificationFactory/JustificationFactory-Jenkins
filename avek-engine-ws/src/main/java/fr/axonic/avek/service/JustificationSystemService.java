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


    @POST
    @Path("/system/{argumentation_system_name}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerArgumentationSystem(@PathParam("argumentation_system_name") String name,JustificationSystem justificationSystem);

    @POST
    @Path("/system")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerArgumentationSystem(JustificationSystem justificationSystem);

    @POST
    @Path("/{argumentation_system_id}/pattern")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerPattern(@PathParam("argumentation_system_id") String argumentationSystemId, Pattern pattern);


    @DELETE
    @Path("/{argumentation_system_id}")
    Response removeArgumentationSystem(@PathParam("argumentation_system_id") String argumentationSystemId);

    @DELETE
    @Path("/{argumentation_system_id}/justificationDiagram")
    Response removeStepsInArgumentationSystem(@PathParam("argumentation_system_id") String argumentationSystemId);


    @GET
    @Path("/systems")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArgumentationSystems();

    @GET
    @Path("/{argumentation_system_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArgumentationSystem(@PathParam("argumentation_system_id") String argumentationSystemId);

    @GET
    @Path("/{argumentation_system_id}/patterns")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArgumentationSystemPatterns(@PathParam("argumentation_system_id") String argumentationSystemId);

    @GET
    @Path("/{argumentation_system_id}/patterns/{pattern_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArgumentationSystemPattern(@PathParam("argumentation_system_id") String argumentationSystemId, @PathParam("pattern_id") String pattern);


    @POST
    @Path("/{argumentation_system_id}/{pattern_id}/step")
    //@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_XML})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response constructStep(@PathParam("argumentation_system_id") String argumentationSystem, @PathParam("pattern_id") String pattern, StepToCreate step);

    /**
     *
     * @param artifact a valid ArtifactType
     * @return a response
     * @see ArtifactType
     */
    @GET
    @Path("/types/{argumentation_artifact}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArtifactTypes(@PathParam("argumentation_artifact") String artifact);


    @GET
    @Path("/{argumentation_system_id}/types")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArtifactTypesUsable(@PathParam("argumentation_system_id") String argumentationSystem);


    @GET
    @Path("/classType")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTypeContent(@QueryParam("classType") String type);

}
