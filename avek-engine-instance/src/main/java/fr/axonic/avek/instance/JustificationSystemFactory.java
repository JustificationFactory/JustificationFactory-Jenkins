package fr.axonic.avek.instance;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.instance.clinical.AVEKJustificationSystem;
import fr.axonic.avek.instance.jenkins.JenkinsJustificationSystem;
import fr.axonic.avek.instance.redmine.RedmineJustificationSystem;
import fr.axonic.validation.exception.VerificationException;

/**
 * Created by cduffau on 18/01/17.
 */
public class JustificationSystemFactory {


    public static JustificationSystem create(JustificationSystemEnum justificationSystem) throws VerificationException, WrongEvidenceException {
        switch (justificationSystem){
            case JENKINS: return new JenkinsJustificationSystem();
            case REDMINE: return new RedmineJustificationSystem();
            case CLINICAL_STUDIES:return new AVEKJustificationSystem();
            default: return null;
        }
    }


}
