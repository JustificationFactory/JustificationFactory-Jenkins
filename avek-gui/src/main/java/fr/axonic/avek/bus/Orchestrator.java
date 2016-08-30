package fr.axonic.avek.bus;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.*;
import fr.axonic.avek.engine.instance.evidence.Stimulation;
import fr.axonic.avek.engine.instance.evidence.Subject;
import fr.axonic.avek.gui.api.*;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.FutureTask;

/**
 * Created by Nathaël N on 04/08/16.
 */
public class Orchestrator implements Observer {
    private final static Logger LOGGER = Logger.getLogger(Orchestrator.class);

    private ViewType currentView;
    private String currentPatternId;
    private Subject currentSubject;
    private Stimulation currentStimulation;

    private List<String> patternNamesList;
    private List<EvidenceRole> evidences;
    private final Stack<FutureTask> tasks;

    private final ArgumentationDiagramAPI engineAPI;
    private final GUIAPI guiAPI;

    public Orchestrator(GUIAPI guiapi) throws VerificationException, WrongEvidenceException, GUIException {
        tasks = new Stack<>();

        engineAPI = ArgumentationDiagramAPIImpl.getInstance();
        guiAPI = guiapi;
        guiapi.addObserver(this);
        
        this.orchestrate();
    }

    private void orchestrate() throws GUIException, VerificationException, WrongEvidenceException {
        guiAPI.showLoading(); // show loading while orchestrating because can last long

        computeNextPattern();

        // If there is only one pattern available, setting the view to it
        if (patternNamesList.size() == 1) {
            showViewFromPattern(patternNamesList.get(0));
        } else {
            Map<ComponentType, Object> content = new HashMap<>();

            // TODO fill content

            guiAPI.show(ViewType.STRATEGY_SELECTION_VIEW, content);
        }
    }

    private void computeNextPattern() throws VerificationException, WrongEvidenceException {
        // Constructing conclusion
        if (currentPatternId != null) {
            constructStep();
            currentPatternId = null;
        }

        // Preparing for following view
        evidences = engineAPI.getBaseEvidences();
        patternNamesList = engineAPI.getPossiblePatterns(evidences);
    }

    private void showViewFromPattern(String patternName) throws GUIException {
        ViewType viewType;
        Map<ComponentType, Object> content = setDataFromEvidence();

        patternName = engineAPI.getPattern(patternName).getName();

        // Selecting the right view depending on pattern
        switch (patternName) {
            case "Treat":
                viewType = ViewType.TREAT_VIEW;
                break;
            case "Establish Effect":
                viewType = ViewType.ESTABLISH_EFFECT_VIEW;
                break;
            case "Generalize":
                viewType = ViewType.GENERALIZE_VIEW;
                break;
            default:
                throw new RuntimeException("Pattern is unknown for ViewType conversion: " + patternName);
        }

        guiAPI.show(viewType, content);
        currentView = viewType;
        currentPatternId = patternName;
    }


    private Map<ComponentType, Object> setDataFromEvidence() {
        Map<ComponentType, Object> content = new HashMap<>();

        // Setting default Experiment results to Data bus
        Map<EffectEnum, ARangedEnum> effects = new HashMap<>();
        for (EffectEnum effect : EffectEnum.values()) {
            effects.put(effect, effect.getState());
        }
        content.put(ComponentType.EFFECTS, effects);


        // Setting others data to Data bus
        for (EvidenceRole evidenceRole : evidences) {
            try {
                switch (evidenceRole.getRole()) {
                    case "subject":
                        currentSubject = (Subject) evidenceRole.getEvidence().getElement();

                        MonitoredSystem ms = new MonitoredSystem(currentSubject.getId());
                        Map<String, AEntity> map = currentSubject.getStaticInformations().getFieldsContainer();
                        AList<AEntity> al = new AList<>();
                        al.setLabel("Static");
                        al.addAll(map.values());
                        ms.addCategory(al);

                        map = currentSubject.getDynamicInformations().getFieldsContainer();
                        al = new AList<>();
                        al.setLabel("Dynamic");
                        al.addAll(map.values());
                        ms.addCategory(al);

                        map = currentSubject.getPathologyInformations().getFieldsContainer();
                        al = new AList<>();
                        al.setLabel("Pathologic");
                        al.addAll(map.values());
                        ms.addCategory(al);

                        content.put(ComponentType.MONITORED_SYSTEM, ms);
                        break;
                    case "stimulation":
                        currentStimulation = (Stimulation) evidenceRole.getEvidence().getElement();

                        AList<AEntity> list = new AList<>();
                        list.setLabel("root");
                        list.addAll(currentStimulation.getFieldsContainer().values());

                        content.put(ComponentType.EXPERIMENTATION_PARAMETERS, list);
                        break;
                    case "":
                        if (evidenceRole.getEvidence() instanceof EstablishEffectConclusion) {
                            LOGGER.debug("Got: " + evidenceRole);
                            EstablishEffectConclusion eec = (EstablishEffectConclusion) evidenceRole.getEvidence();

                            content.put(ComponentType.EFFECTS, ((EstablishedEffect) eec.getElement()).getEffects());
                            break;
                        }
                    default:
                        LOGGER.warn("Unknown Evidence role \"" + evidenceRole.getRole() + "\" in " + evidenceRole);
                }
            } catch (RuntimeException e) {
                LOGGER.error("Impossible to treat Evidence role: " + evidenceRole, e);
            }
        }
        return content;
    }

