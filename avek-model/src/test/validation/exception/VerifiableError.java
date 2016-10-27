package fr.axonic.validation.exception;


import fr.axonic.validation.Verifiable;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to get an Object which associate @ErrorCategory and @Verifiable concerned by the errors. It will
 * be used by the Validation API.
 */
public class VerifiableError {

    private List<Pair<ErrorCategory, List<Verifiable>>> errors;

    public VerifiableError() {
        errors = new ArrayList<>();
    }

    public VerifiableError(List<Pair<ErrorCategory, List<Verifiable>>> errors) {
        this.errors = errors;
    }

    /**
     * This method is used to add an error to the list.  If the parameter is null nothing will be added.
     *
     * @param error The error which will be added to the list.
     */
    public void addError(Pair<ErrorCategory, List<Verifiable>> error) {
        if (error != null) {
            errors.add(error);
        }
    }

    /**
     * This method is used to add an error provoked by a single object to the list.  If the parameter is null nothing
     * will be added.
     *
     * @param error The error which will be added to the list.
     */
    public void addSingleObjectError(Pair<ErrorCategory, Verifiable> error) {
        if (error != null) {
            List<Verifiable> v = new ArrayList<>();
            v.add(error.getValue());
            errors.add(new Pair<>(error.getKey(), v));
        }
    }

    /**
     * This method is used to add multiple error to the current list. If the parameter is null nothing will be added.
     *
     * @param verifiableError The @VerifiableError which contains the errors.
     */
    public void addErrors(VerifiableError verifiableError) {
        if (verifiableError != null && !verifiableError.getErrors().isEmpty()) {
            errors.addAll(verifiableError.getErrors());
        }
    }

    /**
     * Get all the errors stored in the current Object.
     *
     * @return The @List fo all error.
     */
    public List<Pair<ErrorCategory, List<Verifiable>>> getErrors() {
        return errors;
    }

    /**
     * Get the error for the specified @ErrorCategory enum (e.g : all error associated with the category
     *
     * @param errorCat The specified category associated to the errors
     * @return The error list associated to the specified category, an empty list if no errors are associated to
     * the category @UserErrorCategory.PERIOD_OVERLAPPING )
     */
    public Pair<ErrorCategory, List<Verifiable>> getErrors(ErrorCategory errorCat) {
        List<Verifiable> listError = new ArrayList<>();
        for (Pair<ErrorCategory, List<Verifiable>> pair : errors) {
            if (pair.getKey().equals(errorCat)) {
                listError.addAll(pair.getValue());
            }
        }
        return new Pair<>(errorCat, listError);
    }

    /**
     * This method is used to get the errors by category type (e.g : @UserErrorCategory , @TechnicalErrorCategory )
     *
     * @param errorCat The @Class of the category type which will be returned
     * @param <E>      An Class which implement ErrorCategory
     * @return An @List of errors in the specified category type, the list will be empty if there is no error
     * in the category type.
     */
    public <E extends ErrorCategory> List<Pair<E, List<Verifiable>>> getErrorsInCategoryType(Class<E> errorCat) {
        List<Pair<E, List<Verifiable>>> listError = new ArrayList<>();
        for (Pair<ErrorCategory, List<Verifiable>> pair : errors) {
            if (pair.getKey().getClass().isAssignableFrom(errorCat)) {
                listError.add((Pair<E, List<Verifiable>>) pair);
            }
        }
        return listError;
    }

    /**
     * This method is used to get the errors by @Verifiable
     *
     * @param verifiable The @Verifiable which can contains some errors.
     * @return An @List of errors for the specified @Verifiable , the list will be empty if there is no error for the
     * verifiable.
     */
    public List<Pair<ErrorCategory, List<Verifiable>>> getErrorByVerifiable(Verifiable verifiable) {
        List<Pair<ErrorCategory, List<Verifiable>>> listError = new ArrayList<>();
        for (Pair<ErrorCategory, List<Verifiable>> pair : errors) {
            for (Verifiable ver : pair.getValue()) {
                if (ver == verifiable) {
                    listError.add(pair);
                }
            }
        }
        return listError;
    }

    /**
     * This method is used to get the errors categories associated to a @Verifiable
     *
     * @param verifiable The @Verifiable which can contains some errors.
     * @return An @List of @ErrorCategory for the specified @Verifiable , the list will be empty if there is no error
     * for the verifiable.
     */
    public List<ErrorCategory> getErrorCategoriesByVerifiable(Verifiable verifiable) {
        List<ErrorCategory> listError = new ArrayList<>();
        for (Pair<ErrorCategory, List<Verifiable>> pair : errors) {
            for (Verifiable ver : pair.getValue()) {
                if (ver == verifiable) {
                    listError.add(pair.getKey());
                }
            }
        }
        return listError;
    }

    public boolean isEmpty(){
        return errors.size() == 0;
    }

    @Override
    public String toString() {
        String str = "";
        for (Pair<ErrorCategory, List<Verifiable>> pr : getErrors()) {
            str += pr.getKey().getClass().getSimpleName()+"."+pr.getKey() + " : \n";
            for (Verifiable vr : pr.getValue()) {
                str += " -- "+vr.toString()+"\n";
            }
        }
        return str;
    }
}
