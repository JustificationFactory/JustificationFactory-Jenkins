package fr.axonic.avek.instance;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.constraint.pattern.inter.NotCascadingConstraint;
import fr.axonic.avek.engine.constraint.pattern.intra.ConclusionReuseConstraint;
import fr.axonic.avek.engine.constraint.pattern.intra.UnicityConstraint;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.instance.conclusion.EstablishEffectConclusion;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.conclusion.GeneralizationConclusion;
import fr.axonic.avek.instance.evidence.*;
import fr.axonic.avek.instance.strategy.*;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.PatternsBase;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.validation.exception.VerificationException;

import java.util.*;

/**
 * Created by cduffau on 18/01/17.
 */
public class MockedArgumentationSystem {

    public static ArgumentationSystemAPI getAXONICArgumentationSystem() throws VerificationException, WrongEvidenceException {
        return new ArgumentationSystem(getAXONICPatternsBase(),getAXONICBaseEvidences());
    }

    private static PatternsBase getAXONICPatternsBase(){
        List<Pattern> patterns=new ArrayList<>();
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);
        //TODO : Revoir car ici on a un singleton...
        AXONICProject project=new AXONICProject(Stimulator.AXIS, Pathology.OBESITY);
        Strategy ts = new TreatStrategy(new Rationale<>(project),null);
        Pattern treat = new Pattern("1","Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);

        InputType<ExperimentationConclusion> rtExperimentation = new InputType<>("experimentation", ExperimentationConclusion.class);
        InputType<ResultsEvidence> rtResult = new InputType<>("experimentation", ResultsEvidence.class);
        OutputType<EstablishEffectConclusion> conclusionEffectType = new OutputType<>(EstablishEffectConclusion.class);
        //TODO : Revoir car ici on a un singleton...
        Strategy ts2 = new EstablishEffectStrategy(new Rationale<>(project),null);
        Pattern establishEffect = new Pattern("2","Establish Effect", ts2, Arrays.asList(new InputType[] {rtExperimentation,rtResult}), conclusionEffectType);
        patterns.add(establishEffect);

        InputType<EstablishEffectConclusion> rtEstablishedEffect= new InputType<>("effects", EstablishEffectConclusion.class);
        OutputType<GeneralizationConclusion> conclusionGeneralizationType = new OutputType<>(GeneralizationConclusion.class);
        Strategy ts3=new GeneralizeStrategy(new Rationale<>(project),null);
        Pattern generalize=new Pattern("3", "Generalize", ts3, Arrays.asList(new InputType[]{rtEstablishedEffect}),conclusionGeneralizationType);
        patterns.add(generalize);


        List<PatternConstraint> patternConstraints=new ArrayList<>();
        patternConstraints.add(new UnicityConstraint(generalize));
        patternConstraints.add(new NotCascadingConstraint(treat,generalize));
        patterns.add(treat);
        return new PatternsBase(patterns, patternConstraints);


    }
    private static List<SupportRole> getAXONICBaseEvidences() throws VerificationException, WrongEvidenceException {

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
        SupportRole evStimulation0 = rtStimulation.create(stimulation0);
        SupportRole evSubject0 = rtSubject.create(subject0);
        List<SupportRole> baseEvidences=new ArrayList<>();
        baseEvidences.add(evStimulation0);
        baseEvidences.add(evSubject0);
        return baseEvidences;
    }

}
