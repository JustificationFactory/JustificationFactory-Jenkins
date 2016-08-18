package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.EstablishedEffect;
import fr.axonic.avek.engine.instance.conclusion.Experimentation;
import fr.axonic.avek.engine.instance.evidence.*;
import fr.axonic.avek.engine.instance.strategy.EstablishEffectStrategy;
import fr.axonic.avek.engine.instance.strategy.GeneralizeStrategy;
import fr.axonic.avek.engine.instance.strategy.TreatStrategy;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.validation.exception.VerificationException;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by cduffau on 04/08/16.
 */
public class ArgumentationDiagramAPIImpl implements ArgumentationDiagramAPI {

    private Map<String,Pattern> patterns;
    private List<EvidenceRole> baseEvidences;
    private List<Step> steps;
    private final static Logger LOGGER = Logger.getLogger(ArgumentationDiagramAPIImpl.class);

    private static ArgumentationDiagramAPIImpl INSTANCE;

    public static synchronized ArgumentationDiagramAPIImpl getInstance() throws VerificationException, WrongEvidenceException {
        if(INSTANCE==null){
           INSTANCE=new ArgumentationDiagramAPIImpl();
        }
        return INSTANCE;
    }

    public ArgumentationDiagramAPIImpl() throws VerificationException, WrongEvidenceException {
        initPatterns();
        initBaseEvidences();
        steps=new ArrayList<>();
    }

    @Override
    public List<String> getPossiblePatterns(List<EvidenceRole> evidenceRoles) {
        List<String> res=new ArrayList<>();
        for(Pattern pattern : patterns.values()){
            if(pattern.applicable(evidenceRoles)){
                res.add(pattern.getId());
            }
        }
        return res;
    }

    @Override
    public Pattern getPattern(String patternId) {
        return patterns.get(patternId);
    }

    @Override
    public List<EvidenceRole> getBaseEvidences() {
        return baseEvidences;
    }

    @Override
    public Step constructStep(String patternId, List<EvidenceRole> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException {
        Step step=patterns.get(patternId).createStep(evidences,conclusion);
        steps.add(step);
        LOGGER.info(step.getConclusion());
        EvidenceRoleType evidenceRoleType=new EvidenceRoleType("",step.getConclusion().getElement().getClass());
        baseEvidences.add(evidenceRoleType.create(step.getConclusion()));
        return step;
    }

    @Override
    public List<Step> getSteps() {
        return steps;
    }

    private void initPatterns(){
        patterns=new HashMap<>();
        EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Stimulation.class);
        EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Subject.class);
        ConclusionType conclusionExperimentationType = new ConclusionType(Experimentation.class);
        //Revoir car ici on a un singleton...
        Strategy ts = new TreatStrategy();
        Pattern treat = new Pattern("1","Treat", ts, Arrays.asList(new EvidenceRoleType[] {rtStimulation, rtSubject}), conclusionExperimentationType);
        patterns.put(treat.getId(),treat);

        EvidenceRoleType rtExperimentation = new EvidenceRoleType("experimentation", Experimentation.class);
        EvidenceRoleType rtResult = new EvidenceRoleType("experimentation", Result.class);
        rtResult.setOptional(true);
        ConclusionType conclusionEffectType = new ConclusionType(EstablishedEffect.class);
        //Revoir car ici on a un singleton...
        Strategy ts2 = new EstablishEffectStrategy();
        Pattern establishEffect = new Pattern("2","Establish Effect", ts2, Arrays.asList(new EvidenceRoleType[] {rtExperimentation, rtResult}), conclusionEffectType);
        patterns.put(establishEffect.getId(),establishEffect);

        EvidenceRoleType rtEstablishedEffect= new EvidenceRoleType("effects", EstablishedEffect.class);

        Strategy ts3=new GeneralizeStrategy();
        Pattern generalize=new Pattern("3", "Generalize", ts3, Arrays.asList(new EvidenceRoleType[]{rtEstablishedEffect}),conclusionEffectType);
        patterns.put(generalize.getId(),generalize);


    }
    private void initBaseEvidences() throws VerificationException, WrongEvidenceException {
        baseEvidences=new ArrayList<>();

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



        Subject subject=new Subject();
        subject.setIdValue("12345");
        StaticSubjectInformations staticInfos=new StaticSubjectInformations();
        staticInfos.setBirthdayValue(new GregorianCalendar());
        staticInfos.setGenderValue(Gender.MALE);
        staticInfos.setHeightValue(70.5);
        staticInfos.setNameValue("Paul");
        subject.setStaticInformations(staticInfos);
        DynamicSubjectInformations dynamicInfos=new DynamicSubjectInformations();
        dynamicInfos.setBmiValue(40);
        dynamicInfos.setWeightValue(130);
        subject.setDynamicInformations(dynamicInfos);
        PathologySubjectInformations pathologyInfos=new PathologySubjectInformations();
        pathologyInfos.setBeginningOfObesityValue(new GregorianCalendar());
        pathologyInfos.setObesityTypeValue(ObesityType.GYNOID);
        subject.setPathologyInformations(pathologyInfos);
        Evidence<Stimulation> stimulation0 = new Evidence<Stimulation>("Stimulation 0", stimulation);
        Evidence<Subject> subject0 = new Evidence<Subject>("Subject 0",subject);
        EvidenceRoleType<Stimulation> rtStimulation = new EvidenceRoleType<>("stimulation", Stimulation.class);
        EvidenceRoleType<Subject> rtSubject = new EvidenceRoleType<>("subject", Subject.class);
        EvidenceRole evStimulation0 = rtStimulation.create(stimulation0);
        EvidenceRole evSubject0 = rtSubject.create(subject0);
        baseEvidences.add(evStimulation0);
        baseEvidences.add(evSubject0);
    }
}
