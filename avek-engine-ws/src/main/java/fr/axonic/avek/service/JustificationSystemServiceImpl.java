package fr.axonic.avek.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import fr.axonic.avek.ArtifactType;
import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.dao.JerseyMapperProvider;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;
import fr.axonic.avek.engine.StepToCreate;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.ListPatternsBase;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.pattern.type.SupportType;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.JustificationSystemEnum;
import fr.axonic.avek.instance.JustificationSystemFactory;
import fr.axonic.validation.exception.VerificationException;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 16/01/17.
 */
@Path("/justification")
public class JustificationSystemServiceImpl implements JustificationSystemService {
    private static Map<String, JustificationSystem<ListPatternsBase>> justificationSystems;

    private static final Logger LOGGER= LoggerFactory.getLogger(JustificationSystemServiceImpl.class);

    static {
        justificationSystems =new HashMap<>();
        try {
            justificationSystems = JustificationSystemsDAO.loadJustificationSystems();
            for(JustificationSystemEnum justificationSystemEnum : JustificationSystemEnum.values()){
                if(justificationSystems.get(justificationSystemEnum.name())==null) {
                    justificationSystems.put(justificationSystemEnum.name(), JustificationSystemFactory.create(justificationSystemEnum));
                    LOGGER.info(justificationSystemEnum.name()+" Justification System added");
                }
            }
        } catch (VerificationException | WrongEvidenceException | IOException e) {
            e.printStackTrace();
        }
    }
    public JustificationSystemServiceImpl(){

    }

    @Override
    public Response registerArgumentationSystem(String name, JustificationSystem justificationSystem) {
        justificationSystems.put(name, justificationSystem);
        try {
            JustificationSystemsDAO.saveJustificationSystem(name, justificationSystem);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(name).build();
        }
        LOGGER.info(name+" Argumentation System added");
        return Response.status(Response.Status.ACCEPTED).entity(name).build();
    }

    @Override
    public Response registerArgumentationSystem(JustificationSystem justificationSystem) {
        String id =UUID.randomUUID().toString();
        return registerArgumentationSystem(id, justificationSystem);
    }

    @Override
    public Response registerPattern(String argumentationSystemId, Pattern pattern) {
        JustificationSystem<ListPatternsBase> argumentationSystem= justificationSystems.get(argumentationSystemId);
        argumentationSystem.getPatternsBase().addPattern(pattern);
        return Response.status(Response.Status.ACCEPTED).entity(pattern.getId()).build();
    }

    @Override
    public Response removeArgumentationSystem(String argumentationSystemId) {
        if(justificationSystems.remove(argumentationSystemId)==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to remove");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        try {
            JustificationSystemsDAO.removeJustificationSystem(argumentationSystemId);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Argumentation System removed");
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response removeStepsInArgumentationSystem(String argumentationSystemId) {
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
            LOGGER.info(argumentationSystemId+" Argumentation System justificationDiagram removed");
            return Response.status(Response.Status.OK).build();
        }
    }

    @Override
    public Response getArgumentationSystems() {
        Set<String> argumentationSystemsID= justificationSystems.keySet();
        if(argumentationSystemsID.isEmpty()){
            LOGGER.warn("No argumentation systems");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation systems").build();
        }
        LOGGER.info("Argumentation systems provided");
        return Response.status(Response.Status.OK).entity(argumentationSystemsID).build();

    }

    @Override
    public Response getArgumentationSystem(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem= justificationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to provide");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Argumentation System provided");
        return Response.status(Response.Status.OK).entity(argumentationSystem).build();
    }

    @Override
    public Response getArgumentationSystemPatterns(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem= justificationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to provide");
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Argumentation System patterns provided");
        return Response.status(Response.Status.OK).entity(argumentationSystem.getPatternsBase().getPatterns().stream().map(Pattern::getId).collect(Collectors.toList())).build();
    }

    @Override
    public Response getArgumentationSystemPattern(String argumentationSystemId, String pattern) {
        JustificationSystemAPI argumentationSystem= justificationSystems.get(argumentationSystemId);
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
    public Response getArtifactTypes(String artifact) {
        ArtifactType artifactType=ArtifactType.valueOf(artifact.toUpperCase());
        Reflections reflections = new Reflections("fr.axonic.avek");
        List<Class> classes=new ArrayList<>();
        for(Class clas : artifactType.getClasses()){
            Set<Class<? extends Evidence>> classs =
                    reflections.getSubTypesOf(clas);
            for(Class c:classs ){
                if(!Modifier.isAbstract(c.getModifiers())){
                    classes.add(c);
                }
            }

        }

        return Response.status(Response.Status.OK).entity(classes).build();
    }

    @Override
    public Response getArtifactTypesUsable(String argumentationSystem) {
        List<Pattern> patterns= justificationSystems.get(argumentationSystem).getPatternsBase().getPatterns();
        Set<Class> humanClasses=new HashSet<>();
        Set<Class> computedClasses=new HashSet<>();
        for(Pattern pattern:patterns){
            if(pattern.getStrategy() instanceof HumanStrategy){
                humanClasses.addAll(pattern.getInputTypes().stream().map(SupportType::getType).collect(Collectors.toList()));
                humanClasses.add(pattern.getOutputType().getType());
            }
            else {
                computedClasses.addAll(pattern.getInputTypes().stream().map(SupportType::getType).collect(Collectors.toList()));
                computedClasses.add(pattern.getOutputType().getType());
            }
        }
        Map<ArtifactType,Set<Class>> res=new EnumMap<>(ArtifactType.class);
        res.put(ArtifactType.HUMAN_STRATEGY,humanClasses);
        res.put(ArtifactType.COMPUTED_STRATEGY,computedClasses);
        return Response.status(Response.Status.OK).entity(res).build();
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
