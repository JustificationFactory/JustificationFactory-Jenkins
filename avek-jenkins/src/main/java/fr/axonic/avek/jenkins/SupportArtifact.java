package fr.axonic.avek.jenkins;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.os.SU;
import hudson.util.ListBoxModel;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

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
        public String getDisplayName() { return null; }
    }
}
