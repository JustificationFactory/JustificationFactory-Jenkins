package fr.axonic.avek.service;

import fr.axonic.avek.dao.JustificationSystemsDAO;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.instance.JustificationSystemEnum;
import fr.axonic.avek.instance.JustificationSystemFactory;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JustificationSystemsBD {

    private static final Logger LOGGER= LoggerFactory.getLogger(JustificationSystemsBD.class);

    private static JustificationSystemsBD INSTANCE;

    private Map<String, JustificationSystem> justificationSystems;

    private JustificationSystemsBD() throws IOException, VerificationException, WrongEvidenceException {
        justificationSystems =new HashMap<>();

        justificationSystems = JustificationSystemsDAO.loadJustificationSystems();
        for(JustificationSystemEnum justificationSystemEnum : JustificationSystemEnum.values()){
            if(justificationSystems.get(justificationSystemEnum.name())==null) {
                justificationSystems.put(justificationSystemEnum.name(), JustificationSystemFactory.create(justificationSystemEnum));
                LOGGER.info(justificationSystemEnum.name()+" Justification System added");
            }
        }

    }
    public static synchronized JustificationSystemsBD getInstance(){
        if(INSTANCE == null){
            try {
                INSTANCE = new JustificationSystemsBD();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return INSTANCE;
    }

    public Map<String, JustificationSystem> getJustificationSystems() {
        return justificationSystems;
    }
}
