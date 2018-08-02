package fr.axonic.avek.engine.strategy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by cduffau on 26/10/16.
 */
//@JsonDeserialize(as= AXONICProject.class)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
public interface Project {

    String name();
}
