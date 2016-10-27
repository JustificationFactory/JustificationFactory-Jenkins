package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.EstablishedEffect;
import fr.axonic.avek.engine.instance.conclusion.Experimentation;
import fr.axonic.avek.engine.instance.evidence.*;
import fr.axonic.avek.engine.instance.strategy.*;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.util.JAXBUtil;
import fr.axonic.validation.exception.VerificationException;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by cduffau on 04/08/16.
 */
public class ArgumentationDiagramAPIImpl implements ArgumentationDiagramAPI {

    private final Map<String,Pattern> patterns = new HashMap<>();
    private final List<EvidenceRole> baseEvidences = new ArrayList<>();
    private final List<Step> steps;
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
        try{
            List<EvidenceRole> usefullEvidences=patterns.get(patternId).filterUsefullEvidences(evidences);
            Step step=patterns.get(patternId).createStep(usefullEvidences,conclusion.clone(), new Actor("Chloé", Role.INTERMEDIATE_EXPERT));
            steps.add(step);
            LOGGER.info(step.getConclusion());
            EvidenceRoleType evidenceRoleType=new EvidenceRoleType("",step.getConclusion().getElement().getClass());
            baseEvidences.add(evidenceRoleType.create(step.getConclusion().clone()));
            return step;
        }
        catch (NullPointerException  e){
            throw new StepBuildingException("Unknown pattern");
        }


    }

    @Override
    public List<Step> getSteps() {
        try {
            save(new StepsWrapper(steps),new File("test.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return steps;
    }

    private static void save(Object object, File file) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(new Class[]{object.getClass()});
        Marshaller m = context.createMarshaller();
        m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
        FileOutputStream fos = new FileOutputStream(file);
        m.marshal(object, fos);
        fos.close();
    }

    private void initPatterns(){
        patterns.clear();
        EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Stimulation.class);
        EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Subject.class);
        ConclusionType conclusionExperimentationType = new ConclusionType(Experimentation.class);
        //Revoir car ici on a un singleton...
        AXONICProject project=new AXONICProject(Stimulator.AXIS, Pathology.OBESITY);
        Strategy ts = new TreatStrategy(new Rationale<>(project),null);
        Pattern treat = new Pattern("1","Treat", ts, Arrays.asList(new EvidenceRoleType[] {rtStimulation, rtSubject}), conclusionExperimentationType);
        patterns.put(treat.getId(),treat);

        EvidenceRoleType rtExperimentation = new EvidenceRoleType("experimentation", Experimentation.class);
        EvidenceRoleType rtResult = new EvidenceRoleType("experimentation", Result.class);
        rtResult.setOptional(true);
        ConclusionType conclusionEffectType = new ConclusionType(EstablishedEffect.class);
        //Revoir car ici on a un singleton...
        Strategy ts2 = new EstablishEffectStrategy(new Rationale<>(project),null);
        Pattern establishEffect = new Pattern("2","Establish Effect", ts2, Arrays.asList(new EvidenceRoleType[] {rtExperimentation, rtResult}), conclusionEffectType);
        patterns.put(establishEffect.getId(),establishEffect);

        EvidenceRoleType rtEstablishedEffect= new EvidenceRoleType("effects", EstablishedEffect.class);

        Strategy ts3=new GeneralizeStrategy(new Rationale<>(project),null);
        Pattern generalize=new Pattern("3", "Generalize", ts3, Arrays.asList(new EvidenceRoleType[]{rtEstablishedEffect}),conclusionEffectType);
        patterns.put(generalize.getId(),generalize);


    }
    private void initBaseEvidences() throws VerificationException, WrongEvidenceException {
        baseEvidences.clear();

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
        EvidenceRoleType<Stimulation> rtStimulation = new EvidenceRoleType<>("stimulation", Stimulation.class);
        EvidenceRoleType<Subject> rtSubject = new EvidenceRoleType<>("subject", Subject.class);
        EvidenceRole evStimulation0 = rtStimulation.create(stimulation0);
        EvidenceRole evSubject0 = rtSubject.create(subject0);
        baseEvidences.add(evStimulation0);
        baseEvidences.add(evSubject0);
    }
}
