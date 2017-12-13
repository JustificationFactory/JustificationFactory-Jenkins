package fr.axonic.avek.engine.support.evidence;

import fr.axonic.base.AString;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 24/08/17.
 */
@XmlRootElement
public class Document extends Element{

    private String url;

    public Document(String url) {
        super();
        this.url=url;
        super.init();
    }

    public Document(){
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Document{" +
                "url=" + url +
                '}';
    }
}
