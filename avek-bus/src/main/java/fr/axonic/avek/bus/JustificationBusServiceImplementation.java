package fr.axonic.avek.bus;

import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.support.Support;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/bus")
public class JustificationBusServiceImplementation implements JustificationBusService {

    private StepBuilder stepBuilder;

    public JustificationBusServiceImplementation() {
        stepBuilder = new StepBuilder(getJustificationSystems());
    }

    @Override
    public Response transmitSupport(TransmittedSupports supports) {
        for (Support support : supports.getSupports()) {
            try {
                stepBuilder.acknowledgeSupport(support);
            } catch (StepBuildingException | StrategyException e) {
                return Response.serverError().build();
            }
        }

        return Response.ok().build();
    }

    private static List<JustificationSystem> getJustificationSystems() {
        try {
            return new ArrayList<>(JustificationSystemsDAO.loadJustificationSystems().values());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
