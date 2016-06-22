package fr.axonic.avek;

import java.util.Arrays;


public class Experimentation extends Conclusion{
	private Subject subject;
	private Stimulation 	stimulation;
	public Experimentation() {
		
	}
	public Experimentation(Subject subject, Stimulation stimulation) {
		this.subject = subject;
		this.stimulation = stimulation;
		limits = Arrays.asList(new Limit[]{subject,stimulation});
	}
	

}
