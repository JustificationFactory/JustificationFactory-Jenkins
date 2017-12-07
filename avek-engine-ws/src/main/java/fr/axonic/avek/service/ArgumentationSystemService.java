package fr.axonic.avek.service;

import fr.axonic.avek.engine.ArgumentationSystem;
import fr.axonic.avek.engine.ArgumentationSystemAPI;
import fr.axonic.avek.engine.StepToCreate;
import fr.axonic.avek.engine.pattern.Pattern;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by cduffau on 16/01/17.
 */
public interface ArgumentationSystemService {

    @POST
    @Path("/system")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerArgumentationSystem(ArgumentationSystem argumentationSystem);

    @POST
    @Path("/{argumentation_system_id}/pattern")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerPattern(@PathParam("argumentation_system_id") String argumentationSystemId, Pattern pattern);


    @DELETE
    @Path("/{argumentation_system_id}")
    Response removeArgumentationSystem(@PathParam("argumentation_system_id") String argumentationSystemId);

    @DELETE
    @Path("/{argumentation_system_id}/steps")
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
    @Path("{argumentation_system_id}/{pattern_id}/step")
    //@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_XML})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response constructStep(@PathParam("argumentation_system_id") String argumentationSystem, @PathParam("pattern_id") String pattern, StepToCreate step);

}
