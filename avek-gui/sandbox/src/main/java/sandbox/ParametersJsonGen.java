package sandbox;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.base.ABoolean;
import fr.axonic.base.ADate;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import fr.axonic.validation.exception.VerificationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.GregorianCalendar;

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

		aList.add(new ANumber("Frequency", 42.0));
		aList.add(new ABoolean("Bool?", true));

		AList<AEntity> list = new AList<>();
		list.setLabel("List");
		list.add(new ANumber("SubNumber", 12.34));
		list.add(new ADate("SubDate", new GregorianCalendar()));

		AList<AVar> subList = new AList<>();
		subList.setLabel("SubList");
		subList.add(new ANumber("SubSubInteger", 42));
		subList.add(new AString("SubSubString", "TheSubSubStringValue"));
		subList.add(new ANumber("SubSubDouble", 49.3));

		list.add(subList);
		list.add(new ABoolean("SubBool", false));
		list.setLabel("SubCategory");
		aList.add(list);

		aList.add(new ANumber("Times redo", 12));

		return Jsonifier.toJson(aList);
	}
}
