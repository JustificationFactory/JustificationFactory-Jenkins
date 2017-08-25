package fr.axonic.avek.engine.support.evidence;

import fr.axonic.base.AString;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 24/08/17.
 */
@XmlRootElement
public class Document extends Element{

    private AString url;

    public Document(String url) {
        super();
        this.url = new AString();
        try {
            this.url.setValue(url);
        } catch (VerificationException e) {
            e.printStackTrace();
        }
        super.init();
    }

    public Document() {
        this.url=new AString();
    }

    public AString getUrl() {
        return url;
    }

    public void setUrl(AString url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Document{" +
                "url=" + url +
                '}';
    }
}
