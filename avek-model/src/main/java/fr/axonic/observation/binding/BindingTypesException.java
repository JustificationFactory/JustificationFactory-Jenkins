package fr.axonic.observation.binding;

/**
 * Created by cboinaud on 19/07/2016.
 */
public class BindingTypesException extends Exception {

    public BindingTypesException() {
    }

    public BindingTypesException(String message) {
        super(message);
    }

    public BindingTypesException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingTypesException(Throwable cause) {
        super(cause);
    }

    public BindingTypesException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
