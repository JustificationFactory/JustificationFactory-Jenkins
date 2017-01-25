package fr.axonic.avek.engine.instance.evidence;

import fr.axonic.avek.engine.conclusion.Restriction;
import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.base.AString;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class Subject extends Element implements Restriction {

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

    @XmlElement
    public AString getId() {
        return id;
    }

    private void setId(AString id) {
        this.id = id;
    }

    public void setIdValue(String id) throws VerificationException {
        this.id.setValue(id);
    }

    @XmlElement
    public StaticSubjectInformations getStaticInformations() {
        return staticInformations;
    }

    public void setStaticInformations(StaticSubjectInformations staticInformations) {
        this.staticInformations = staticInformations;
    }

    @XmlElement
    public DynamicSubjectInformations getDynamicInformations() {
        return dynamicInformations;
    }

    public void setDynamicInformations(DynamicSubjectInformations dynamicInformations) {
        this.dynamicInformations = dynamicInformations;
    }

    @XmlElement
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
