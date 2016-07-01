package fr.axonic.avek.gui.model.verification.exception;

/**
 * Created by lbrouchet on 08/07/2015.
 * This enum contain the technical error which can happened when an error occurred during the validation.
 */
public enum TechnicalErrorCategory implements ErrorCategory {
    NULL_PARAMETER,
    WRONG_NUMBER_OF_PARAMETER,
    NULL_OBJECT;

}
