package fr.axonic.avek.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import fr.axonic.avek.dao.JerseyMapperProvider;
import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;
import fr.axonic.avek.engine.StepToCreate;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.JustificationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

@Path("/justification")
public class JustificationDiagramServiceImpl implements JustificationDiagramService {

    private static final Logger LOGGER= LoggerFactory.getLogger(JustificationDiagramServiceImpl.class);

    private Map<String, JustificationSystem> justificationSystems=JustificationSystemsBD.getInstance().getJustificationSystems();

    @Override
    public Response constructStep(String argumentationSystem, String pattern, StepToCreate step) {

        try {
            JustificationStep res = justificationSystems.get(argumentationSystem).constructStep(justificationSystems.get(argumentationSystem).getPatternsBase().getPattern(pattern),step.getSupports(),step.getConclusion());
            LOGGER.info("Step created on "+argumentationSystem+" with pattern "+pattern);
            try {
                JustificationSystemsDAO.saveJustificationSystem(argumentationSystem, justificationSystems.get(argumentationSystem));
            } catch (IOException e) {
                LOGGER.error(e.toString());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystem).build();
            }
            return Response.status(Response.Status.CREATED).entity(res).build();
        } catch (StepBuildingException | WrongEvidenceException | StrategyException e) {
            LOGGER.error("Error during Step creation on "+argumentationSystem+" with pattern "+pattern);
            LOGGER.error(e.toString());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getStackTrace()).build();
        }


    }

    @Override
    public Response clearSteps(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem = justificationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to remove");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        else {
            argumentationSystem.getJustificationDiagram().getSteps().clear();
            try {
                JustificationSystemsDAO.saveJustificationSystem(argumentationSystemId,argumentationSystem);
            } catch (IOException e) {
                LOGGER.error(e.toString());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
            }
            LOGGER.info(argumentationSystemId+" Justification System Justification Diagram removed");
            return Response.status(Response.Status.OK).build();
        }
    }

    @Override
    public Response getTypeContent(String type) {
        try {
            Class clas=Class.forName(type);
            JerseyMapperProvider jerseyMapperProvider=new JerseyMapperProvider();
            JsonSchema schema=jerseyMapperProvider.getContext(null).generateJsonSchema(clas);

            return Response.status(Response.Status.OK).entity(jerseyMapperProvider.getContext(null).writerWithDefaultPrettyPrinter().writeValueAsString(schema)).build();

        } catch (ClassNotFoundException | JsonProcessingException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getStackTrace()).build();

        }
    }
}
