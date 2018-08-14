package fr.axonic.avek.engine.exception;

public class AlreadyBuildingException extends StepBuildingException{
    public AlreadyBuildingException(String s) {
        super(s);
    }

    public AlreadyBuildingException(String message, Throwable cause) {
        super(message, cause);
    }
}
