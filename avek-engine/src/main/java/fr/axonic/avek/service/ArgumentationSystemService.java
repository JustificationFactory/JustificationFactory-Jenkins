package fr.axonic.avek.service;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.SupportRole;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by cduffau on 16/01/17.
 */
public interface ArgumentationSystemService {

    @POST
    @Path("/system/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerArgumentationSystem(@QueryParam("argumentation_system") ArgumentationSystemAPI argumentationSystem);

    @DELETE
    @Path("/{argumentation_system_id}")
    Response removeArgumentationSystem(@PathParam("argumentation_system_id") String argumentationSystemId);

    @GET
    @Path("/{argumentation_system_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArgumentation(@PathParam("argumentation_system_id") String argumentationSystemId);

    @POST
    @Path("/step/{argumentation_system_id}/{pattern_id}")
    Response constructStep(@PathParam("argumentation_system_id") String argumentationSystem, @PathParam("pattern_id") String pattern, @QueryParam("evidences") List<SupportRole> evidences, @QueryParam("conclusion") Conclusion conclusion);

}
