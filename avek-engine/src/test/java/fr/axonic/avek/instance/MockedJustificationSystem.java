package fr.axonic.avek.instance;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.constraint.InvalidPatternConstraint;
import fr.axonic.avek.engine.constraint.graph.NoHypothesisConstraint;
import fr.axonic.avek.engine.constraint.graph.RelatedArgumentationSystemConstraint;
import fr.axonic.avek.engine.constraint.pattern.intra.ActorTypePatternConstraint;
import fr.axonic.avek.engine.constraint.step.NotCascadingConstraint;
import fr.axonic.avek.engine.constraint.step.UniquenessConstraint;
import fr.axonic.avek.engine.diagram.JustificationPatternDiagram;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.DiagramPatternsBase;
import fr.axonic.avek.engine.pattern.ListPatternsBase;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.PatternsBase;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.conclusion.EstablishEffectConclusion;
import fr.axonic.avek.instance.conclusion.EstablishedEffect;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.conclusion.GeneralizationConclusion;
import fr.axonic.avek.instance.evidence.*;
import fr.axonic.avek.instance.strategy.*;
import fr.axonic.avek.instance.*;
import fr.axonic.avek.instance.*;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;

import java.util.*;

public class MockedJustificationSystem<T extends PatternsBase> extends JustificationSystem<T> {

    public MockedJustificationSystem(T base) throws VerificationException, WrongEvidenceException {
        super(base, getAXONICBaseEvidences());
    }

    public MockedJustificationSystem() throws VerificationException, WrongEvidenceException {
        super((T) getAXONICPatternsBase(),getAXONICBaseEvidences());
    }

    public static ListPatternsBase getAXONICPatternsBase(){

        List<Pattern> patterns=getPatterns();


        List<ArgumentationSystemConstraint> argumentationSystemConstraints =new ArrayList<>();
        argumentationSystemConstraints.add(new UniquenessConstraint(patterns.get(2)));
        argumentationSystemConstraints.add(new NotCascadingConstraint(patterns.get(0),patterns.get(2)));
        argumentationSystemConstraints.add(new NoHypothesisConstraint());
        argumentationSystemConstraints.add(new RelatedArgumentationSystemConstraint());

        return new ListPatternsBase(patterns, argumentationSystemConstraints);


    }

    public static DiagramPatternsBase getAXONICDiagramPattern(){
        return new DiagramPatternsBase(new JustificationPatternDiagram(getPatterns()));
    }

