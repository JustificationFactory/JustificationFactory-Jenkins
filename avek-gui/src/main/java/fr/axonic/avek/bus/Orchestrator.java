package fr.axonic.avek.bus;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.*;
import fr.axonic.avek.engine.instance.evidence.Subject;
import fr.axonic.avek.gui.api.ComponentType;
import fr.axonic.avek.gui.api.GUIAPIImpl;
import fr.axonic.avek.gui.api.GUIException;
import fr.axonic.avek.gui.api.ViewType;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.view.etablisheffect.EstablishEffectView;
import fr.axonic.avek.gui.view.generalize.GeneralizeView;
import fr.axonic.avek.gui.view.strategyselection.StrategySelectionView;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 04/08/16.
 */
public class Orchestrator {
    private final static Logger LOGGER = Logger.getLogger(Orchestrator.class);

    private ViewType currentView;
    private Pattern currentPattern;
    //private Subject currentSubject;
    //private Stimulation currentStimulation;

    private List<String> patternList;
    private List<EvidenceRole> evidences;
    private final Stack<FutureTask> tasks;

    private final ArgumentationDiagramAPI engineAPI;
    private final GUIAPIImpl guiAPI;

    private Orchestrator() throws VerificationException, WrongEvidenceException {
        tasks = new Stack<>();

        engineAPI = ArgumentationDiagramAPIImpl.getInstance();
        guiAPI = GUIAPIImpl.getInstance();
    }

    private void orchestrate() throws GUIException, VerificationException, WrongEvidenceException {
        guiAPI.showLoading(); // show loading while orchestrating because can last long

        computeNextPattern();
        setEvidencesInDataBus();

        // If there is only one pattern available, setting the view to it
        if (patternList.size() == 1) {
            showViewFromPattern(engineAPI.getPattern(patternList.get(0)));
        } else {
            Map<ComponentType, Object> content = new HashMap<>();

            // TODO fill content

            guiAPI.show(ViewType.STRATEGY_SELECTION_VIEW, content);
        }
    }

    private void computeNextPattern() throws VerificationException, WrongEvidenceException {
        // Constructing conclusion
        if (currentPattern != null) {
            constructStep(engineAPI);
            currentPattern = null;
        }

        // Preparing for following view
        evidences = engineAPI.getBaseEvidences();
        patternList = engineAPI.getPossiblePatterns(evidences);
    }

    private void showViewFromPattern(Pattern p) throws GUIException {
        ViewType viewType;
        Map<ComponentType, Object> content = new HashMap<>();

        // Selecting the right view depending on pattern
        switch (p.getName()) {
            case "Treat":
                viewType = ViewType.TREAT_VIEW;
                // TODO fill content
                break;
            case "Establish Effect":
                viewType = ViewType.ESTABLISH_EFFECT_VIEW;
                // TODO fill content
                break;
            case "Generalize":
                viewType = ViewType.GENERALIZE_VIEW;
                // TODO fill content
                break;
            default:
                throw new RuntimeException("Pattern is unknown for ViewType conversion: " + p);
        }

        guiAPI.show(viewType, content);
        currentView = viewType;
        currentPattern = p;
    }

    // TODO ↓↓↓↓↓↓↓↓↓↓↓↓↓ All methods under this should be reviewed ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    private void setFollowingView() throws VerificationException, WrongEvidenceException, ExecutionException, InterruptedException {
        // If there is more than one pattern available,
        // setting a selection view for user to choose the one he want
        StrategySelectionView ssv = new StrategySelectionView();
        ssv.load();

        FutureTask setViewTask = new FutureTask<>(() -> {
            GUIAPIImpl.getInstance().getFrame().setView(ssv);
            return true;
        });
        Platform.runLater(setViewTask);

        List<String> names = patternList.stream().collect(Collectors.toList());
        if (names != null) {
            ssv.setAvailableChoices(names);
        }

