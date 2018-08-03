package fr.axonic.avek.bus;

import fr.axonic.avek.engine.support.Support;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface JustificationBusService {

    @POST
    @Path("/bus/supports")
    Response transmitSupport(List<Support> supports);
}
