package fr.axonic.avek.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;
import fr.axonic.avek.engine.pattern.ListPatternsBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JustificationSystemsDAO {
    private static final Logger LOGGER= LoggerFactory.getLogger(JustificationSystemsDAO.class);

    private static final String DIR="data";

    public static Map<String, JustificationSystem<ListPatternsBase>> loadJustificationSystems() throws IOException {
        Map<String, JustificationSystem<ListPatternsBase>> res=new HashMap<>();
        ObjectMapper mapper=new JerseyMapperProvider().getContext(null);
        File dir = new File(DIR);
        if(!dir.exists()){
            dir.mkdir();
        }
        if(dir.listFiles()!=null){
            for (File file:dir.listFiles()) {
                String name=file.getName().split("\\.")[0];
                LOGGER.info("Found Justification System "+name);
                res.put(name,mapper.readValue(file, JustificationSystem.class));
            }
        }
        return res;
    }

    public static void saveJustificationSystem(String name, JustificationSystemAPI argumentationSystem) throws IOException {
        File file=new File(DIR+"/"+name+".data");
        file.createNewFile();
        ObjectMapper mapper=new JerseyMapperProvider().getContext(null);
        mapper.writeValue(file,argumentationSystem);


    }

    public static void removeJustificationSystem(String name) throws IOException {
        File file=new File(DIR+"/"+name+".data");
        if(file.exists()){
            file.delete();
        }

    }
}
