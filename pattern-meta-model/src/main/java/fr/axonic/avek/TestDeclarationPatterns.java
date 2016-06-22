package fr.axonic.avek;

import java.util.Arrays;


public class TestDeclarationPatterns {

	public static void main(String[] args) {
		EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Stimulation.class);
		EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Subject.class);
		ConclusionType conclusionExperimentationType = new ConclusionType(Experimentation.class);
		//Revoir car ici on a un singleton...
		Strategy ts = new TreatStrategy();
		Pattern treat = new Pattern("Treat", ts, Arrays.asList(new EvidenceRoleType[] {rtStimulation, rtSubject}), conclusionExperimentationType);
		
		
		Evidence stimulation0 = new Stimulation();
		Evidence subject0 = new Subject();	
		Conclusion experimentation0 = new Experimentation();	
		
		EvidenceRole evStimulation0 = rtStimulation.create(stimulation0 );
		EvidenceRole evSubject0 = rtSubject.create(subject0);
		Step step0 = treat.createStep(Arrays.asList(new EvidenceRole[] {evStimulation0,evSubject0}), experimentation0);
		
		//Step step0 = new Step(treat,Arrays.asList(new Element[] {stimulation0,subject0}), experimentation0);
		
		System.out.println(step0);
		//System.out.println(step00);
		
		
		EvidenceRoleType rtExperimentation = new EvidenceRoleType("experimentation", Experimentation.class);
		EvidenceRoleType rtResults = new EvidenceRoleType("result", Result.class);		
		ConclusionType conclusionEffectType = new ConclusionType(Effect.class);
		// revoir avec la bonne stratgeie
		Pattern establishProperty = new Pattern("EstablishProperty",ts, Arrays.asList(new EvidenceRoleType[] {rtExperimentation, rtResults}), conclusionEffectType);
		
		Evidence results0 = new Result();
		Conclusion effect0 = new Effect();	
		//Step step1 = treat.createStep(Arrays.asList(new Evidence[] {experimentation0,results0}), effect0);
		
		
		

	}

}
