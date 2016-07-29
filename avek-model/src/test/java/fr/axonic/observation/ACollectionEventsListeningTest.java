package fr.axonic.observation;


import fr.axonic.base.ANumber;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import fr.axonic.observation.event.*;
import fr.axonic.validation.exception.VerificationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cboinaud on 15/07/2016.
 */
public class ACollectionEventsListeningTest {

    private        TestStructure        testStructure;
    private static List<AEntityChanged> events;

    private AEntityListener defaultListener = new AEntityListener() {

        @Override public void changed(AEntityChanged event) {
            ACollectionEventsListeningTest.events.add(event);
        }

        @Override public boolean listenOnlyEffectiveChanges() {
            return false;
        }

        @Override public boolean acceptChangedType(ChangedEventType changedEventType) {
            return true;
        }
    };

    @Before
    public void cleanUp(){
        events = new ArrayList<>();
        testStructure = new TestStructure();
    }

    @After
    public void tearDown(){
        events = null;
        testStructure.removeListeners();
        testStructure = null;
    }

    @Test
    public void aListEventAddedEntityTest() {

        testStructure.myList.addListener(defaultListener);

        ANumber number = new ANumber(20);
        assertTrue(events.isEmpty());

        testStructure.myList.add(number);

        assertEquals(events.size(), 1);

        int count = 0;
        for(AEntityEvent event : events.get(0).getEvents()) {
            count++;
            assertTrue(event instanceof ACollectionChangedEvent);
            assertEquals(event.getChangedType(), ChangedEventType.ADDED);
            assertEquals(event.getSource(), testStructure.myList);
            assertEquals(((ACollectionChangedEvent) event).getChangedValue(), new ANumber(20));
        }

        assertEquals(count, 1);
    }

    @Test
    public void aListEventRemovedEntityTest(){

        testStructure.myList.addListener(defaultListener);

        ANumber number = new ANumber(20);
        assertTrue(events.isEmpty());

        testStructure.myList.add(number);
        testStructure.myList.remove(number);

        assertEquals(events.size(), 2);

        int count = 0;
        for(AEntityEvent event : events.get(1).getEvents()) {
            count++;
            assertTrue(event instanceof ACollectionChangedEvent);

            assertEquals(event.getChangedType(), ChangedEventType.REMOVED);
            assertEquals(event.getSource(), testStructure.myList);
            assertEquals(((ACollectionChangedEvent) event).getChangedValue(), new ANumber(20));
        }

        assertEquals(count, 1);
    }

    @Test
    public void aListEventPermutedEntityTest(){
        testStructure.myList.addListener(defaultListener);

        List numbers = new ArrayList<>();

        numbers.add(new ANumber(1));
        numbers.add(new ANumber(2));
        numbers.add(new ANumber(3));
        numbers.add(new ANumber(4));
        numbers.add(new ANumber(5));

        assertTrue(events.isEmpty());

        testStructure.myList.addAll(numbers);

        assertEquals(events.size(), 1);
        assertEquals(events.get(0).getEvents().size(), 5);

        testStructure.myList.sort((o1, o2) -> o1.getValue().intValue() - o2.getValue().intValue());

        assertEquals(events.size(), 2);
        assertEquals(events.get(1).getEvents().size(), 5);

        int count = 0;
        for(AEntityEvent event : events.get(1).getEvents()) {
            assertTrue(event instanceof ACollectionChangedEvent);

            assertEquals(event.getChangedType(), ChangedEventType.PERMUTED);
            assertEquals(event.getSource(), testStructure.myList);
            assertEquals(((ACollectionChangedEvent) event).getChangedValue(), new ANumber(++count));
        }
    }

    @Test
    public void aListEventChangedEntityTest1(){

    }

    @Test
    public void aListEventChangedEntityTest2() throws VerificationException {

        testStructure.myList.addListener(defaultListener);

        ANumber number = new ANumber(20);
        assertTrue(events.isEmpty());

        testStructure.myList.add(number);
        testStructure.myList.get(0).setValue(10);

        assertEquals(events.size(), 2);

        int count = 0;
        for (AEntityEvent event : events.get(1).getEvents()) {
            count++;
            assertTrue(event instanceof AEntityChangedEvent);
        }

        assertEquals(count, 1);
    }

    @Test
    public void aListInStructureEventTest() throws VerificationException {

        testStructure.addListener(defaultListener);

        ANumber number = new ANumber(20);
        assertTrue(events.isEmpty());

        testStructure.myList.add(number);

        assertEquals(events.size(), 1);

        int count = 0;
        for (AEntityEvent event : events.get(0).getEvents()) {
            count++;
            assertTrue(event instanceof ACollectionChangedEvent);
        }

        assertEquals(count, 1);
    }

    private class TestStructure extends AStructure {

        public AList<ANumber> myList;

        public TestStructure() {
            myList = new AList<>();
            init();
        }
    }

}
