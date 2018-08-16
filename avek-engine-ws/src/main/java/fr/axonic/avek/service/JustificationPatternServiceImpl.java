package fr.axonic.avek.service;

import fr.axonic.avek.ArtifactType;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;
import fr.axonic.avek.engine.pattern.ListPatternsBase;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.SupportType;
import fr.axonic.avek.engine.pattern.type.Type;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.support.evidence.Evidence;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@Path("/justification")
public class JustificationPatternServiceImpl implements JustificationPatternService {

    private static final Logger LOGGER= LoggerFactory.getLogger(JustificationPatternServiceImpl.class);

    private Map<String, JustificationSystem> justificationSystems=JustificationSystemsBD.getInstance().getJustificationSystems();


    @Override
    public Response registerPattern(String argumentationSystemId, Pattern pattern) {
        JustificationSystem<ListPatternsBase> argumentationSystem= justificationSystems.get(argumentationSystemId);
        argumentationSystem.getPatternsBase().addPattern(pattern);
        return Response.status(Response.Status.ACCEPTED).entity(pattern.getId()).build();
    }

    @Override
    public Response getJustificationSystemPatterns(String argumentationSystemId) {
        JustificationSystemAPI argumentationSystem= justificationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to provide");
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id "+argumentationSystemId).build();
        }
        LOGGER.info(argumentationSystemId+" Justification System patterns provided");
        return Response.status(Response.Status.OK).entity(argumentationSystem.getPatternsBase().getPatterns().stream().map(Pattern::getId).collect(Collectors.toList())).build();
    }

    @Override
    public Response getJustificationSystemPattern(String argumentationSystemId, String pattern) {
        JustificationSystemAPI argumentationSystem= justificationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            LOGGER.warn("Unknown "+argumentationSystemId+", impossible to provide");
            return Response.status(Response.Status.NOT_FOUND).entity("No justification system with id "+argumentationSystemId).build();
        }
        LOGGER.info("Pattern "+pattern+" for "+argumentationSystemId+" Justification System  provided");
        return Response.status(Response.Status.OK).entity(argumentationSystem.getPatternsBase().getPattern(pattern)).build();

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
        Set<Type> humanClasses=new HashSet<>();
        Set<Type> computedClasses=new HashSet<>();
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
        Map<ArtifactType,Set<Type>> res=new EnumMap<>(ArtifactType.class);
        res.put(ArtifactType.HUMAN_STRATEGY,humanClasses);
        res.put(ArtifactType.COMPUTED_STRATEGY,computedClasses);
        return Response.status(Response.Status.OK).entity(res).build();
    }
}
