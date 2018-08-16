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
    Response registerJustificationSystem(@PathParam("justification_system_name") String name, JustificationSystem justificationSystem);

    @POST
    @Path("/system")
    @Consumes(MediaType.APPLICATION_JSON)
    Response registerJustificationSystem(JustificationSystem justificationSystem);


    @DELETE
    @Path("/{justification_system_id}")
    Response removeJustificationSystem(@PathParam("justification_system_id") String justificationSystemId);

    @GET
    @Path("/systems")
    @Produces(MediaType.APPLICATION_JSON)
    Response getJustificationSystems();

    @GET
    @Path("/{justification_system_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getJustificationSystem(@PathParam("justification_system_id") String justificationSystemId);
}
