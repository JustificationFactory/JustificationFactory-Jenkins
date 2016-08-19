package fr.axonic.avek.gui;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.*;
import fr.axonic.avek.engine.instance.evidence.Stimulation;
import fr.axonic.avek.engine.instance.evidence.Subject;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.DataBus;
import fr.axonic.avek.gui.view.AbstractView;
import fr.axonic.avek.gui.view.LoadingView;
import fr.axonic.avek.gui.view.etablisheffect.EstablishEffectView;
import fr.axonic.avek.gui.view.frame.MainFrame;
import fr.axonic.avek.gui.view.generalize.GeneralizeView;
import fr.axonic.avek.gui.view.strategyselection.StrategySelectionView;
import fr.axonic.avek.gui.view.treat.TreatView;
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
    private final static Orchestrator INSTANCE = new Orchestrator();

    private AbstractView currentView;
    private Pattern currentPattern;
    private Subject currentSubject;
    private Stimulation currentStimulation;

    private final LoadingView loadingView = new LoadingView();

    private MainFrame frame;

    private List<Pattern> patternList;
    private List<EvidenceRole> evidences;
    private final Stack<FutureTask> tasks;

    private Orchestrator() {
        tasks = new Stack<>();
    }

    private void orchestrate() {
        FutureTask ft = new FutureTask<>(() -> { this.setFollowingView(); return true; });
        this.tasks.push(ft);

        // Setting a loading view up while orchestrator compute for the next view
        frame.setView(loadingView);
        frame.hideStrategyButton();

        new Thread(ft).start();
    }

    private void setFollowingView() throws VerificationException, WrongEvidenceException, ExecutionException, InterruptedException {
        ArgumentationDiagramAPIImpl adAPI = ArgumentationDiagramAPIImpl.getInstance();

        // Constructing conclusion
        if (currentPattern != null) {
            constructStep(adAPI);
            currentPattern = null;
        }

        // Preparing for following view
        evidences = adAPI.getBaseEvidences();
        List<String> patternIds=adAPI.getPossiblePatterns(evidences);
        patternList = new ArrayList<>();
        patternIds.stream().forEach(s -> patternList.add(adAPI.getPattern(s)));

        setEvidencesInDataBus();

        // If there is only one pattern available, setting the view to it
        if (patternList.size() == 1 && setViewFromPattern(patternList.get(0))) {
            return;
        }

        // If there is more than one pattern available,
        // setting a selection view for user to choose the one he want
        StrategySelectionView ssv = new StrategySelectionView();
        ssv.load();

        FutureTask setViewTask = new FutureTask<>(() -> { frame.setView(ssv); return true; });
        Platform.runLater(setViewTask);

        List<String> names = patternList.stream().map(Pattern::getName).collect(Collectors.toList());
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
                        jellyBeanItemsToEffectList(currentView.getEffects())
            ));

            adAPI.constructStep(INSTANCE.currentPattern.getId(),
                    INSTANCE.evidences, conclusion);
        } catch (WrongEvidenceException | StepBuildingException e) {
            LOGGER.error("Impossible to constructStep");
        }
    }
    private void constructStep(ArgumentationDiagramAPIImpl adAPI) {
        switch(currentPattern.getName()) {
            case "Treat":            constructTreatStep(adAPI);           break;
            case "Establish Effect": constructEstablishEffectStep(adAPI); break;
            case "Generalize":       constructGeneralizeStep(adAPI);      break;
            default:
                LOGGER.error("Constructing \""+currentPattern.getName()+"\" not implemented");
        }
    }

    private AList<Effect> jellyBeanItemsToEffectList(List<JellyBeanItem> effectsAsJellyBeanItems) {
        return effectsAsJellyBeanItems
                .stream()
                .map(this::jellyBeanItemToEffect)
                .collect(Collectors.toCollection(AList::new));
    }
    private Effect jellyBeanItemToEffect(JellyBeanItem jellyBeanItem) {
        EffectEnum[] eetab = EffectEnum.values();

        for (EffectEnum effectEnum : eetab) {
            if(jellyBeanItem.getIdentifier().equals(effectEnum)) {
                try {
                    Effect effect = new Effect();
                    effectEnum.setStateValue((EffectStateEnum) jellyBeanItem.getState());
                    effect.setEffectValue(effectEnum);
                    return effect;
                } catch (VerificationException e) {
                    LOGGER.error("Impossible to add effect "+jellyBeanItem, e);
                }
            }
        }

        LOGGER.error("Impossible to add effect "+jellyBeanItem);
        return null;
    }

    private void setEvidencesInDataBus() {
        // Setting default Experiment results to Data bus
        Map<EffectEnum, ARangedEnum> experimentsResults = new HashMap<>();
        for(EffectEnum effect : EffectEnum.values()) {
            experimentsResults.put(effect, effect.getState());
        }
        DataBus.setExperimentResults(experimentsResults);


        // Setting others data to Data bus
        for(EvidenceRole evidenceRole : evidences) {
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

                        DataBus.setMonitoredSystem(ms);
                        break;
                    case "stimulation":
                        currentStimulation = (Stimulation) evidenceRole.getEvidence().getElement();

                        AList<AEntity> list = new AList<>();
                        list.setLabel("root");
                        list.addAll(currentStimulation.getFieldsContainer().values());
                        DataBus.setExperimentParams(list);
                        break;
                    case "":
                        if(evidenceRole.getEvidence() instanceof EstablishEffectConclusion) {
                            LOGGER.debug("Got: "+evidenceRole);
                            EstablishEffectConclusion eec = (EstablishEffectConclusion) evidenceRole.getEvidence();
                            DataBus.setExperimentResults(((EstablishedEffect)eec.getElement()).getEffects());
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

    private boolean setViewFromPattern(Pattern p) {
        AbstractView view;

        // Selecting the right view depending on pattern
        switch (p.getName()) {
            case "Treat":            view = new TreatView();           break;
            case "Establish Effect": view = new EstablishEffectView(); break;
            case "Generalize":       view = new GeneralizeView();      break;
            default:
                LOGGER.warn("Pattern is unknown for View conversion: " + p);
                return false;
        }

        // Setting the view
        currentView = view;
        FutureTask ft = new FutureTask<>(() -> {
            frame.setView(currentView);
            frame.setStrategyButtonLabel("Treat");
            return true;
        });

        tasks.push(ft);
        Platform.runLater(ft);
        currentPattern = p;
        return true;
    }


    /**
     * Ask the orchestrator to orchestrate this frame
     * @param frame The Frame to orchestrate
     */
    static void setFrame(MainFrame frame) {
        INSTANCE.frame = frame;
        INSTANCE.orchestrate();
    }

    /**
     * Called by Selection view to inform orchestrator about what choice was done
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

        if(selectedPattern == null) {
            LOGGER.warn("No pattern found with name: "+value);
        }
        else {
            INSTANCE.setViewFromPattern(selectedPattern);
        }
    }

    /**
     * Call this method when user is validating data he send to the current view
     */
    public static void onValidate() {
        INSTANCE.orchestrate();
    }

    /**
     * <b>Blocking method</b> that finish when orchestrator has all his tasks done
     * @throws ExecutionException thrown if a task thrown an internal exception
     * @throws InterruptedException thrown if a task was interrupted
     */
    static void waitforOrchestrating() throws ExecutionException, InterruptedException {
        // While there are tasks in the taskList
        while(!INSTANCE.tasks.isEmpty()) {
            // Get oldest task from the list
            FutureTask ft = INSTANCE.tasks.pop();
            // Waiting for this task to end
            ft.get();
        }
    }
}
