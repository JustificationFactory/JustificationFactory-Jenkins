package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.kernel.JustificationStepAPI;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.strategy.Strategy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@XmlRootElement
public class JustificationStep extends JustificationStepAPI<Support,Conclusion> {
	private String id;
	private String patternId;

	private JustificationStep() {
		id= UUID.randomUUID().toString().replace("-", "");
	}

	public JustificationStep(String patternId, Strategy strategy, List<Support> supportRolelist, Conclusion conclusion) {
		super(supportRolelist,strategy,conclusion);
		this.patternId=patternId;
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
	public boolean isTerminal() {
		return true;
	}

	@Override
	public List<JustificationStepAPI> conformsTo() {
		return null;
	}
}
