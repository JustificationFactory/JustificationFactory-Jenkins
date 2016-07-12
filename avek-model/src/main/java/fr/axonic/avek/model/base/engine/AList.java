package fr.axonic.avek.model.base.engine;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 11/07/16.
 */
@XmlRootElement
public class AList<T extends AEntity> extends AEntity {

    private List<T> entities;

    public AList(List<T> entities) {
        this.entities = entities;
    }

    public AList() {
        this.entities = new ArrayList<>();
    }

    @XmlElement
    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public void addEntity(T entity){
        entities.add(entity);
    }

    private void removeEntity(T entity){
        entities.remove(entity);
    }
}
