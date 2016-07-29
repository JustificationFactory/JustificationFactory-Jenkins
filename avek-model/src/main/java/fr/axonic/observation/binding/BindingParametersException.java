package fr.axonic.observation.binding;

/**
 * Created by cboinaud on 19/07/2016.
 */
public class BindingParametersException extends Exception {

    public BindingParametersException() {
    }

    public BindingParametersException(String message) {
        super(message);
    }

    public BindingParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingParametersException(Throwable cause) {
        super(cause);
    }

    public BindingParametersException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
