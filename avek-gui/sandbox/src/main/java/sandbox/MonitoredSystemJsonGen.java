package sandbox;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.ADate;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.GregorianCalendar;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class MonitoredSystemJsonGen {

    public static void main(String[] args) throws VerificationException, IOException {
        String subjectJson = generateSubject();
        File f = new File("./avek-gui/src/main/resources/json/MonitoredSystemFile.json");
        f.delete();
        f.createNewFile();

        Files.write(Paths.get(f.toURI()),
                subjectJson.getBytes());
    }

    private static String generateSubject() {
        MonitoredSystem ms = new MonitoredSystem(new AString("id", "42A"));

        AList<AEntity> staticList = new AList<>(
            new ANumber("Id", 42),
            new ANumber("Age", 25),
            new ANumber("Sex", 1));
        staticList.setLabel("Static");
        ms.addCategory(staticList);

        AList<AEntity> pathologicList = new AList<>(
            new AString("Pathology", "OVERWEIGHT"),
            new ADate("Beginning", new GregorianCalendar()),
            new AString("Type", "Gynoid"));
        pathologicList.setLabel("Pathologic");
        ms.addCategory(pathologicList);

        AList<AEntity> dynamicList = new AList<>(
            new ANumber("Size", 123.45),
            new ANumber("Weight", 67),
            new ANumber("IMC", 67d / (1.2345 * 1.2345)));
        dynamicList.setLabel("Dynamic");
        ms.addCategory(dynamicList);

        return Jsonifier.fromAEntity(ms);
    }
}
