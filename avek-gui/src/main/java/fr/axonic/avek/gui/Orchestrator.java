package fr.axonic.avek.gui;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.*;
import fr.axonic.avek.engine.instance.evidence.Stimulation;
import fr.axonic.avek.engine.instance.evidence.Subject;
import fr.axonic.avek.gui.model.DataBus;
import fr.axonic.avek.gui.view.*;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.engine.*;
import fr.axonic.validation.exception.VerificationException;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    private Orchestrator() {
    }

    private void orchestrate() throws VerificationException, WrongEvidenceException {
        frame.setView(loadingView);
        frame.hideStrategyButton();

        ArgumentationDiagramAPIImpl adAPI = ArgumentationDiagramAPIImpl.getInstance();

        new Thread(() -> {
            // Constructing conclusion

            if(currentPattern != null) {
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
            Platform.runLater(() -> frame.setView(ssv));
            List<String> names = patternList.stream().map(Pattern::getName).collect(Collectors.toList());
            if(names != null)
                ssv.setAvailableChoices(names);
        }).start();
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

                    final AList<Effect> effectList = new AList<>();

                    Map<String, String> effectsAsMap = currentView.getEffects();

                    // TODO This is a mock
                    EffectEnum[] eetab = EffectEnum.values();
                    for(EffectEnum ee : eetab) {
                        try {
                            Effect e = new Effect();
                            e.setEffectValue(ee);
                            effectList.add(e);
                        } catch (VerificationException e1) {
                            e1.printStackTrace();
                        }
                    }

                    /*for(String key : effectsAsMap.keySet()) {
                        effectList.add(new Effect(   ));
                    }*/


                    adAPI.constructStep(INSTANCE.currentPattern.getId(),
                            INSTANCE.evidences,
                            new EstablishEffectConclusion(
                                    "Establish Effect",new EstablishedEffect(
                                    new Experimentation(
                                            INSTANCE.currentStimulation,
                                            INSTANCE.currentSubject),
                                    effectList
                            )));
                } catch (WrongEvidenceException | StepBuildingException e) {
                    LOGGER.error("Impossible to constructStep");
                }
                break;
            case "Generalize":
                LOGGER.debug("Constructing Generalize step");
                try {
                    final GeneralizeView currentView = (GeneralizeView) this.currentView;

                    final AList<Effect> effectList = new AList<>();

                    // TODO This is a mock
                    EffectEnum[] eetab = EffectEnum.values();
                    for(EffectEnum ee : eetab) {
                        try {
                            Effect e = new Effect();
                            e.setEffectValue(ee);
                            effectList.add(e);
                        } catch (VerificationException e1) {
                            e1.printStackTrace();
                        }
                    }

                    adAPI.constructStep(INSTANCE.currentPattern.getId(),
                            INSTANCE.evidences,
                            new GeneralizationConclusion(
                                    "Generalization",new EstablishedEffect(
                                            new Experimentation(
                                                    INSTANCE.currentStimulation,
                                                    INSTANCE.currentSubject),
                                    effectList
                            )));
                } catch (WrongEvidenceException | StepBuildingException e) {
                    LOGGER.error("Impossible to constructStep");
                }
                break;
            default:
                LOGGER.error("Constructing \""+currentPattern.getName()+"\" not implemented");
        }
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
        switch (p.getName()) {
            case "Treat":
                TreatView tv = new TreatView();
                currentView = tv;
                Platform.runLater(() -> {
                    frame.setView(tv);
                    frame.setStrategyButtonLabel("Treat");
                });
                break;
            case "Establish Effect":
                EstablishEffectView eev = new EstablishEffectView();
                currentView = eev;
                Platform.runLater(() -> {
                    frame.setView(eev);
                    frame.setStrategyButtonLabel("Establish Effect");
                });
                break;
            case "Generalize":
                GeneralizeView gv = new GeneralizeView();
                currentView = gv;
                Platform.runLater(() -> {
                    frame.setView(gv);
                    frame.setStrategyButtonLabel("Generalize");
                });
                break;
            default:
                LOGGER.warn("Pattern is unknown for View conversion: " + p);
                return false;
        }

        currentPattern = p;
        return true;
    }


    static void setFrame(MainFrame frame) {
        INSTANCE.frame = frame;
        try {
            INSTANCE.orchestrate();
        } catch (VerificationException | WrongEvidenceException e) {
            LOGGER.error("Impossible to get ArgumentationDiagramAPIImpl", e);
        }
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
        try {
            INSTANCE.orchestrate();
        } catch (VerificationException | WrongEvidenceException e) {
            LOGGER.error("Impossible to get ArgumentationDiagramAPIImpl", e);
        }
    }
}
