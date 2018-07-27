package fr.axonic.avek.engine.kernel;



import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

public abstract class JustificationStepAPI<T extends Assertion, V extends Assertion> implements JustificationElement<JustificationStepAPI>{

    protected List<T> supports;
    protected StrategyAPI strategy;
    protected V conclusion;

    public JustificationStepAPI() {
    }

    public JustificationStepAPI(List<T> supports, StrategyAPI strategy, V conclusion) {
        this.supports = supports;
        this.strategy = strategy;
        this.conclusion = conclusion;
    }

    public List<T> getSupports() {
        return supports;
    }

    public void setSupports(List<T> supports) {
        this.supports = supports;
    }

    public StrategyAPI getStrategy() {
        return strategy;
    }

    public void setStrategy(StrategyAPI strategy) {
        this.strategy = strategy;
    }

    public V getConclusion() {
        return conclusion;
    }

    public void setConclusion(V conclusion) {
        this.conclusion = conclusion;
    }

    @Override
    @XmlTransient
    public boolean isTerminal() {
        boolean cclTerminal = conclusion.isTerminal();
        boolean supportsTerminal=supports.stream().allMatch(JustificationElement::isTerminal);
        return cclTerminal && supportsTerminal;
    }
}
