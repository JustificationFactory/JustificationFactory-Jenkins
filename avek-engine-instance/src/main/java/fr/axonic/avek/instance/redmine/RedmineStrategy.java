package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.strategy.ComputedStrategy;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Document;

import java.util.List;
import java.util.Optional;

public class RedmineStrategy extends ComputedStrategy {

    public RedmineStrategy(String name) {
        super(name, null, null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        Optional<RedmineDocumentEvidence> evidence = supportList.stream()
                .filter(s -> s instanceof RedmineDocumentEvidence)
                .map(s -> (RedmineDocumentEvidence) s)
                .findFirst();
        RedmineConclusion redmineConclusion = new RedmineConclusion();
        evidence.ifPresent(ev -> {
            redmineConclusion.setName(ev.getName() + " validated");
            Document element=new Document(ev.getElement().getUrl());
            element.setVersion(null);
            redmineConclusion.setElement(element);
        });

        return redmineConclusion;
    }
}
