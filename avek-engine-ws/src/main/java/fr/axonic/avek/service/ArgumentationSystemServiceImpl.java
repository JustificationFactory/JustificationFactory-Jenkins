package fr.axonic.avek.service;

import fr.axonic.avek.dao.ArgumentationSystemsDAO;
import fr.axonic.avek.engine.ArgumentationSystemAPI;
import fr.axonic.avek.engine.StepToCreate;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.instance.MockedArgumentationSystem;
import fr.axonic.validation.exception.VerificationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 16/01/17.
 */
@Path("/argumentation")
public class ArgumentationSystemServiceImpl implements ArgumentationSystemService {
    private static Map<String, ArgumentationSystemAPI> argumentationSystems;

    private static final Logger LOGGER= LoggerFactory.getLogger(ArgumentationSystemServiceImpl.class);

    static {
        argumentationSystems=new HashMap<>();
        try {
            argumentationSystems= ArgumentationSystemsDAO.loadArgumentationSystems();

           if(argumentationSystems.get("AXONIC")==null) {
               argumentationSystems.put("AXONIC", MockedArgumentationSystem.getAXONICArgumentationSystem());
               LOGGER.info("AXONIC Argumentation System added");
           }
           if(argumentationSystems.get("Jenkins")==null){
               argumentationSystems.put("Jenkins", MockedArgumentationSystem.getJenkinsArgumentationSystem());
               LOGGER.info("Jenkins Argumentation System added");
           }
        } catch (VerificationException | WrongEvidenceException | IOException e) {
            e.printStackTrace();
        }
    }
    public ArgumentationSystemServiceImpl(){

    }

    @Override
    public Response registerArgumentationSystem(ArgumentationSystemAPI argumentationSystem) {
        String id =UUID.randomUUID().toString();
        argumentationSystems.put(id,argumentationSystem);
        try {
            ArgumentationSystemsDAO.saveArgumentationSystem(id,argumentationSystem);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(id).build();
        }
        LOGGER.info(id+" Argumentation System added");
        return Response.status(Response.Status.ACCEPTED).entity(id).build();
    }

    @Override
    public Response removeArgumentationSystem(String argumentationSystemId) {
        if(argumentationSystems.remove(argumentationSystemId)==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to remove");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        try {
            ArgumentationSystemsDAO.removeArgumentationSystem(argumentationSystemId);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Argumentation System removed");
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response removeStepsInArgumentationSystem(String argumentationSystemId) {
        ArgumentationSystemAPI argumentationSystem = argumentationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to remove");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        else {
            argumentationSystem.removeSteps();
            try {
                ArgumentationSystemsDAO.saveArgumentationSystem(argumentationSystemId,argumentationSystem);
            } catch (IOException e) {
                LOGGER.error(e.toString());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
            }
            LOGGER.info(argumentationSystemId+" Argumentation System steps removed");
            return Response.status(Response.Status.OK).build();
        }
    }

    @Override
    public Response getArgumentationSystems() {
        Set<String> argumentationSystemsID=argumentationSystems.keySet();
        if(argumentationSystemsID.isEmpty()){
            LOGGER.warn("No argumentation systems");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation systems").build();
        }
        LOGGER.info("Argumentation systems provided");
        return Response.status(Response.Status.OK).entity(argumentationSystemsID).build();

    }

    @Override
    public Response getArgumentationSystem(String argumentationSystemId) {
        ArgumentationSystemAPI argumentationSystem=argumentationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to provide");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Argumentation System provided");
        return Response.status(Response.Status.OK).entity(argumentationSystem).build();
    }

    @Override
    public Response getArgumentationSystemPatterns(String argumentationSystemId) {
        ArgumentationSystemAPI argumentationSystem=argumentationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to provide");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Argumentation System patterns provided");
        return Response.status(Response.Status.OK).entity(argumentationSystem.getPatternsBase().getPatterns().stream().map(Pattern::getId).collect(Collectors.toList())).build();
    }

    @Override
    public Response getArgumentationSystemPattern(String argumentationSystemId, String pattern) {
        ArgumentationSystemAPI argumentationSystem=argumentationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to provide");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        LOGGER.info("Pattern "+pattern+" for "+argumentationSystemId+" Argumentation System  provided");
        return Response.status(Response.Status.OK).entity(argumentationSystem.getPatternsBase().getPattern(pattern)).build();

    }

    @Override
    public Response constructStep(String argumentationSystem, String pattern, StepToCreate step) {

        try {
            Step res = argumentationSystems.get(argumentationSystem).constructStep(argumentationSystems.get(argumentationSystem).getPattern(pattern),step.getSupports(),step.getConclusion());
            LOGGER.info("Step created on "+argumentationSystem+" with pattern "+pattern);
            try {
                ArgumentationSystemsDAO.saveArgumentationSystem(argumentationSystem,argumentationSystems.get(argumentationSystem));
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
}
