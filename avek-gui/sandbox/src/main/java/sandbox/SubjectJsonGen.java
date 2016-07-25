package sandbox;

import com.google.gson.GsonBuilder;
import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.model.sample.ExampleState;
import fr.axonic.avek.gui.model.sample.ExampleStateBool;
import fr.axonic.avek.gui.model.structure.ExperimentationResults;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.ARangedEnum;
import fr.axonic.avek.model.base.AString;
import fr.axonic.avek.model.verification.exception.VerificationException;

import java.io.File;
import java.io.IOException;
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
		String enums1Json = generateParameters();
		Files.write(Paths.get(f.toURI()),
				enums1Json.getBytes());
	}

	private static String generateSubject() {
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

		return Jsonifier.toJson(ms);
	}

	public static String generateParameters() throws VerificationException {
		ExperimentationResults expRes = new ExperimentationResults();

		{	ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
			aEnum.setRange(Arrays.asList(ExampleState.values()));
			expRes.put("AE1", aEnum);
		}
		{	ExampleState val = ExampleState.values()[0];
			ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(val);
			aEnum.setRange(Arrays.asList(ExampleState.values()));
			expRes.put("AE2", aEnum);
		}
		{
			ARangedEnum<ExampleStateBool> aEnum = new ARangedEnum<>(ExampleStateBool.FALSE);
			aEnum.setRange(Arrays.asList(ExampleStateBool.values()));
			expRes.put("AE3", aEnum);
		}
		{	ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
			aEnum.setRange(Arrays.asList(ExampleState.values()));
			expRes.put("AE4", aEnum);
		}
		{	ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
			aEnum.setRange(Arrays.asList(ExampleState.values()));
			expRes.put("AE5", aEnum);
		}
		{	ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.MEDIUM);
			aEnum.setRange(Arrays.asList(ExampleState.values()));
			expRes.put("AE6", aEnum);
		}
		{	ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
			aEnum.setRange(Arrays.asList(ExampleState.values()));
			expRes.put("AE7", aEnum);
		}
		{
			ARangedEnum<ExampleStateBool> aEnum = new ARangedEnum<>(ExampleStateBool.TRUE);
			aEnum.setRange(Arrays.asList(ExampleStateBool.values()));
			expRes.put("AE8", aEnum);
		}
		{	ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
			aEnum.setRange(Arrays.asList(ExampleState.values()));
			expRes.put("AE9", aEnum);
		}

		return Jsonifier.toJson(expRes);
	}

}
