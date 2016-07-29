package fr.axonic.observation;


import fr.axonic.base.ABoolean;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AStructure;
import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.observation.event.AEntityChangedEvent;
import fr.axonic.observation.event.ChangedEventType;
import fr.axonic.validation.exception.VerificationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cboinaud on 13/07/2016.
 */
public class AStructureEventsListeningTest {

    private        TestStructure       testStructure;
    private static AEntityChangedEvent event;

    private AEntityListener defaultListener = new AEntityListener() {

        @Override
        public void changed(AEntityChanged events) {
            event = (AEntityChangedEvent) events.getEvents().get(0);
        }

        @Override
        public boolean listenOnlyEffectiveChanges() {
            return false;
        }

        @Override
        public boolean acceptChangedType(ChangedEventType changedEventType) {
            return true;
        }
    };

    @Before
    public void cleanUp(){
        event = null;
        testStructure = new TestStructure();
    }

    @After
    public void tearDown(){
        event = null;
        testStructure.removeListeners();
        testStructure = null;
    }

    @Test
    public void aVarChangedEventTest() throws VerificationException {

        testStructure.addListener(defaultListener);

        assertNull(event);
        testStructure.myNumber.setValue(20);
        assertNotNull(event);

        assertEquals(event.getOldValue(), 10);
        assertEquals(event.getNewValue(), 20);
        assertEquals(event.getSource(), testStructure.myNumber);
        assertEquals(event.getChangedType(), ChangedEventType.CHANGED);
        assertEquals(event.getPropertyName(), "VALUE");
    }

    @Test
    public void aVarRecursiveChangedEventTest() throws VerificationException {

        testStructure.addListener(defaultListener);

        assertNull(event);
        testStructure.myStructure.myNumber.setValue(20);
        assertNotNull(event);

        assertEquals(event.getOldValue(), 5);
        assertEquals(event.getNewValue(), 20);
        assertEquals(event.getSource(), testStructure.myStructure.myNumber);
        assertEquals(event.getChangedType(), ChangedEventType.CHANGED);
        assertEquals(event.getPropertyName(), "VALUE");
    }

    private class TestStructure extends AStructure {

        public ANumber myNumber;
        public AString myString;
        public TestSubStructure myStructure;
        public ABoolean myBoolean;

        public TestStructure() {
            myNumber = new ANumber(10);
            myString = new AString("hello");
            myStructure = new TestSubStructure();
            myBoolean = new ABoolean(false);
            init();
        }
    }

    private class TestSubStructure extends AStructure {

        public ANumber myNumber;
        public AString myString;

        public TestSubStructure(){
            myNumber = new ANumber(5);
            myString = new AString("world");
            init();
        }
    }

}
