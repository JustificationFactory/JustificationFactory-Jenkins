package fr.axonic.avek;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.Experimentation;
import fr.axonic.avek.engine.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.engine.instance.evidence.*;
import fr.axonic.avek.engine.instance.strategy.TreatStrategy;
import fr.axonic.avek.engine.strategy.*;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;


public class TestDeclarationPatterns {

	public static void main(String[] args) throws WrongEvidenceException, StepBuildingException, VerificationException, StrategyException {
		EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Stimulation.class);
		EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Subject.class);
		ConclusionType conclusionExperimentationType = new ConclusionType(Experimentation.class);
		//Revoir car ici on a un singleton...
		class TestProject implements Project{

		}
		Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
		Pattern treat = new Pattern("Treat", ts, Arrays.asList(new EvidenceRoleType[] {rtStimulation, rtSubject}), conclusionExperimentationType);
		
		
		Evidence<Stimulation> stimulation0 = new Evidence<Stimulation>("Stimulation 0", new Stimulation());
		Evidence<Subject> subject0 = new Evidence<Subject>("Subject 0",new Subject());
		ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
		
		EvidenceRole evStimulation0 = rtStimulation.create(stimulation0 );
		EvidenceRole evSubject0 = rtSubject.create(subject0);
		Step step0 = treat.createStep(Arrays.asList(new EvidenceRole[] {evStimulation0,evSubject0}), experimentation0, new Actor("Toto", Role.INTERMEDIATE_EXPERT));
		
		//Step step0 = new Step(treat,Arrays.asList(new Element[] {stimulation0,subject0}), experimentation0);
		
		System.out.println(step0);
		//System.out.println(step00);
		
		
		EvidenceRoleType rtExperimentation = new EvidenceRoleType("experimentation", Experimentation.class);
		EvidenceRoleType rtResults = new EvidenceRoleType("result", Result.class);
		ConclusionType conclusionEffectType = new ConclusionType(Effect.class);
		// revoir avec la bonne stratgeie
		Pattern establishProperty = new Pattern("EstablishProperty",ts, Arrays.asList(new EvidenceRoleType[] {rtExperimentation, rtResults}), conclusionEffectType);
		
		Evidence<Result> results0 = new Evidence<Result>("Result 0",new Result(new AList<>()));
		Conclusion<Effect> effect0 = new Conclusion<Effect>("Effect 0",new Effect());
		//Step step1 = treat.createStep(Arrays.asList(new Evidence[] {experimentation0,results0}), effect0);
		
		
		

	}

}
