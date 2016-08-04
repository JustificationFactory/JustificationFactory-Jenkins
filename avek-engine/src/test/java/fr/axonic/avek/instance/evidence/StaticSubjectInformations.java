package fr.axonic.avek.instance.evidence;

import fr.axonic.base.ADate;
import fr.axonic.base.AEnum;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AStructure;
import fr.axonic.validation.exception.VerificationException;

import java.util.GregorianCalendar;

/**
 * Created by cduffau on 02/08/16.
 */
public class StaticSubjectInformations extends AStructure{

    private AEnum<Gender> gender;
    private ADate birthday;
    private AString name;
    private ANumber height;

    public StaticSubjectInformations() {
        super();
        this.setCode("static");
        this.setPath("fr.axonic.subject");

        gender=new AEnum<>();
        gender.setLabel("Gender");
        gender.setCode("gender");
        gender.setPath("fr.axonic.subject.static");

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

    public AEnum<Gender> getGender() {
        return gender;
    }

    private void setGender(AEnum<Gender> gender) {
        this.gender = gender;
    }

    public void setGenderValue(Gender gender) throws VerificationException {
        this.gender.setValue(gender);
    }

    public ADate getBirthday() {
        return birthday;
    }

    private void setBirthday(ADate birthday) {
        this.birthday = birthday;
    }

    public void setBirthdayValue(GregorianCalendar birthday) throws VerificationException {
        this.birthday.setValue(birthday);
    }

    public AString getName() {
        return name;
    }

    public void setNameValue(String name) throws VerificationException {
        this.name.setValue(name);
    }

    private void setName(AString name) {
        this.name = name;
    }

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
