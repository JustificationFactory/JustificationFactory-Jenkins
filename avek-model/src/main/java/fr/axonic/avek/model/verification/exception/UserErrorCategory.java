package fr.axonic.avek.model.verification.exception;

/**
 * Created by lbrouchet on 08/07/2015.
 * This enum contain the errors which can occurred during the validation. These error are made by the User.
 */
public enum UserErrorCategory implements ErrorCategory {
    PERIOD_OVERLAPPING,
    INCORRECT_AMPLITUDE,
    INCORRECT_DURATION,
    INCORRECT_FREQUENCY;
}

