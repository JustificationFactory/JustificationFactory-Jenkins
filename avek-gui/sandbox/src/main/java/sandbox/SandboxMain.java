package sandbox;

import fr.axonic.validation.exception.VerificationException;

import java.io.IOException;

/**
 * Created by Nathaël N on 05/08/16.
 */
public class SandboxMain {
    public static void main(String[] args) throws IOException, VerificationException {
        ParametersJsonGen.main(args);
        ResultsJsonGen.main(args);
        MonitoredSystemJsonGen.main(args);
    }
}
