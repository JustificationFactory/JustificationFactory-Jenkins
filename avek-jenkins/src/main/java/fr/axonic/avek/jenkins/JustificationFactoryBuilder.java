package fr.axonic.avek.jenkins;

import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.instance.DocumentEvidence;
import fr.axonic.avek.instance.jenkins.conclusion.JenkinsStatus;
import fr.axonic.avek.instance.jenkins.conclusion.JenkinsStatusEnum;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.*;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.apache.commons.validator.routines.UrlValidator;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Sample {@link Builder}.
 *
 * <p>
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked
 * and a new {@link JustificationFactoryBuilder} is created. The created
 * instance is persisted to the project configuration XML by using
 * XStream, so this allows you to use instance fields (like {@link #justificationSystemName})
 * to remember the configuration.
 *
 * <p>
 * When a build is performed, the {@link #perform} method will be invoked. 
 *
 * @author Kohsuke Kawaguchi
 */
public class JustificationFactoryBuilder extends Publisher implements SimpleBuildStep{

    private static final Logger LOGGER= LoggerFactory.getLogger(JustificationFactoryBuilder.class);

    private final String justificationSystemName;
    private final String patternId;
    private final JenkinsStatus jenkinsStatus;
    private JustificationFactoryClient justificationFactoryClient;

    private  List<SupportArtifact> supports;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public JustificationFactoryBuilder(String justificationSystemName, String patternId, SupportArtifact[] supports) {
        this.justificationSystemName = justificationSystemName;
        this.patternId=patternId;
        this.jenkinsStatus =new JenkinsStatus();
        this.supports = Arrays.asList(supports);
        this.justificationFactoryClient =new JustificationFactoryClient(getDescriptor().getJustificationFactoryURL());
    }

    public JenkinsStatus getJenkinsStatus() {
        return jenkinsStatus;
    }


    public SupportArtifact[] getSupports() {
        return supports.toArray(new SupportArtifact[supports.size()]);
    }




    /**
     * We'll use this from the {@code config.jelly}.
     */
    public String getJustificationSystemName() {
        return justificationSystemName;
    }

    public String getPatternId() {
        return patternId;
    }

    @Override
    public void perform(Run<?,?> build, FilePath workspace, Launcher launcher, TaskListener listener) {
        // This is where you 'build' the project.
        // Since this is a dummy, we just say 'hello world' and call that a build.
        listener.getLogger().println("URL : "+getDescriptor().getJustificationFactoryURL());
        listener.getLogger().println("Justification System :"+ justificationSystemName +", pattern ID : "+patternId);
        listener.getLogger().println("Supports :"+supports);

        try {
            JenkinsStatusEnum status=Result.SUCCESS.equals(build.getResult())?JenkinsStatusEnum.OK:JenkinsStatusEnum.KO;
            jenkinsStatus.setStatus(status);
            jenkinsStatus.setVersion(build.getEnvironment(listener).get("GIT_COMMIT"));
            if(status==JenkinsStatusEnum.OK){

                JustificationStep step= justificationFactoryClient.sendStep(justificationSystemName,patternId, jenkinsStatus, supports);
                listener.getLogger().println("Pushed on "+getDescriptor().getJustificationFactoryURL());
                for(Support support : step.getSupports()){
                    for(SupportArtifact artifact:supports){
                        File file=new File(artifact.getArtifactPath());
                        if(support instanceof DocumentEvidence &&file.getName().equals(((Document)support.getElement()).getUrl())){
                            SmbUtil smbUtil=SmbUtil.getSmbUtil(getDescriptor().getSmbDirURL(), justificationSystemName,patternId, step.getId(),support.getId(), artifact.getArtifactPath());
                            smbUtil.copy();
                            break;
                        }

                    }
                }
                listener.getLogger().println("Copy artifacts on "+getDescriptor().getSmbDirURL());
            }
            else{
                listener.error("Build status : "+build.getResult()+". Impossible to trace results");
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
     * Descriptor for {@link JustificationFactoryBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See {@code src/main/resources/hudson/plugins/hello_world/JustificationFactoryBuilder/*.jelly}
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
        private String justificationFactoryURL;
        private String smbDirURL;

        /**
         * In order to load the persisted global configuration, you have to 
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        /**
         * Performs on-the-fly validation of the form field 'justificationSystemName'.
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
        public FormValidation doCheckJustificationFactoryURL(@QueryParameter String value)
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

        public FormValidation doCheckJustificationSystemName(@QueryParameter String value) {
            if (value.length() == 0)
                return FormValidation.error("Please set a justification system");
            try {
                List<String> argumentationSystems=new JustificationFactoryClient(getJustificationFactoryURL()).getArgumentationSystems();
                if(argumentationSystems.stream().noneMatch(s -> s.equals(value))){
                    return FormValidation.error("Unknown justification system "+value);
                }
            } catch (JustificationFactoryException e) {
                return FormValidation.error("Please set a valid justification system");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckPatternId(@QueryParameter String value, @QueryParameter String argumentationSystemName){
            if (value.length() == 0)
                return FormValidation.error("Please set a pattern");
            try {
                List<String> patterns = new JustificationFactoryClient(getJustificationFactoryURL()).getPatterns(argumentationSystemName);
                if (patterns.stream().noneMatch(pattern -> pattern.equals(value)))
                    return FormValidation.error("Unknown pattern ID in "+argumentationSystemName);
            } catch (JustificationFactoryException e) {
                return FormValidation.error("Please set a valid justification system");
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
            return "Trace with Justification Factory";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            // To persist global configuration information,
            // set that to properties and call save().
            justificationFactoryURL = formData.getString("justificationFactoryURL");
            smbDirURL =formData.getString("smbDirURL");
            LOGGER.info(formData.toString());
            // ^Can also use req.bindJSON(this, formData);
            //  (easier when there are many fields; need set* methods for this, like setUseFrench)
            save();
            return super.configure(req,formData);
        }

        /**
         * This method returns true if the global configuration says we should speak French.
         *
         * The method justificationSystemName is bit awkward because global.jelly calls this method to determine
         * the initial state of the checkbox by the naming convention.
         */
        public String getJustificationFactoryURL() {
            return justificationFactoryURL;
        }

        public String getSmbDirURL() {
            return smbDirURL;
        }
    }
}

