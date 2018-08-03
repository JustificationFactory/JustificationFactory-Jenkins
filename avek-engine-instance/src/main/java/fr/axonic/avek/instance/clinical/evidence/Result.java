package fr.axonic.avek.instance.clinical.evidence;

import fr.axonic.avek.engine.support.evidence.Element;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AList;

public class Result extends Element {

    private AList<AString> filePaths;

    public Result(AList<AString> filePaths) {
        this.filePaths = filePaths;
    }

    public AList<AString> getFilePaths() {
        return filePaths;
    }

    @Override
    public String toString() {
        return "Result{" +
                "filePaths=" + filePaths +
                '}';
    }
}
