package fr.axonic.avek.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axonic.avek.engine.ArgumentationSystemAPI;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.instance.MockedArgumentationSystem;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
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
            argumentationSystems.put("AXONIC", MockedArgumentationSystem.getAXONICArgumentationSystem());
            LOGGER.info("AXONIC Argumentation System added");
        } catch (VerificationException | WrongEvidenceException e) {
            e.printStackTrace();
        }
    }
    public ArgumentationSystemServiceImpl(){

    }

    @Override
    public Response registerArgumentationSystem(ArgumentationSystemAPI argumentationSystem) {
        String id =UUID.randomUUID().toString();
        argumentationSystems.put(id,argumentationSystem);
        LOGGER.info(id+" Argumentation System added");
        return Response.status(Response.Status.ACCEPTED).entity(id).build();
    }

    @Override
    public Response removeArgumentationSystem(String argumentationSystemId) {
        if(argumentationSystems.remove(argumentationSystemId)==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to remove");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Argumentation System removed");
        return Response.status(Response.Status.OK).build();
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
    public Response constructStep(String argumentationSystem, String pattern, String step) {
        LOGGER.info("test");
        ObjectMapper mapper=new ObjectMapper();
        try {
            mapper.readValue(step,Step.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        Conclusion conclusion=new ExperimentationConclusion();
        Step step1=new Step("1",new HumanStrategy(), new ArrayList<SupportRole>(), conclusion);
        /**try {
            Step res = argumentationSystems.get(argumentationSystem).constructStep(argumentationSystems.get(argumentationSystem).getPattern(pattern),step.getEvidences(),step.getConclusion());
            LOGGER.info("Step created on "+argumentationSystem+" with pattern "+pattern);
            return Response.status(Response.Status.CREATED).entity(res).build();
        } catch (StepBuildingException | WrongEvidenceException | StrategyException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getStackTrace()).build();
        }*/
         return Response.status(Response.Status.OK).entity(step).build();

    }
}
