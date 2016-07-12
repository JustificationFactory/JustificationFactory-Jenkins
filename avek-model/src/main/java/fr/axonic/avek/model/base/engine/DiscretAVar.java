package fr.axonic.avek.model.base.engine;

import fr.axonic.avek.model.verification.exception.VerificationException;

import java.util.List;

/**
 * Created by cduffau on 08/07/16.
 */
public interface DiscretAVar<T> extends AVarLimit{

    List<T> getRange();
    void setRange(List<T> range) throws VerificationException;

}