    private void constructStep() {
        // Obtaining data
        List<ComponentType> s = currentView.getCompatibleComponents();
        Map<ComponentType, Object> data = new HashMap<>();

        for(ComponentType type : s) {
            data.put(type, guiAPI.getData(type));
        }

        // constructing step
        switch (currentPatternId) {
            case "Treat":
                constructTreatStep();
                break;
            case "Establish Effect":
                constructEstablishEffectStep(data);
                break;
            case "Generalize":
                constructGeneralizeStep(data);
                break;
            default:
                LOGGER.error("Constructing \"" + currentPatternId + "\" not implemented");
        }
    }

    private void constructTreatStep() {
        LOGGER.debug("Constructing Treat step");
        try {
            engineAPI.constructStep(currentPatternId,
                    evidences,
                    new ExperimentationConclusion(
                            "Experimentation",
                            currentSubject,
                            currentStimulation));
        } catch (WrongEvidenceException | StepBuildingException e) {
            LOGGER.error("Impossible to constructStep");
        }
    }

    private void constructEstablishEffectStep(Map<ComponentType, Object> data) {
        LOGGER.debug("Constructing Establish effect step");
        try {
            AList<Effect> effects = DataTranslator.jellyBeanItemsToEffectList((List<JellyBeanItem>) data.get(ComponentType.EFFECTS));

            EstablishedEffect establishedEffect =
                    new EstablishedEffect(
                        new Experimentation(currentStimulation, currentSubject),
                        effects);

            EstablishEffectConclusion conclusion =
                    new EstablishEffectConclusion(
                            "Establish Effect",
                            establishedEffect);

            // TODO pass UploadedFile.uploadedFolder; in establishEffectConclusion

            engineAPI.constructStep(currentPatternId, evidences, conclusion);
        } catch (WrongEvidenceException | StepBuildingException e) {
            LOGGER.error("Impossible to constructStep");
        }
    }

    private void constructGeneralizeStep(Map<ComponentType, Object> data) {
        LOGGER.debug("Constructing Generalize step");
        try {
            // TODO pass UploadedFile.uploadedFolder; in GeneralizationConclusion
            AList<Effect> effects = DataTranslator.jellyBeanItemsToEffectList((List<JellyBeanItem>) data.get(ComponentType.EFFECTS));

            GeneralizationConclusion conclusion = new GeneralizationConclusion(
                    "Generalization",
                    new EstablishedEffect(
                            new Experimentation(
                                    currentStimulation,
                                    currentSubject),
                            effects
                    ));

            engineAPI.constructStep(currentPatternId, evidences, conclusion);
        } catch (WrongEvidenceException | StepBuildingException e) {
            LOGGER.error("Impossible to constructStep");
        }
    }


    /**
     *
     * @param o Should be a GUIAPI
     * @param arg Should be a Map&lt;String, Object&gt;
     */
    @Override
    public void update(Observable o, Object arg) {
        LOGGER.debug("Received notify !");

        if(!o.equals(guiAPI)) {
            throw new RuntimeException("Update get not from current used GUI API");
        }
        GUIAPI api = (GUIAPI) o;
        Map<String, Object> data = (Map<String, Object>) arg;

        for(Map.Entry<String,Object> entry : data.entrySet()) {
            switch (entry.getKey()) {
                case "Pattern" : // When user selected pattern strategy he wants clicking on submit button of selection view
                    String selectedPatternName = (String) entry.getValue();

                    if(patternNamesList.contains(selectedPatternName)) {
                        try {
                            showViewFromPattern(selectedPatternName);
                        } catch (GUIException e) {
                            LOGGER.error("Unknown error occurred while showing view", e);
                        }
                    } else {
                        LOGGER.warn("No pattern found with name: " + selectedPatternName);
                    }
                    break;
                case "Strategy" : // When user validate data he wrote clicking on strategy button
                    try {
                        orchestrate();
                    } catch (GUIException | VerificationException | WrongEvidenceException e) {
                        LOGGER.error("Unknown error occurred while orchestrating", e);
                    }
                    break;
                default:
                    throw new RuntimeException("No rule existing for name: " + entry.getKey());
            }
        }
    }
}
