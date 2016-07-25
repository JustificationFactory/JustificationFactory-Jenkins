package sandbox;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.model.base.ABoolean;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AString;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import fr.axonic.avek.model.base.engine.AVar;
import fr.axonic.avek.model.verification.exception.VerificationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class ParametersJsonGen {

	public static void main(String[] args) throws VerificationException, IOException {
		String parametersJson = generateExpParameters();
		File f = new File("./avek-gui/src/main/resources/files/parametersFile.json");
		f.delete();
		f.createNewFile();

		Files.write(Paths.get(f.toURI()),
				parametersJson.getBytes());
	}

	private static String generateExpParameters() {
		AList<AEntity> aList = new AList<>();

		aList.addEntity(new ANumber("Frequency", 42.0));
		aList.addEntity(new ABoolean("Bool?", true));

		AList<AEntity> list = new AList<>();
		list.setLabel("List");
		list.addEntity(new ANumber("SubNumber", 12.34));
		list.addEntity(new ADate("SubDate", new Date()));

		AList<AVar> subList = new AList<>();
		subList.setLabel("SubList");
		subList.addEntity(new ANumber("SubSubInteger", 42));
		subList.addEntity(new AString("SubSubString", "TheSubSubStringValue"));
		subList.addEntity(new ANumber("SubSubDouble", 49.3));

		list.addEntity(subList);
		list.addEntity(new ABoolean("SubBool", false));
		list.setLabel("SubCategory");
		aList.addEntity(list);

		aList.addEntity(new ANumber("Times redo", 12));

		return Jsonifier.toJson(aList);
	}
}
