package fr.axonic.avek.engine.provider;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.constraint.pattern.inter.NotCascadingConstraint;
import fr.axonic.avek.engine.constraint.pattern.intra.ConclusionReuseConstraint;
import fr.axonic.avek.engine.constraint.pattern.intra.UnicityConstraint;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.EstablishedEffect;
import fr.axonic.avek.engine.instance.conclusion.Experimentation;
import fr.axonic.avek.engine.instance.evidence.*;
import fr.axonic.avek.engine.instance.strategy.*;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.validation.exception.VerificationException;

import java.util.*;

/**
 * Created by cduffau on 18/01/17.
 */
public class MockedArgumentationSystem {

    public static ArgumentationSystemAPI getAXONICArgumentationSystem() throws VerificationException, WrongEvidenceException {
        ArgumentationSystem argumentationSystem=new ArgumentationSystem(getAXONICPatternsBase(),getAXONICBaseEvidences());

        return argumentationSystem;
    }

    private static PatternsBase getAXONICPatternsBase(){
        List<Pattern> patterns=new ArrayList<>();
        EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Stimulation.class);
        EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Subject.class);
        ConclusionType conclusionExperimentationType = new ConclusionType(Experimentation.class);
        //TODO : Revoir car ici on a un singleton...
        AXONICProject project=new AXONICProject(Stimulator.AXIS, Pathology.OBESITY);
        Strategy ts = new TreatStrategy(new Rationale<>(project),null);
        Pattern treat = new Pattern("1","Treat", ts, Arrays.asList(new EvidenceRoleType[] {rtStimulation, rtSubject}), conclusionExperimentationType);

        EvidenceRoleType rtExperimentation = new EvidenceRoleType("experimentation", Experimentation.class);
        EvidenceRoleType rtResult = new EvidenceRoleType("experimentation", Result.class);
        rtResult.setOptional(true);
        ConclusionType conclusionEffectType = new ConclusionType(EstablishedEffect.class);
        //TODO : Revoir car ici on a un singleton...
        Strategy ts2 = new EstablishEffectStrategy(new Rationale<>(project),null);
        Pattern establishEffect = new Pattern("2","Establish Effect", ts2, Arrays.asList(new EvidenceRoleType[] {rtExperimentation, rtResult}), conclusionEffectType);
        patterns.add(establishEffect);

        EvidenceRoleType rtEstablishedEffect= new EvidenceRoleType("effects", EstablishedEffect.class);

        Strategy ts3=new GeneralizeStrategy(new Rationale<>(project),null);
        Pattern generalize=new Pattern("3", "Generalize", ts3, Arrays.asList(new EvidenceRoleType[]{rtEstablishedEffect}),conclusionEffectType);
        patterns.add(generalize);


        List<PatternConstraint> patternConstraints=new ArrayList<>();
        patternConstraints.add(new UnicityConstraint(generalize));
        patternConstraints.add(new ConclusionReuseConstraint(treat));
        patternConstraints.add(new ConclusionReuseConstraint(establishEffect));
        patternConstraints.add(new NotCascadingConstraint(treat,generalize));
        patterns.add(treat);
        return new PatternsBase(patterns, patternConstraints);


    }
    private static List<EvidenceRole> getAXONICBaseEvidences() throws VerificationException, WrongEvidenceException {

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
        Evidence<Stimulation> stimulation0 = new Evidence<Stimulation>("Stimulation 0", stimulation);
        Evidence<Subject> subject0 = new Evidence<Subject>("Subject 0",subject);
        EvidenceRoleType<Evidence> rtStimulation = new EvidenceRoleType<>("stimulation", Evidence.class);
        EvidenceRoleType<Evidence> rtSubject = new EvidenceRoleType<>("subject", Evidence.class);
        EvidenceRole evStimulation0 = rtStimulation.create(stimulation0);
        EvidenceRole evSubject0 = rtSubject.create(subject0);
        List<EvidenceRole> baseEvidences=new ArrayList<>();
        baseEvidences.add(evStimulation0);
        baseEvidences.add(evSubject0);
        return baseEvidences;
    }

}
