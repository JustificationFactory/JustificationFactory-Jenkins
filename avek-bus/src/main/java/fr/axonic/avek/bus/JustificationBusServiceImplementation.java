package fr.axonic.avek.bus;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.instance.JustificationSystemEnum;
import fr.axonic.avek.instance.JustificationSystemFactory;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/bus")
public class JustificationBusServiceImplementation implements JustificationBusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JustificationBusServiceImplementation.class);

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
                LOGGER.error("Error while transmitting supports", e);
                return Response.serverError().build();
            }
        }

        return Response.ok().build();
    }

    private static List<JustificationSystem> getJustificationSystems() {
        try {
            List<JustificationSystem> systems = new ArrayList<>();
            systems.add(JustificationSystemFactory.create(JustificationSystemEnum.REDMINE));
            return systems;
        } catch (WrongEvidenceException | VerificationException e) {
            return new ArrayList<>();
        }
    }
}
