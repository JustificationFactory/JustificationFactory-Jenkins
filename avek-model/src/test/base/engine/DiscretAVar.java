package fr.axonic.base.engine;



import fr.axonic.validation.exception.VerificationException;

/**
 * Created by cduffau on 08/07/16.
 */
public interface DiscretAVar<T extends AVar> extends AVarLimit{

    AList<T> getRange();
    void setRange(AList<T> range) throws VerificationException;

}
