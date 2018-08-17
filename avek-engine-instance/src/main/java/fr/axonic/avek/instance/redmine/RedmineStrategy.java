package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.strategy.ComputedStrategy;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Document;

import java.util.List;
import java.util.Optional;

public abstract class RedmineStrategy extends ComputedStrategy {

    public RedmineStrategy(String name) {
        super(name, null, null);
    }
}
