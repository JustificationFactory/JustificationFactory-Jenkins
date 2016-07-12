package fr.axonic.avek.model.base.engine;

import fr.axonic.avek.model.verification.exception.VerificationException;

/**
 * Created by cduffau on 11/07/16.
 */
public interface ContinuousAVar<T> extends AVarLimit {

    T getMin();

    void setMin(T min) throws VerificationException;

    T getMax();

    void setMax(T max) throws VerificationException;
}
