package fr.axonic.avek.gui.model.base;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.TimeUnit;

public final class AVarHelper {


    private enum EqualityTestType {
        VALUE,
        OBJECT
    }

    private static boolean equal(Object o1, Object o2, EqualityTestType equalityTestType) {
        boolean result = false;
        switch (equalityTestType) {
            case OBJECT: {
                result = (o1 == o2);
            }
                break;
            case VALUE: {
                result = (o1 == o2);
                if (!result) {
                    if (o1 != null) {
                        result = o1.equals(o2);
                    }
                }
            }
                break;
            default: {
                throw new IllegalArgumentException();
            }
        }
        return result;
    }

    private static <T extends AVar> boolean wait(T var, Object value, long timeout, TimeUnit timeUnit,
            EqualityTestType equalityTestType) throws InterruptedException {

        boolean result = false;

        if (var == null) {
            throw new IllegalArgumentException();
        }

        // Test 1
        if (!equal(var.getValue(), value, equalityTestType)) {

            Object lock = new Object();

            PropertyChangeListener listener = new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    synchronized (lock) {

                        // Test 2
                        if (equal(var.getValue(), value, equalityTestType)) {
                            synchronized (lock) {
                                lock.notifyAll();
                            }
                        }
                    }
                }
            };

            try {

                var.addPropertyChangeListener(AVarProperty.VALUE.name(), listener);

                synchronized (lock) {

                    // Test 3
                    if (!equal(var.getValue(), value, equalityTestType)) {
                        lock.wait(TimeUnit.MILLISECONDS.convert(timeout, timeUnit));
                    }
                }
            } finally {
                var.removePropertyChangeListener(listener);
            }

            // Test 4
            result = equal(var.getValue(), value, equalityTestType);

        } else {
            result = true;
        }

        return result;
    }

    /**
     * 
     * @param var
     * @param value
     * @param timeout
     * @param timeUnit
     * @return
     * @throws InterruptedException
     */
    public static <T extends AVar> boolean waitEqualValue(T var, Object value, long timeout, TimeUnit timeUnit)
            throws InterruptedException {
        return wait(var, value, timeout, timeUnit, EqualityTestType.VALUE);
    }

    /**
     * 
     * @param var
     * @param value
     * @return
     * @throws InterruptedException
     */
    public static <T extends AVar> boolean waitEqualValue(T var, Object value) throws InterruptedException {
        return waitEqualValue(var, value, 0, TimeUnit.MILLISECONDS);
    }

}
