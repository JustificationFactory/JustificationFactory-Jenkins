package fr.axonic.avek.jenkins;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.evidence.DocumentEvidence;
import fr.axonic.avek.instance.jenkins.conclusion.JenkinsStatus;
import fr.axonic.avek.instance.jenkins.conclusion.JenkinsStatusEnum;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.scm.SCM;
import hudson.tasks.*;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.apache.commons.validator.routines.UrlValidator;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Sample {@link Builder}.
 *
 * <p>
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked
 * and a new {@link fr.axonic.avek.jenkins.ArgumentationFactoryBuilder} is created. The created
 * instance is persisted to the project configuration XML by using
 * XStream, so this allows you to use instance fields (like {@link #argumentationSystemName})
 * to remember the configuration.
 *
 * <p>
 * When a build is performed, the {@link #perform} method will be invoked. 
 *
 * @author Kohsuke Kawaguchi
 */
public class ArgumentationFactoryBuilder extends Publisher implements SimpleBuildStep{


    private final String argumentationSystemName;
    private final String patternId, conclusionId;
    private final JenkinsStatus jenkinsStatus;
    private ArgumentationFactoryClient argumentationFactoryClient;

    private  List<SupportArtifact> supports;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public ArgumentationFactoryBuilder(String argumentationSystemName, String patternId, String conclusionId, SupportArtifact[] supports) {
        this.argumentationSystemName = argumentationSystemName;
        this.patternId=patternId;
        this.jenkinsStatus =new JenkinsStatus();
        this.conclusionId=conclusionId;
        this.supports = Arrays.asList(supports);
        this.argumentationFactoryClient=new ArgumentationFactoryClient(getDescriptor().getArgumentationFactoryURL());
    }

    public JenkinsStatus getJenkinsStatus() {
        return jenkinsStatus;
    }

    public String getConclusionId() {
        return conclusionId;
    }

    public SupportArtifact[] getSupports() {
        return supports.toArray(new SupportArtifact[supports.size()]);
    }




    /**
     * We'll use this from the {@code config.jelly}.
     */
    public String getArgumentationSystemName() {
        return argumentationSystemName;
    }

    public String getPatternId() {
        return patternId;
    }

    @Override
    public void perform(Run<?,?> build, FilePath workspace, Launcher launcher, TaskListener listener) {
        // This is where you 'build' the project.
        // Since this is a dummy, we just say 'hello world' and call that a build.
        listener.getLogger().println("URL : "+getDescriptor().getArgumentationFactoryURL());
        listener.getLogger().println("Argumentation System :"+ argumentationSystemName +", pattern ID : "+patternId);
        listener.getLogger().println("Supports :"+supports);
        listener.getLogger().println("Conclusion :"+ conclusionId);

        try {
            JenkinsStatusEnum status=Result.SUCCESS.equals(build.getResult())?JenkinsStatusEnum.OK:JenkinsStatusEnum.KO;
            jenkinsStatus.setStatus(status);
            jenkinsStatus.setVersion(build.getEnvironment(listener).get("GIT_COMMIT"));
            if(status==JenkinsStatusEnum.OK){

                Step step=argumentationFactoryClient.sendStep(argumentationSystemName,patternId, conclusionId, jenkinsStatus, supports);
                listener.getLogger().println("Pushed on "+getDescriptor().getArgumentationFactoryURL());
                for(SupportRole support : step.getEvidences()){
                    for(SupportArtifact artifact:supports){
                        File file=new File(artifact.getArtifactPath());
                        if(file.getName().equals(((Document)support.getSupport().getElement()).getUrl().getValue())){
                            SmbUtil smbUtil=SmbUtil.getSmbUtil(getDescriptor().getSmbDir(),argumentationSystemName,patternId, step.getId(),support.getSupport().getId(), artifact.getArtifactPath());
                            smbUtil.copy();
                            break;
                        }

                    }
                }
                listener.getLogger().println("Copy artifacts on "+getDescriptor().getSmbDir());
            }
            else{
                listener.error("Build status : "+build.getResult().toString()+". Impossible to trace results");
            }

        } catch (ResponseException e) {
            listener.fatalError("ERROR CODE : "+e.getStatusCode());
            listener.fatalError(e.getReason());
            build.setResult(Result.FAILURE);
        } catch (IOException | InterruptedException e) {
            listener.fatalError(e.getMessage());
            build.setResult(Result.FAILURE);
        }
    }


    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }


    /**
     * Descriptor for {@link fr.axonic.avek.jenkins.ArgumentationFactoryBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See {@code src/main/resources/hudson/plugins/hello_world/ArgumentationFactoryBuilder/*.jelly}
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        /**
         * To persist global configuration information,
         * simply store it in a field and call save().
         *
         * <p>
         * If you don't want fields to be persisted, use {@code transient}.
         */
        private String argumentationFactoryURL;
        private String smbDir;

        /**
         * In order to load the persisted global configuration, you have to 
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        /**
         * Performs on-the-fly validation of the form field 'argumentationSystemName'.
         *
         * @param value
         *      This parameter receives the value that the user has typed.
         * @return
         *      Indicates the outcome of the validation. This is sent to the browser.
         *      <p>
         *      Note that returning {@link FormValidation#error(String)} does not
         *      prevent the form from being saved. It just means that a message
         *      will be displayed to the user. 
         */
        public FormValidation doCheckArgumentationFactoryURL(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set an URL");
            String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
            UrlValidator urlValidator = new UrlValidator(schemes);
            if (!urlValidator.isValid(value))
                return FormValidation.warning("Please set a valid URL");
            return FormValidation.ok();
        }

        public FormValidation doCheckSmbDirURL(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set an URL");
            String[] schemes = {"smb"}; // DEFAULT schemes = "http", "https", "ftp"
            UrlValidator urlValidator = new UrlValidator(schemes);
            if (!urlValidator.isValid(value))
                return FormValidation.warning("Please set a valid URL");
            return FormValidation.ok();
        }

        public FormValidation doCheckArgumentationSystemName(@QueryParameter String value) {
            if (value.length() == 0)
                return FormValidation.error("Please set a argumentation system");
            try {
                List<String> argumentationSystems=new ArgumentationFactoryClient(getArgumentationFactoryURL()).getArgumentationSystems();
                if(argumentationSystems.stream().noneMatch(s -> s.equals(value))){
                    return FormValidation.error("Unknown argumentation system "+value);
                }
            } catch (ArgumentationFactoryException e) {
                return FormValidation.error("Please set a valid argumentation system");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckPatternId(@QueryParameter String value, @QueryParameter String argumentationSystemName){
            if (value.length() == 0)
                return FormValidation.error("Please set a pattern");
            try {
                List<String> patterns = new ArgumentationFactoryClient(getArgumentationFactoryURL()).getPatterns(argumentationSystemName);
                if (patterns.stream().noneMatch(pattern -> pattern.equals(value)))
                    return FormValidation.error("Unknown pattern ID in "+argumentationSystemName);
            } catch (ArgumentationFactoryException e) {
                return FormValidation.error("Please set a valid argumentation system");
            }


            return FormValidation.ok();
        }

        public FormValidation doCheckConclusionId(@QueryParameter String value, @QueryParameter String argumentationSystemName, @QueryParameter String patternId){
            if (value.length() == 0)
                return FormValidation.error("Please set a conclusion ID");
            try {
               Pattern pattern = new ArgumentationFactoryClient(getArgumentationFactoryURL()).getPattern(argumentationSystemName,patternId);
                if (!pattern.getOutputType().getType().getName().equals(value))
                    return FormValidation.error("Unknown conclusion ID");
            } catch (ArgumentationFactoryException e) {
                return FormValidation.error("Please set a valid argumentation system and pattern ID");
            }


            return FormValidation.ok();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Trace with Argumentation Factory";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            // To persist global configuration information,
            // set that to properties and call save().
            argumentationFactoryURL = formData.getString("argumentationFactoryURL");
            smbDir=formData.getString("smbDirURL");

            // ^Can also use req.bindJSON(this, formData);
            //  (easier when there are many fields; need set* methods for this, like setUseFrench)
            save();
            return super.configure(req,formData);
        }

        /**
         * This method returns true if the global configuration says we should speak French.
         *
         * The method argumentationSystemName is bit awkward because global.jelly calls this method to determine
         * the initial state of the checkbox by the naming convention.
         */
        public String getArgumentationFactoryURL() {
            return argumentationFactoryURL;
        }

        public String getSmbDir() {
            return smbDir;
        }
    }
}

