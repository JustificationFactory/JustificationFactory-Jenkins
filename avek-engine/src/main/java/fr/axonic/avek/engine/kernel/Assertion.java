package fr.axonic.avek.engine.kernel;


import java.util.List;

public interface Assertion<T extends Artifact> extends JustificationElement<Assertion>{

    List<T> getArtifacts();
}
