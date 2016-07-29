package fr.axonic.observation;

import fr.axonic.base.ANumber;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import fr.axonic.observation.event.ACollectionChangedEvent;
import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.observation.event.ChangedEventType;
import fr.axonic.validation.exception.VerificationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cboinaud on 18/07/2016.
 */
public class EffectiveChangesEventsListeningTest {

    private TestStructure               testStructure;
    private static List<AEntityChanged> events;
    private AEntityListener defaultListener = new AEntityListener() {
        @Override
        public void changed(AEntityChanged event) {
            EffectiveChangesEventsListeningTest.events.add(event);
        }

        @Override
        public boolean listenOnlyEffectiveChanges() {
            return true;
        }

        @Override
        public boolean acceptChangedType(ChangedEventType changedEventType) {
            return true;
        }
    };

    @Before
    public void cleanUp() {
        events = new ArrayList<>();
        testStructure = new TestStructure();
    }

    @After
    public void tearDown() {
        events = null;
        testStructure.removeListeners();
        testStructure = null;
    }

    @Test
    public void aVarEffectiveChangeTest() throws VerificationException {

        this.testStructure.myNumber.addListener(defaultListener);
        assertTrue(events.isEmpty());

        this.testStructure.myNumber.setValue(10);
        assertTrue(events.isEmpty());

        this.testStructure.myNumber.setValue(20);
        assertEquals(events.size(), 1);
    }

    @Test
    public void aListEffectiveChangeTest(){

        List<ANumber> numbers = new ArrayList<>();

        numbers.add(new ANumber(1));
        numbers.add(new ANumber(2));
        numbers.add(new ANumber(3));
        numbers.add(new ANumber(4));
        numbers.add(new ANumber(5));

        testStructure.myList.addAll(numbers);

        this.testStructure.myList.addListener(defaultListener);
        assertTrue(events.isEmpty());

        // Change all elements in list
        testStructure.myList.replaceAll(aNumber -> new ANumber(10));
        assertEquals(events.size(), 1);

        events.forEach(group -> group.getEvents().forEach(event -> {
            assertEquals(event.getChangedType(), ChangedEventType.CHANGED);
            assertEquals(((ACollectionChangedEvent) event).getChangedValue(), new ANumber(10));
        }));
    }

    @Test
    public void aStructureEffectiveChangeTest() throws VerificationException {

        testStructure.addListener(defaultListener);
        assertTrue(events.isEmpty());

        testStructure.myNumber.setValue(10);
        assertTrue(events.isEmpty());

        testStructure.myNumber.setValue(30);
        assertEquals(events.size(), 1);
    }

    private class TestStructure extends AStructure {

        public final AList<ANumber> myList;
        public final ANumber myNumber;

        public TestStructure() {
            myList = new AList<>();
            myNumber = new ANumber(10);
            init();
        }
    }

}
