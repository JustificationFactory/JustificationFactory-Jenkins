package sandbox;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.model.sample.BooleanState;
import fr.axonic.avek.gui.model.sample.ExampleState;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class ResultsJsonGen {

    public static void main(String[] args) throws VerificationException, IOException {
        File f = new File("./avek-gui/src/main/resources/json/resultEnum1.json");
        f.delete();
        f.createNewFile();
        String enums1Json = generateExperimentResults();
        Files.write(Paths.get(f.toURI()),
                enums1Json.getBytes());
    }

    private static String generateExperimentResults() throws VerificationException {
        Map<String, ARangedEnum> expRes = new LinkedHashMap<>();

        {
            ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(ExampleState.values())));
            expRes.put("AE1", aEnum);
        }
        {
            ExampleState val = ExampleState.values()[0];
            ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(val);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(ExampleState.values())));
            expRes.put("AE2", aEnum);
        }
        {
            ARangedEnum<BooleanState> aEnum = new ARangedEnum<>(BooleanState.FALSE);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(BooleanState.values())));
            expRes.put("AE3", aEnum);
        }
        {
            ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(ExampleState.values())));
            expRes.put("AE4", aEnum);
        }
        {
            ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(ExampleState.values())));
            expRes.put("AE5", aEnum);
        }
        {
            ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.MEDIUM);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(ExampleState.values())));
            expRes.put("AE6", aEnum);
        }
        {
            ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(ExampleState.values())));
            expRes.put("AE7", aEnum);
        }
        {
            ARangedEnum<BooleanState> aEnum = new ARangedEnum<>(BooleanState.TRUE);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(BooleanState.values())));
            expRes.put("AE8", aEnum);
        }
        {
            ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(ExampleState.values())));
            expRes.put("AE9", aEnum);
        }

        return Jsonifier.toJson(expRes);
    }

}
