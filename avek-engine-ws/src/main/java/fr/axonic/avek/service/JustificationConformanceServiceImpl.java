package fr.axonic.avek.service;

import fr.axonic.avek.engine.JustificationSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/justification")
public class JustificationConformanceServiceImpl implements JustificationConformanceService {

    private static final Logger LOGGER= LoggerFactory.getLogger(JustificationConformanceServiceImpl.class);

    private Map<String, JustificationSystem> justificationSystems=JustificationSystemsBD.getInstance().getJustificationSystems();


    @Override
    public Response checkJustificationSystemCompleteness(String name) {
        boolean complete=justificationSystems.get(name).isComplete();
        return Response.status(Response.Status.OK).entity(complete).build();
    }

    @Override
    public Response compareJustificationDiagrams(String justificationDiagram, String justificationDiagram2) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @Override
    public Response deriveJustificationDiagrams(String justificationDiagram, String justificationDiagram2) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @Override
    public Response achieveJustificationDiagram(String justificationDiagram) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @Override
    public Response alignJustificationDiagrams(String justificationDiagram, String justificationDiagram2) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
}
