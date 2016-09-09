package fr.axonic.avek.engine.instance.evidence;

import fr.axonic.avek.engine.conclusion.Limit;
import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.base.AString;
import fr.axonic.validation.exception.VerificationException;

public class Subject extends Element implements Limit {

    private AString id;
    private StaticSubjectInformations staticInformations;
    private DynamicSubjectInformations dynamicInformations;
    private PathologySubjectInformations pathologyInformations;


    public Subject(String id, StaticSubjectInformations staticInformations, DynamicSubjectInformations dynamicInformations, PathologySubjectInformations pathologyInformations) {


        super();
        this.setLabel("Subject");
        this.setPath("fr.axonic");
        this.setCode("subject");
        this.id=new AString();
        this.id.setLabel("Subject ID");
        this.id.setCode("id");
        this.id.setPath("fr.axonic.subject");
        try {
            this.id.setValue(id);
        } catch (VerificationException e) {
            e.printStackTrace();
        }
        this.staticInformations = staticInformations;
        this.dynamicInformations = dynamicInformations;
        this.pathologyInformations = pathologyInformations;
        super.init();
    }

    public Subject() throws VerificationException {
        this(null, null, null, null);
    }

    public AString getId() {
        return id;
    }

    private void setId(AString id) {
        this.id = id;
    }

    public void setIdValue(String id) throws VerificationException {
        this.id.setValue(id);
    }

    public StaticSubjectInformations getStaticInformations() {
        return staticInformations;
    }

    public void setStaticInformations(StaticSubjectInformations staticInformations) {
        this.staticInformations = staticInformations;
    }

    public DynamicSubjectInformations getDynamicInformations() {
        return dynamicInformations;
    }

    public void setDynamicInformations(DynamicSubjectInformations dynamicInformations) {
        this.dynamicInformations = dynamicInformations;
    }

    public PathologySubjectInformations getPathologyInformations() {
        return pathologyInformations;
    }

    public void setPathologyInformations(PathologySubjectInformations pathologyInformations) {
        this.pathologyInformations = pathologyInformations;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", staticInformations=" + staticInformations +
                ", dynamicInformations=" + dynamicInformations +
                ", pathologyInformations=" + pathologyInformations +
                '}';
    }
}