        tasks.push(setViewTask);
    }


    private void constructTreatStep(ArgumentationDiagramAPI adAPI) {
        LOGGER.debug("Constructing Treat step");
        try {
            adAPI.constructStep(INSTANCE.currentPattern.getId(),
                    INSTANCE.evidences,
                    new ExperimentationConclusion(
                            "Experimentation",
                            INSTANCE.currentSubject,
                            INSTANCE.currentStimulation));
        } catch (WrongEvidenceException | StepBuildingException e) {
            LOGGER.error("Impossible to constructStep");
        }
    }

    private void constructEstablishEffectStep(ArgumentationDiagramAPI adAPI) {
        LOGGER.debug("Constructing Establish effect step");
        try {
            final EstablishEffectView currentView = (EstablishEffectView) this.currentView;

            List<JellyBeanItem> jellyBeanItems = currentView.getEffects();
            AList<Effect> effects = jellyBeanItemsToEffectList(jellyBeanItems);

            EstablishedEffect establishedEffect = new EstablishedEffect(
                    new Experimentation(
                            INSTANCE.currentStimulation,
                            INSTANCE.currentSubject),
                    effects);
            EstablishEffectConclusion conclusion =
                    new EstablishEffectConclusion(
                            "Establish Effect",
                            establishedEffect);

            // TODO pass UploadedFile.uploadedFolder; in establishEffectConclusion

            adAPI.constructStep(INSTANCE.currentPattern.getId(),
                    INSTANCE.evidences, conclusion);
        } catch (WrongEvidenceException | StepBuildingException e) {
            LOGGER.error("Impossible to constructStep");
        }
    }

    private void constructGeneralizeStep(ArgumentationDiagramAPI adAPI) {
        LOGGER.debug("Constructing Generalize step");
        try {
            final GeneralizeView currentView = (GeneralizeView) this.currentView;

            // TODO pass UploadedFile.uploadedFolder; in GeneralizationConclusion

            GeneralizationConclusion conclusion = new GeneralizationConclusion(
                    "Generalization",
                    new EstablishedEffect(
                            new Experimentation(
                                    INSTANCE.currentStimulation,
                                    INSTANCE.currentSubject),
                            Bus.jellyBeanItemsToEffectList(currentView.getEffects())
                    ));

            adAPI.constructStep(INSTANCE.currentPattern.getId(),
                    INSTANCE.evidences, conclusion);
        } catch (WrongEvidenceException | StepBuildingException e) {
            LOGGER.error("Impossible to constructStep");
        }
    }

    private void constructStep(ArgumentationDiagramAPI adAPI) {
        switch (currentPattern.getName()) {
            case "Treat":
                constructTreatStep(adAPI);
                break;
            case "Establish Effect":
                constructEstablishEffectStep(adAPI);
                break;
            case "Generalize":
                constructGeneralizeStep(adAPI);
                break;
            default:
                LOGGER.error("Constructing \"" + currentPattern.getName() + "\" not implemented");
        }
    }

    private void setEvidencesInDataBus() {
        // Setting default Experiment results to Data bus
        Map<EffectEnum, ARangedEnum> experimentsResults = new HashMap<>();
        for (EffectEnum effect : EffectEnum.values()) {
            experimentsResults.put(effect, effect.getState());
        }
        Bus.setExperimentResults(experimentsResults);


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

                        Bus.setMonitoredSystem(ms);
                        break;
                    case "stimulation":
                        currentStimulation = (Stimulation) evidenceRole.getEvidence().getElement();

                        AList<AEntity> list = new AList<>();
                        list.setLabel("root");
                        list.addAll(currentStimulation.getFieldsContainer().values());
                        Bus.setExperimentParams(list);
                        break;
                    case "":
                        if (evidenceRole.getEvidence() instanceof EstablishEffectConclusion) {
                            LOGGER.debug("Got: " + evidenceRole);
                            EstablishEffectConclusion eec = (EstablishEffectConclusion) evidenceRole.getEvidence();
                            Bus.setExperimentResults(((EstablishedEffect) eec.getElement()).getEffects());
                            break;
                        }
                    default:
                        LOGGER.warn("Unknown Evidence role \"" + evidenceRole.getRole() + "\" in " + evidenceRole);
                }
            } catch (RuntimeException e) {
                LOGGER.error("Impossible to treat Evidence role: " + evidenceRole, e);
            }
        }
    }

    /**
     * Called by Selection view to inform orchestrator about what choice was done
     *
     * @param value The choice selected by user
     */
    public static void submitChoice(String value) {
        // Finding the pattern users choose
        Pattern selectedPattern = null;
        for (Pattern p : INSTANCE.patternList) {
            if (p.getName().equals(value)) {
                selectedPattern = p;
                break;
            }
        }

        if (selectedPattern == null) {
            LOGGER.warn("No pattern found with name: " + value);
        } else {
            INSTANCE.showViewFromPattern(selectedPattern);
        }
    }

    /**
     * Call this method when user is validating data he send to the current view
     */
    public static void onValidate() {
        INSTANCE.orchestrate();
    }
}
