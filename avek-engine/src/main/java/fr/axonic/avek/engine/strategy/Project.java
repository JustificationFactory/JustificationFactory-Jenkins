package fr.axonic.avek.engine.strategy;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.axonic.avek.instance.strategy.AXONICProject;

/**
 * Created by cduffau on 26/10/16.
 */
@JsonDeserialize(as= AXONICProject.class)
public interface Project {
}
