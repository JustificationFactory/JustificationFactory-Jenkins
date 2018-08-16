package fr.axonic.avek.engine.support.evidence;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Created by cduffau on 24/08/17.
 */
@XmlRootElement
public class Document extends Element {

    private String url;

    public Document(String url) {
        super();
        this.url=url;
        super.init();
    }

    public Document(){
    }

    public Document(Document element) {
        super(element);
        this.url = element.getUrl();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        if (!super.equals(o)) return false;
        Document document = (Document) o;
        return Objects.equals(url, document.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), url);
    }
}
