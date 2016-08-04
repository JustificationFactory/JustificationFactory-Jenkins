package sandbox;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.ADate;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.validation.exception.VerificationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.GregorianCalendar;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class SubjectJsonGen {

    public static void main(String[] args) throws VerificationException, IOException {
        String subjectJson = generateSubject();
        File f = new File("./avek-gui/src/main/resources/json/subjectFile.json");
        f.delete();
        f.createNewFile();

        Files.write(Paths.get(f.toURI()),
                subjectJson.getBytes());
    }

    private static String generateSubject() {
        MonitoredSystem ms = new MonitoredSystem(42);
        ms.addCategory("Static");

        ms.addAVar("Static", new ANumber("Id", 42));
        ms.addAVar("Static", new ANumber("Age", 25));
        ms.addAVar("Static", new ANumber("Sex", 1));

        ms.addCategory("Pathologic");
        ms.addAVar("Pathologic", new AString("Pathology", "OVERWEIGHT"));
        ms.addAVar("Pathologic", new ADate("Beginning", new GregorianCalendar()));
        ms.addAVar("Pathologic", new AString("Type", "Gynoid"));

        ms.addCategory("Dynamic");
        ms.addAVar("Dynamic", new ANumber("Size", 123.45));
        ms.addAVar("Dynamic", new ANumber("Weight", 67));
        ms.addAVar("Dynamic", new ANumber("IMC", 67d / (1.2345 * 1.2345)));

        return Jsonifier.toJson(ms);
    }
}
