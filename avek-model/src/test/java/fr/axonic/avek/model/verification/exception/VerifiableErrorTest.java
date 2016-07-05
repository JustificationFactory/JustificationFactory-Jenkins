package fr.axonic.avek.model.verification.exception;


import javafx.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lbrouchet on 08/07/2015.
 */
public class VerifiableErrorTest {

    /**Pole p;
    Pole pp;
    Pole p2;

    @Test
    public void testGetErrors() throws VerificationException {
        VerifiableError listTest = getBasicList();
        assertEquals(2, listTest.getErrors().size());
        assertEquals(UserErrorCategory.INCORRECT_AMPLITUDE, listTest.getErrors().get(0).getKey());
        assertEquals(2, listTest.getErrors().get(0).getValue().size());
        assertEquals(Pole.class, listTest.getErrors().get(0).getValue().get(0).getClass());
        assertEquals(Pole.class, listTest.getErrors().get(0).getValue().get(1).getClass());
        assertEquals(1, ((Pole) listTest.getErrors().get(0).getValue().get(0)).getPoleNumber().intValue());
        assertEquals(2, ((Pole) listTest.getErrors().get(0).getValue().get(1)).getPoleNumber().intValue());
        assertTrue(Float.compare(((Pole) listTest.getErrors().get(0).getValue().get(0)).getRatio().floatValue(), 1.0f) == 0);
        assertTrue(Float.compare(((Pole) listTest.getErrors().get(0).getValue().get(1)).getRatio().floatValue(), 0.5f) == 0);
    }

    @Test
    public void testGetErrorsByCategory() throws VerificationException {
        VerifiableError listTest = getBasicList();
        assertEquals(2, listTest.getErrors(UserErrorCategory.INCORRECT_AMPLITUDE).getValue().size());
        assertEquals(UserErrorCategory.INCORRECT_AMPLITUDE,
                listTest.getErrors(UserErrorCategory.INCORRECT_AMPLITUDE).getKey());
        assertEquals(new Pole(1, 1.0f), listTest.getErrors(UserErrorCategory.INCORRECT_AMPLITUDE).getValue().get(0));
        assertEquals(new Pole(2, 0.5f), listTest.getErrors(UserErrorCategory.INCORRECT_AMPLITUDE).getValue().get(1));

        assertEquals(2, listTest.getErrors(TechnicalErrorCategory.NULL_PARAMETER).getValue().size());
        assertEquals(TechnicalErrorCategory.NULL_PARAMETER,
                listTest.getErrors(TechnicalErrorCategory.NULL_PARAMETER).getKey());
        assertEquals(new Pole(3, 1.0f), listTest.getErrors(TechnicalErrorCategory.NULL_PARAMETER).getValue().get(0));
        assertEquals(new Pole(2, 0.5f), listTest.getErrors(TechnicalErrorCategory.NULL_PARAMETER).getValue().get(1));
    }

    @Test
    public void testGetErrorsInCategoryType() throws VerificationException {
        VerifiableError listTest = getBasicList();
        assertEquals(1, listTest.getErrorsInCategoryType(UserErrorCategory.class).size());
        assertEquals(UserErrorCategory.INCORRECT_AMPLITUDE,
                listTest.getErrorsInCategoryType(UserErrorCategory.class).get(0).getKey());
        assertEquals(2, listTest.getErrorsInCategoryType(UserErrorCategory.class).get(0).getValue().size());
        assertEquals(new Pole(1, 1.0f),
                listTest.getErrorsInCategoryType(UserErrorCategory.class).get(0).getValue().get(0));
        assertEquals(new Pole(2, 0.5f),
                listTest.getErrorsInCategoryType(UserErrorCategory.class).get(0).getValue().get(1));

        assertEquals(1, listTest.getErrorsInCategoryType(TechnicalErrorCategory.class).size());
        assertEquals(TechnicalErrorCategory.NULL_PARAMETER,
                listTest.getErrorsInCategoryType(TechnicalErrorCategory.class).get(0).getKey());
        assertEquals(new Pole(3, 1.0f),
                listTest.getErrorsInCategoryType(TechnicalErrorCategory.class).get(0).getValue().get(0));
        assertEquals(new Pole(2, 0.5f),
                listTest.getErrorsInCategoryType(TechnicalErrorCategory.class).get(0).getValue().get(1));
    }

    @Test
    public void testGetErrorByVerifiable() throws VerificationException {
        VerifiableError listTest = getBasicList();
        assertEquals(1, listTest.getErrorByVerifiable(p).size());
        assertEquals(UserErrorCategory.INCORRECT_AMPLITUDE, listTest.getErrorByVerifiable(p).get(0).getKey());
        assertEquals(2, listTest.getErrorByVerifiable(p).get(0).getValue().size());
        assertEquals(p, listTest.getErrorByVerifiable(p).get(0).getValue().get(0));

        assertEquals(2, listTest.getErrorByVerifiable(p2).size());
        assertEquals(UserErrorCategory.INCORRECT_AMPLITUDE, listTest.getErrorByVerifiable(p2).get(0).getKey());
        assertEquals(TechnicalErrorCategory.NULL_PARAMETER, listTest.getErrorByVerifiable(p2).get(1).getKey());
        assertEquals(2, listTest.getErrorByVerifiable(p2).get(0).getValue().size());
        assertEquals(p, listTest.getErrorByVerifiable(p2).get(0).getValue().get(0));
        assertEquals(p2, listTest.getErrorByVerifiable(p2).get(0).getValue().get(1));
        assertEquals(2, listTest.getErrorByVerifiable(p2).get(1).getValue().size());
        assertEquals(pp, listTest.getErrorByVerifiable(p2).get(1).getValue().get(0));
        assertEquals(p2, listTest.getErrorByVerifiable(p2).get(1).getValue().get(1));

        assertEquals(1, listTest.getErrorByVerifiable(pp).size());
        assertEquals(TechnicalErrorCategory.NULL_PARAMETER, listTest.getErrorByVerifiable(pp).get(0).getKey());
        assertEquals(2, listTest.getErrorByVerifiable(pp).get(0).getValue().size());
        assertEquals(pp, listTest.getErrorByVerifiable(pp).get(0).getValue().get(0));

    }

    @Test
    public void testGetErrorCategoriesByVerifiable() throws VerificationException {
        VerifiableError listTest = getBasicList();
        assertEquals(1, listTest.getErrorByVerifiable(p).size());
        assertEquals(UserErrorCategory.INCORRECT_AMPLITUDE, listTest.getErrorCategoriesByVerifiable(p).get(0));

        assertEquals(2, listTest.getErrorByVerifiable(p2).size());
        assertEquals(UserErrorCategory.INCORRECT_AMPLITUDE, listTest.getErrorCategoriesByVerifiable(p2).get(0));
        assertEquals(TechnicalErrorCategory.NULL_PARAMETER, listTest.getErrorCategoriesByVerifiable(p2).get(1));

        assertEquals(1, listTest.getErrorByVerifiable(pp).size());
        assertEquals(TechnicalErrorCategory.NULL_PARAMETER, listTest.getErrorCategoriesByVerifiable(pp).get(0));

    }

    public VerifiableError getBasicList() throws VerificationException {
        List<Pair<ErrorCategory, List<Verifiable>>> list = new ArrayList<>();

        // error 1
        // 2 * UserErrorCategory.INCORRECT_AMPLITUDE
        // Pole 1, ratio 1.0
        // Pole 2, ratio 1.5
        List<Verifiable> listVerif = new ArrayList<>();
        p = new Pole(1, 1.0f);
        p2 = new Pole(2, 0.5f);
        listVerif.add(p);
        listVerif.add(p2);
        Pair<ErrorCategory, List<Verifiable>> pair = new Pair<>(UserErrorCategory.INCORRECT_AMPLITUDE, listVerif);
        list.add(pair);

        // error 2
        // 2 * TechnicalErrorCategory.NULL_PARAMETER
        // Pole 3, ratio 1.0
        // Pole 2, ratio 1.5 (same as error 1 )
        List<Verifiable> listVerif2 = new ArrayList<>();
        pp = new Pole(3, 1.0f);
        listVerif2.add(pp);
        listVerif2.add(p2);
        Pair<ErrorCategory, List<Verifiable>> pair2 = new Pair<>(TechnicalErrorCategory.NULL_PARAMETER, listVerif2);
        list.add(pair2);

        return new VerifiableError(list);
    }*/

}
