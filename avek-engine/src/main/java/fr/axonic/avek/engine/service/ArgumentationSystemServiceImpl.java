package fr.axonic.avek.engine.service;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.provider.MockedArgumentationSystem;
import fr.axonic.validation.exception.VerificationException;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cduffau on 16/01/17.
 */
@Path("/argumentation")
public class ArgumentationSystemServiceImpl implements ArgumentationSystemService{
    private Map<String, ArgumentationSystemAPI> argumentationSystems;

    public ArgumentationSystemServiceImpl(){
        argumentationSystems=new HashMap<>();
        try {
            argumentationSystems.put("AXONIC", MockedArgumentationSystem.getAXONICArgumentationSystem());
        } catch (VerificationException e) {
            e.printStackTrace();
        } catch (WrongEvidenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response registerArgumentationSystem(ArgumentationSystemAPI argumentationSystem) {
        String id =UUID.randomUUID().toString();
        argumentationSystems.put(id,argumentationSystem);
        return Response.status(Response.Status.ACCEPTED).entity(id).build();
    }

    @Override
    public Response removeArgumentationSystem(String argumentationSystemId) {
        if(argumentationSystems.remove(argumentationSystemId)==null){
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        return Response.status(200).build();
    }

    @Override
    public Response getArgumentation(String argumentationSystemId) {
        ArgumentationSystemAPI argumentationSystem=argumentationSystems.get(argumentationSystemId);
        if(argumentationSystem==null){
            return Response.status(Response.Status.NOT_FOUND).entity("No argumentation system with id "+argumentationSystemId).build();
        }
        return Response.status(Response.Status.OK).entity(argumentationSystem).build();
    }

    @Override
    public Response constructStep(String argumentationSystem, String pattern, List<EvidenceRole> evidences, Conclusion conclusion) {
        try {
            Step step = argumentationSystems.get(argumentationSystem).constructStep(pattern,evidences,conclusion);
            return Response.status(Response.Status.CREATED).entity(step).build();
        } catch (StepBuildingException | WrongEvidenceException | StrategyException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getStackTrace()).build();
        }

    }
}