    private static List<Pattern> getPatterns(){
        List<Pattern> patterns=new ArrayList<>();
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor = new InputType<>("actor", Actor.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);
        //TODO : Revoir car ici on a un singleton...
        AXONICProject project=new AXONICProject(Stimulator.AXIS, Pathology.OBESITY);
        Strategy ts = new TreatStrategy(new Rationale<>(project),null);
        Pattern treat = new Pattern("1","Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject, rtActor}), conclusionExperimentationType);
        try {
            treat.addConstructionConstraint(new ActorTypePatternConstraint(treat,rtActor, Role.INTERMEDIATE_EXPERT));
        } catch (InvalidPatternConstraint invalidPatternConstraint) {
            invalidPatternConstraint.printStackTrace();
        }
        InputType<ExperimentationConclusion> rtExperimentation = new InputType<>("experimentation", ExperimentationConclusion.class);
        InputType<ResultsEvidence> rtResult = new InputType<>("experimentation", ResultsEvidence.class);
        OutputType<EstablishEffectConclusion> conclusionEffectType = new OutputType<>(EstablishEffectConclusion.class);
        //TODO : Revoir car ici on a un singleton...
        Strategy ts2 = new EstablishEffectStrategy(new Rationale<>(project),null);
        Pattern establishEffect = new Pattern("2","Establish Effect", ts2, Arrays.asList(new InputType[] {rtExperimentation,rtResult}), conclusionEffectType);


        InputType<EstablishEffectConclusion> rtEstablishedEffect= new InputType<>("effects", EstablishEffectConclusion.class);
        OutputType<GeneralizationConclusion> conclusionGeneralizationType = new OutputType<>(GeneralizationConclusion.class);
        Strategy ts3=new GeneralizeStrategy(new Rationale<>(project),null);
        Pattern generalize=new Pattern("3", "Generalize", ts3, Arrays.asList(new InputType[]{rtEstablishedEffect}),conclusionGeneralizationType);

        patterns.add(treat);
        patterns.add(establishEffect);
        patterns.add(generalize);
        return patterns;
    }

    private static List<Support> getAXONICBaseEvidences() throws VerificationException, WrongEvidenceException {

        WaveformParameter waveformParameter=new WaveformParameter();
        waveformParameter.setAmplitudeValue(1000.1);
        waveformParameter.setDurationValue(300);
        waveformParameter.setFrequencyValue(500);

        StimulationScheduler scheduler=new StimulationScheduler();
        scheduler.setFromValue(new GregorianCalendar());
        GregorianCalendar to=new GregorianCalendar();
        to.add(Calendar.HOUR_OF_DAY,1);
        scheduler.setToValue(to);
        Stimulation stimulation=new Stimulation(scheduler, waveformParameter);
        stimulation.setWaveformValue(WaveformEnum.RECTANGULAR);

        StaticSubjectInformations staticInfos=new StaticSubjectInformations();
        staticInfos.setBirthdayValue(new GregorianCalendar());
        staticInfos.setGenderValue(Gender.MALE);
        staticInfos.setHeightValue(70.5);
        staticInfos.setNameValue("Paul");
        DynamicSubjectInformations dynamicInfos=new DynamicSubjectInformations();
        dynamicInfos.setBmiValue(40);
        dynamicInfos.setWeightValue(130);

        PathologySubjectInformations pathologyInfos=new PathologySubjectInformations();
        pathologyInfos.setBeginningOfObesityValue(new GregorianCalendar());
        pathologyInfos.setObesityTypeValue(ObesityType.GYNOID);

        Subject subject=new Subject("12345",staticInfos,dynamicInfos,pathologyInfos);
        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", stimulation);
        SubjectEvidence subject0 = new SubjectEvidence("Subject 0",subject);
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor = new InputType<>("actor", Actor.class);
        Support evStimulation0 = rtStimulation.create(stimulation0);
        Support evSubject0 = rtSubject.create(subject0);
        Support evActor = rtActor.create(new Actor("Chloé", Role.SENIOR_EXPERT));
        List<Support> baseEvidences=new ArrayList<>();
        baseEvidences.add(evStimulation0);
        baseEvidences.add(evSubject0);
        baseEvidences.add(evActor);
        return baseEvidences;
    }

    public static void fillSomeSteps(JustificationSystem justificationSystem, int number) throws WrongEvidenceException, StrategyException, StepBuildingException, VerificationException {
        Evidence<Stimulation> stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0", new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(), stimulation0.getElement());

        if (number >= 1) {
            InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
            InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
            InputType<Actor> rtActor = new InputType<>("actor", Actor.class);
            OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);
            Support evStimulation0 = rtStimulation.create(stimulation0);
            Support evSubject0 = rtSubject.create(subject0);
            Support evActor = rtActor.create(new Actor("Chloé", Role.SENIOR_EXPERT));
            justificationSystem.constructStep(justificationSystem.getPatternsBase().getPattern("0"), Arrays.asList(evStimulation0, evSubject0, evActor), experimentation0);

        }
        if (number >= 2) {
            InputType<ExperimentationConclusion> rtExperimentation = new InputType<>("experimentation", ExperimentationConclusion.class);
            InputType<ResultsEvidence> rtResults = new InputType<>("result", ResultsEvidence.class);
            OutputType<EstablishEffectConclusion> conclusionEffectType = new OutputType<>(EstablishEffectConclusion.class);
            // revoir avec la bonne stratgeie
            Evidence<Result> results0 = new ResultsEvidence("Result 0", new Result(new AList<>()));
            Conclusion<EstablishedEffect> effect0 = new EstablishEffectConclusion("Effect 0", new EstablishedEffect(null, new AList<>()));
            Support experimentationRole = rtExperimentation.create(experimentation0);
            Support evResults = rtResults.create(results0);
            justificationSystem.constructStep(justificationSystem.getPatternsBase().getPattern("1"), Arrays.asList(experimentationRole, evResults), effect0);
        }
    }
}
