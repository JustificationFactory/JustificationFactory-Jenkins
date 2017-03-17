package fr.axonic.avek;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.instance.conclusion.Effect;
import fr.axonic.avek.instance.conclusion.Experimentation;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.*;
import fr.axonic.avek.instance.strategy.TreatStrategy;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.strategy.*;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;


public class TestDeclarationPatterns {

	public static void main(String[] args) throws WrongEvidenceException, StepBuildingException, VerificationException, StrategyException {
		InputType rtStimulation = new InputType("stimulation", Stimulation.class);
		InputType rtSubject = new InputType("subject", Subject.class);
		OutputType conclusionExperimentationType = new OutputType(Experimentation.class);
		//Revoir car ici on a un singleton...
		class TestProject implements Project{

		}
		Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
		Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);
		
		
		Evidence<Stimulation> stimulation0 = new Evidence<Stimulation>("Stimulation 0", new Stimulation());
		Evidence<Subject> subject0 = new Evidence<Subject>("Subject 0",new Subject());
		ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
		
		SupportRole evStimulation0 = rtStimulation.create(stimulation0 );
		SupportRole evSubject0 = rtSubject.create(subject0);
		Step step0 = treat.createStep(Arrays.asList(new SupportRole[] {evStimulation0,evSubject0}), experimentation0, new Actor("Toto", Role.INTERMEDIATE_EXPERT));
		
		//Step step0 = new Step(treat,Arrays.asList(new Element[] {stimulation0,subject0}), experimentation0);
		
		System.out.println(step0);
		//System.out.println(step00);
		
		
		InputType rtExperimentation = new InputType("experimentation", Experimentation.class);
		InputType rtResults = new InputType("result", Result.class);
		OutputType conclusionEffectType = new OutputType(Effect.class);
		// revoir avec la bonne stratgeie
		Pattern establishProperty = new Pattern("EstablishProperty",ts, Arrays.asList(new InputType[] {rtExperimentation, rtResults}), conclusionEffectType);
		
		Evidence<Result> results0 = new Evidence<Result>("Result 0",new Result(new AList<>()));
		Conclusion<Effect> effect0 = new Conclusion<Effect>("Effect 0",new Effect());
		//Step step1 = treat.createStep(Arrays.asList(new Evidence[] {experimentation0,results0}), effect0);
		
		
		

	}

}
