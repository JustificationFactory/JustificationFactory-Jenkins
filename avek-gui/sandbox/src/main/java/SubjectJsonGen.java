
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.axonic.avek.gui.model.results.ExampleState;
import fr.axonic.avek.gui.model.results.State;
import fr.axonic.avek.gui.view.EnumAdapter;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.ARangedEnum;
import fr.axonic.avek.model.base.AString;
import fr.axonic.avek.model.verification.exception.VerificationException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class SubjectJsonGen {

	public static void main(String[] args) throws VerificationException, IOException {
		String subjectJson = generateSubject();
		File f = new File("./avek-gui/src/main/resources/files/subjectFile.json");
		f.delete();
		f.createNewFile();

		Files.write(Paths.get(f.toURI()),
				subjectJson.getBytes());

		f = new File("./avek-gui/src/main/resources/files/resultEnum1.json");
		f.delete();
		f.createNewFile();
		String enums1Json = generateEnums();
		Files.write(Paths.get(f.toURI()),
				enums1Json.getBytes());
	}

	public static String generateSubject() {
		MonitoredSystem ms = new MonitoredSystem(42);
		ms.addCategory("Static");

		ms.addAVar("Static", new ANumber("Id", 42));
		ms.addAVar("Static", new ANumber("Age", 25));
		ms.addAVar("Static", new ANumber("Sex", 1));

		ms.addCategory("Pathologic");
		ms.addAVar("Pathologic", new AString("Pathology", "OVERWEIGHT"));
		ms.addAVar("Pathologic", new ADate("Beginning", new Date()));
		ms.addAVar("Pathologic", new AString("Type", "Gynoid"));

		ms.addCategory("Dynamic");
		ms.addAVar("Dynamic", new ANumber("Size", 123.45));
		ms.addAVar("Dynamic", new ANumber("Weight", 67));
		ms.addAVar("Dynamic", new ANumber("IMC", 67d/(1.2345*1.2345)));

		return new GsonBuilder().setPrettyPrinting().create().toJson(ms);
	}

	public static String generateEnums() throws VerificationException {
		ExampleState val = ExampleState.values()[0];
		ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(val);
		aEnum.setRange(Arrays.asList(ExampleState.values()));
		//aEnum.setDefaultValue(ExampleState.MEDIUM);

		return new GsonBuilder().registerTypeAdapter(ExampleState.class,new EnumAdapter<>(ExampleState.class)).setPrettyPrinting().create().toJson(aEnum);
	}

}
