package fr.axonic.avek.gui;

import fr.axonic.avek.engine.ArgumentationDiagramAPIImpl;
import fr.axonic.avek.engine.Pattern;
import fr.axonic.avek.engine.StepBuildingException;
import fr.axonic.avek.engine.WrongEvidenceException;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.*;
import fr.axonic.avek.engine.instance.evidence.Stimulation;
import fr.axonic.avek.engine.instance.evidence.Subject;
import fr.axonic.avek.gui.model.DataBus;
import fr.axonic.avek.gui.view.*;
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
    private final List<FutureTask> tasks;

    private Orchestrator() {
        tasks = new ArrayList<>();

        Map<String, ARangedEnum> map = new HashMap<>();
        for(EffectEnum effect : EffectEnum.values()) {
            map.put(effect.name(), effect.getState());
        }
        DataBus.setExperimentResults(map);
    }

    private void orchestrate() {
        FutureTask ft = new FutureTask<>(() -> { this.setFollowingView(); return true; });
        this.tasks.add(ft);

        frame.setView(loadingView);
        frame.hideStrategyButton();

        new Thread(ft).start();
    }

    private void setFollowingView() throws VerificationException, WrongEvidenceException, ExecutionException, InterruptedException {
        ArgumentationDiagramAPIImpl adAPI = ArgumentationDiagramAPIImpl.getInstance();

        // Constructing conclusion
        if (currentPattern != null) {
            constructStep(adAPI);
        }

        evidences = null;
        patternList = null;
        currentPattern = null;

        // Preparing following view
        evidences = adAPI.getBaseEvidences();
        patternList = adAPI.getPossiblePatterns(evidences);

        setEvidencesInDataBus();

        if (patternList.size() == 1) {
            if (setViewFromPattern(patternList.get(0))) {
                return;
            }
        }

        StrategySelectionView ssv = new StrategySelectionView();
        ssv.load();

        FutureTask setViewTask = new FutureTask<>(() -> { frame.setView(ssv); return true; });
        Platform.runLater(setViewTask);

        List<String> names = patternList.stream().map(Pattern::getName).collect(Collectors.toList());
        if (names != null) {
            ssv.setAvailableChoices(names);
        }

        tasks.add(setViewTask);
    }

    private void constructStep(ArgumentationDiagramAPIImpl adAPI) {
        switch(currentPattern.getName()) {
            case "Treat":
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
                break;
            case "Establish Effect":
                LOGGER.debug("Constructing Establish effect step");
                try {
                    final EstablishEffectView currentView = (EstablishEffectView) this.currentView;

                    adAPI.constructStep(INSTANCE.currentPattern.getId(),
                            INSTANCE.evidences,
                            new EstablishEffectConclusion(
                                    "Establish Effect",new EstablishedEffect(
                                    new Experimentation(
                                            INSTANCE.currentStimulation,
                                            INSTANCE.currentSubject),
                                    toEffectList(currentView.getEffects())
                            )));
                } catch (WrongEvidenceException | StepBuildingException e) {
                    LOGGER.error("Impossible to constructStep");
                }
                break;
            case "Generalize":
                LOGGER.debug("Constructing Generalize step");
                try {
                    final GeneralizeView currentView = (GeneralizeView) this.currentView;

                    adAPI.constructStep(INSTANCE.currentPattern.getId(),
                            INSTANCE.evidences,
                            new GeneralizationConclusion(
                                    "Generalization",new EstablishedEffect(
                                            new Experimentation(
                                                    INSTANCE.currentStimulation,
                                                    INSTANCE.currentSubject),
                                    toEffectList(currentView.getEffects())
                            )));
                } catch (WrongEvidenceException | StepBuildingException e) {
                    LOGGER.error("Impossible to constructStep");
                }
                break;
            default:
                LOGGER.error("Constructing \""+currentPattern.getName()+"\" not implemented");
        }
    }

    private AList<Effect> toEffectList(Map<String, String> effectsAsMap) {
        final AList<Effect> effectList = new AList<>();

        EffectEnum[] eetab = EffectEnum.values();
        for(String key : effectsAsMap.keySet()) {
            for (EffectEnum effectEnum : eetab) {
                if(key.equals(effectEnum.toString())) {
                    try {
                        Effect effect = new Effect();
                        effect.setEffectValue(effectEnum);
                        effectList.add(effect);
                    } catch (VerificationException e) {
                        LOGGER.error("Impossible to add effect "+key, e);
                    }
                }
            }
        }

        return effectList;
    }

    private void setEvidencesInDataBus() {
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
                    default:
                        LOGGER.warn("Unknown Evidence role \"" + evidenceRole.getRole() + "\" in " + evidenceRole);
                }
            } catch (RuntimeException e) {
                LOGGER.error("Impossible to treat Evidence role: " + evidenceRole, e);
            }
        }
    }

    private boolean setViewFromPattern(Pattern p) {
        FutureTask ft = null;
        switch (p.getName()) {
            case "Treat":
                TreatView tv = new TreatView();
                currentView = tv;
                ft = new FutureTask<>(() -> {
                    frame.setView(tv);
                    frame.setStrategyButtonLabel("Treat");
                    return true;
                });
                break;
            case "Establish Effect":
                EstablishEffectView eev = new EstablishEffectView();
                currentView = eev;
                ft = new FutureTask<>(() -> {
                    frame.setView(eev);
                    frame.setStrategyButtonLabel("Establish Effect");
                    return true;
                });
                break;
            case "Generalize":
                GeneralizeView gv = new GeneralizeView();
                currentView = gv;
                ft = new FutureTask<>(() -> {
                    frame.setView(gv);
                    frame.setStrategyButtonLabel("Generalize");
                    return true;
                });
                break;
            default:
                LOGGER.warn("Pattern is unknown for View conversion: " + p);
                return false;
        }

        tasks.add(ft);
        Platform.runLater(ft);
        currentPattern = p;
        return true;
    }


    static void setFrame(MainFrame frame) {
        INSTANCE.frame = frame;
        INSTANCE.orchestrate();
    }

    public static void submitChoice(String value) {
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

    public static void onValidate() {
        INSTANCE.orchestrate();
    }

    static void waitforOrchestrating() throws ExecutionException, InterruptedException {
        while(!INSTANCE.tasks.isEmpty()) {
            FutureTask ft = INSTANCE.tasks.get(0);
            ft.get();
            INSTANCE.tasks.remove(ft);
        }
    }
}
