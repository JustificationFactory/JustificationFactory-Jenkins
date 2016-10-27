package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.base.AContiniousDate;
import fr.axonic.base.AContinuousNumber;
import fr.axonic.validation.exception.VerificationException;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 25/07/16.
 */
public class TestBoundedParameter extends ApplicationTest {
    static {
        UtilForTests.disableGraphics();
    }

    private AContinuousNumber number;
    private AContiniousDate date;
    private BoundedParameter boundedParameterN;
    private BoundedParameter boundedParameterD;

    @Override
    public void start(Stage stage) throws Exception {
        number = new AContinuousNumber();
        date = new AContiniousDate();
        number.setValue(42.69);
        number.setLabel("Number's Label");
        
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        calendar.set(2016,Calendar.DECEMBER,31,23,59,42);
        calendar.clear(Calendar.MILLISECOND); // Remove milliseconds from date
        date.setValue(calendar);
        date.setLabel("Date's Label");
        
        boundedParameterN = new BoundedParameter(2, number);
        boundedParameterD = new BoundedParameter(2, date);

        assertEquals(1, boundedParameterN.getNodes().size());
        assertEquals(5, boundedParameterN.getNodeLine().size());
        assertEquals(1, boundedParameterN.getNodes().size());
        assertEquals(5, boundedParameterN.getNodeLine().size());

        HBox boxNumber = new HBox();
        boxNumber.getChildren().addAll(boundedParameterN.getNodeLine());
        
        HBox boxDate = new HBox();
        boxDate.getChildren().addAll(boundedParameterD.getNodeLine());

        VBox box = new VBox();
        box.getChildren().addAll(boxDate, boxNumber);
        
        Scene scene = new Scene(box, 800, 200);
        stage.setScene(scene);
        stage.show();
    }
    
    @Test
    public void testCheckbox() throws VerificationException {
        assertTrue(boundedParameterN.markedUtil.isSelected());
        clickOn(boundedParameterN.markedUtil);
        assertFalse(boundedParameterN.markedUtil.isSelected());

        assertTrue(boundedParameterD.markedUtil.isSelected());
        clickOn(boundedParameterD.markedUtil);
        assertFalse(boundedParameterD.markedUtil.isSelected());
        clickOn(boundedParameterD.markedUtil);
        assertTrue(boundedParameterD.markedUtil.isSelected());
        
        clickOn(boundedParameterN.markedUtil);
        assertTrue(boundedParameterN.markedUtil.isSelected());
    }


    @Test
    public void testChangingValueNumber() throws VerificationException {
        clickOn(boundedParameterN.minEquivRange);

        this.type(KeyCode.RIGHT, 10); // going to the end of the number
        eraseText(5)// erasing number
        .write("31.58"); // Typing a little number

        assertEquals(number.getValue(), 42.69); // unchanged
        //assertEquals(number.getMax(), 42.69); // unchanged
        //assertEquals(number.getMin(), 31.58); // edited

        clickOn(boundedParameterN.maxEquivRange);

        this.type(KeyCode.RIGHT, 10); // going to the end of the number
        eraseText(5)// erasing number
                .write("31.58"); // Typing a little number

        assertEquals(number.getValue(), 42.69); // unchanged
        //assertEquals(number.getMax(), 42.69); // unchanged
        //assertEquals(number.getMin(), 31.58); // unchanged

        this.type(KeyCode.RIGHT, 10); // going to the end of the number
        eraseText(5)// erasing number
                .write("53.80"); // Typing a big number

        assertEquals(number.getValue(), 42.69); // unchanged
        //assertEquals(number.getMax(), 53.8); // unchanged
        //assertEquals(number.getMin(), 31.58); // unchanged
    }


    @Test
    public void testChangingValueDate() throws VerificationException {
        clickOn(boundedParameterD.minEquivRange);

        this.type(KeyCode.RIGHT, 20); // going to the end of the text
        eraseText(20)// erasing date
                .write("30/11/2015 22:58:41"); // Typing an ancient date

        GregorianCalendar defaultDate = (GregorianCalendar) Calendar.getInstance();
        defaultDate.set(2016,Calendar.DECEMBER,31,23,59,42);
        defaultDate.clear(Calendar.MILLISECOND); // Remove milliseconds from date
        GregorianCalendar ancientDate = (GregorianCalendar) Calendar.getInstance();
        ancientDate.set(2015,Calendar.NOVEMBER,30,22,58,41);
        ancientDate.clear(Calendar.MILLISECOND); // Remove milliseconds from date
        GregorianCalendar futureDate = (GregorianCalendar) Calendar.getInstance();
        futureDate.set(2018,Calendar.FEBRUARY,2,1,0,43);
        futureDate.clear(Calendar.MILLISECOND); // Remove milliseconds from date

        assertEquals(date.getValue().getTimeInMillis(), defaultDate.getTimeInMillis()); // unchanged
        //assertEquals(date.getMax().getTimeInMillis(), defaultDate.getTimeInMillis()); // unchanged
        //assertEquals(date.getMin().getTimeInMillis(), ancientDate.getTimeInMillis()); // edited

        clickOn(boundedParameterD.maxEquivRange);

        this.type(KeyCode.RIGHT, 20); // going to the end of the text
        eraseText(20)// erasing date
                .write("30/11/2015 22:58:41"); // Typing an ancient date

        assertEquals(date.getValue().getTimeInMillis(), defaultDate.getTimeInMillis()); // unchanged
        //assertEquals(date.getMax().getTimeInMillis(), defaultDate.getTimeInMillis()); // unchanged
        //assertEquals(date.getMin().getTimeInMillis(), ancientDate.getTimeInMillis()); // unchanged

        this.type(KeyCode.RIGHT, 20); // going to the end of the text
        eraseText(20)// erasing date
                .write("02/02/2018 01:00:43"); // Typing a future date

        assertEquals(date.getValue().getTimeInMillis(), defaultDate.getTimeInMillis()); // unchanged
        //assertEquals(date.getMax().getTimeInMillis(), futureDate.getTimeInMillis()); // unchanged
        //assertEquals(date.getMin().getTimeInMillis(), ancientDate.getTimeInMillis()); // unchanged
    }
}
