package fr.axonic.avek.model.verification.exception;

/**
 * Created by lbrouchet on 23/07/2015.
 * This enum contain the Error category possible with ANumber
 */
public enum ANumberErrorCategory implements ErrorCategory{
    MIN_NOT_RESPECTED,
    MAX_NOT_RESPECTED,
    WRONG_MIN_MAX
}
