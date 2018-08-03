package fr.axonic.avek.bus;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.support.Support;

import javax.ws.rs.core.Response;
import java.util.List;

public class JustificationBusServiceImplementation implements JustificationBusService {

    private StepBuilder stepBuilder;

    public JustificationBusServiceImplementation() {
        // TODO
        stepBuilder = new StepBuilder(new JustificationSystem());
    }

    @Override
    public Response transmitSupport(List<Support> supports) {
        for (Support support : supports) {
            try {
                stepBuilder.acknowledgeSupport(support);
            } catch (StepBuildingException | StrategyException e) {
                return Response.serverError().build();
            }
        }

        return Response.ok().build();
    }
}
