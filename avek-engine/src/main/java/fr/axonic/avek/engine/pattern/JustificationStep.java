package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.kernel.JustificationStepAPI;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.strategy.Strategy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlRootElement
public class JustificationStep extends JustificationStepAPI<Support,Conclusion> implements Cloneable{
	private String id;
	private String patternId;

	private JustificationStep() {
		id= UUID.randomUUID().toString().replace("-", "");
	}

	public JustificationStep(String patternId, Strategy strategy, List<Support> supportRolelist, Conclusion conclusion) {
		super(supportRolelist,strategy,conclusion);
		this.patternId=patternId;
		this.id= UUID.randomUUID().toString().replace("-", "");
	}

	private JustificationStep(String id,String patternId, Strategy strategy, List<Support> supportRolelist, Conclusion conclusion) {
		this(patternId,strategy,supportRolelist,conclusion);
		this.id= id;
	}


	public JustificationStep(JustificationStep step) throws CloneNotSupportedException {
		this(step.getId(),step.getPatternId(), (Strategy) step.getStrategy(),new ArrayList<>(step.getSupports()),step.getConclusion().clone());
	}

	@XmlElement
	@XmlElementWrapper
	public List<Support> getSupports() {
		return supports;
	}

	@XmlElement
	public String getPatternId() {
		return patternId;
	}

	public void setPatternId(String patternId) {
		this.patternId = patternId;
	}

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Step{" +
				"id='" + id + '\'' +
				", patternId='" + patternId + '\'' +
				", evidences=" + supports +
				", strategy=" + strategy +
				", conclusion=" + conclusion +
				'}';
	}

	@Override
	public List<JustificationStepAPI> conformsTo() {
		return null;
	}

	@Override
	public JustificationStep clone() throws CloneNotSupportedException {
		JustificationStep step= (JustificationStep) super.clone();
		step.supports=new ArrayList<>();
		for(Support support:supports){
			step.supports.add(support.clone());
		}
		step.conclusion=conclusion.clone();
		step.id=id;
		step.patternId=patternId;
		step.strategy=strategy;
		return step;
	}
}
