package fr.axonic.avek.jenkins;

import fr.axonic.avek.engine.pattern.Pattern;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.os.SU;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import jcifs.smb.SmbFile;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

/**
 * Created by cduffau on 22/08/17.
 */
public class SupportArtifact extends AbstractDescribableImpl<SupportArtifact> {

    private String supportId;
    private String artifactPath;

    public String getSupportId() {
        return supportId;
    }

    @DataBoundSetter
    public void setSupportId(String supportId) {
        this.supportId = supportId;
    }

    public String getArtifactPath() {
        return artifactPath;
    }

    @DataBoundSetter
    public void setArtifactPath(String artifactPath) {
        this.artifactPath = artifactPath;
    }

    @DataBoundConstructor
    public SupportArtifact(String supportId, String artifactPath) {
        this.supportId = supportId;
        this.artifactPath = artifactPath;
    }

    public SupportArtifact() {
    }

    @Override
    public String toString() {
        return "SupportArtifact{" +
                "supportId='" + supportId + '\'' +
                ", artifactPath='" + artifactPath + '\'' +
                '}';
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<SupportArtifact> {

        public String getDisplayName() { return "Support Artifact"; }

        public FormValidation doCheckSupportId(@QueryParameter String value){
            ArgumentationFactoryBuilder.DescriptorImpl des=new ArgumentationFactoryBuilder.DescriptorImpl();

            /**System.out.println( argumentationSystemName+", "+patternID);
            if (value.length() == 0)
                return FormValidation.error("Please set a conclusion ID");
            try {
                Pattern pattern = new ArgumentationFactoryClient(des.getArgumentationFactoryURL()).getPattern(argumentationSystemName,patternID);
                if (pattern.getInputTypes().stream().noneMatch(inputType -> inputType.getType().getName().equals(value)))
                    return FormValidation.error("Unknown support ID");
            } catch (ArgumentationFactoryException e) {
                return FormValidation.error("Please set a valid argumentation system and pattern ID");
            }
            */

            return FormValidation.ok();
        }


        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {

            // ^Can also use req.bindJSON(this, formData);
            //  (easier when there are many fields; need set* methods for this, like setUseFrench)
            save();
            return super.configure(req,formData);
        }
    }
}
