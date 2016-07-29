package fr.axonic.base;

import fr.axonic.base.engine.AVarHelper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class AVarHelperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AVarHelperTest.class);

    @Test
    public void testWaitEqualValue() {

        for (int i = 0; i < 10; i++) {
            ABoolean var = new ABoolean(false);
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(50);
                        var.setValue(true);
                    } catch (Exception e) {
                        LOGGER.error(e.toString(),e);
                    }
                }
            };

            new Thread(runnable).start();

            try {
                Assert.assertTrue(AVarHelper.waitEqualValue(var, true, 500, TimeUnit.MILLISECONDS));
            } catch (InterruptedException e) {
                LOGGER.error(e.toString(),e);
            }
        }
    }

    @Test
    public void testWaitEqualValueNeg() {

        ABoolean var = new ABoolean(false);
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    var.setValue(true);
                } catch (Exception e) {
                    LOGGER.error(e.toString(),e);
                }
            }
        };

        new Thread(runnable).start();

        try {
            Assert.assertFalse(AVarHelper.waitEqualValue(var, true, 50, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            LOGGER.error(e.toString(),e);
        }

    }

}
