package fr.axonic.avek.instance.evidence;

import fr.axonic.base.*;
import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * Created by cduffau on 02/08/16.
 */
@XmlRootElement
@XmlSeeAlso(Gender.class)
public class StaticSubjectInformations extends AStructure{

    private ARangedEnum<Gender> gender;
    private ADate birthday;
    private AString name;
    private ANumber height;

    public StaticSubjectInformations() throws VerificationException {
        super();
        this.setLabel("Static Informations");
        this.setCode("static");
        this.setPath("fr.axonic.subject");

        gender=new ARangedEnum<>(Gender.class);
        gender.setLabel("Gender");
        gender.setCode("gender");
        gender.setPath("fr.axonic.subject.static");
        gender.setRange(AVarHelper.transformToAVar(Arrays.asList(Gender.values())));

        birthday=new ADate();
        birthday.setLabel("Birthday");
        birthday.setCode("birthday");
        birthday.setPath("fr.axonic.subject.static");

        name=new AString();
        name.setLabel("Name");
        name.setCode("name");
        name.setPath("fr.axonic.subject.static");

        height=new ANumber();
        height.setLabel("Height");
        height.setCode("height");
        height.setPath("fr.axonic.subject.static");

        super.init();
    }

    @XmlElement
    public AEnum<Gender> getGender() {
        return gender;
    }

    private void setGender(ARangedEnum<Gender> gender) {
        this.gender = gender;
    }

    public void setGenderValue(Gender gender) throws VerificationException {
        this.gender.setValue(gender);
    }

    @XmlElement
    public ADate getBirthday() {
        return birthday;
    }

    private void setBirthday(ADate birthday) {
        this.birthday = birthday;
    }

    public void setBirthdayValue(GregorianCalendar birthday) throws VerificationException {
        this.birthday.setValue(birthday);
    }

    @XmlElement
    public AString getName() {
        return name;
    }

    public void setNameValue(String name) throws VerificationException {
        this.name.setValue(name);
    }

    private void setName(AString name) {
        this.name = name;
    }

    @XmlElement
    public ANumber getHeight() {
        return height;
    }

    public void setHeightValue(double height) throws VerificationException {
        this.height.setValue(height);
    }

    private void setHeight(ANumber height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "StaticSubjectInformations{" +
                "gender=" + gender +
                ", birthday=" + birthday +
                ", name=" + name +
                ", height=" + height +
                '}';
    }
}
