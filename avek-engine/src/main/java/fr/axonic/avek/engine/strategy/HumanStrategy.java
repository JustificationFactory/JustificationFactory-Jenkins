package fr.axonic.avek.engine.strategy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
public class HumanStrategy extends Strategy{

	private Comment comment;

	public HumanStrategy(String name,Rationale rationale, UsageDomain usageDomain) {
		super(name,rationale, usageDomain);
	}

	public HumanStrategy() {
	}

	public void addComment(Comment comment){
		this.comment=comment;
	}
	@XmlElement
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}


