package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;

import java.util.List;

public class TopLevelRedmineStrategy extends RedmineStrategy {

    private final String outputName;

    public TopLevelRedmineStrategy(String name, String outputName) {
        super(name);

        this.outputName = outputName;
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        RedmineConclusion conclusion = new RedmineConclusion();

        conclusion.setName(outputName);

        return conclusion;
    }
}
