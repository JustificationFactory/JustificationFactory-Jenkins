package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by cduffau on 28/10/16.
 */
public class HumanStrategyTest {

    private class HumanStrategyTestUnit extends HumanStrategy{

        @Override
        public boolean check(Map<String, Evidence> evidenceRoles, Conclusion conclusion) {
            return true;
        }
    }
    private HumanStrategy humanStrategy;

    @Before
    public void setUp(){
        humanStrategy=new HumanStrategyTestUnit();
        humanStrategy.setMinimumRole(Role.INTERMEDIATE_EXPERT);
    }

    @Test
    public void testWithoutRole() throws StrategyException {
        humanStrategy.setMinimumRole(null);
        humanStrategy.setActor(new Actor("Chloé",Role.TECHNICIAN));
    }

    @Test
    public void testWithRightRole() throws StrategyException {
        humanStrategy.setActor(new Actor("Chloé",Role.INTERMEDIATE_EXPERT));
    }

    @Test(expected = StrategyException.class)
    public void testWithUpperRole() throws StrategyException {
        humanStrategy.setActor(new Actor("Chloé",Role.JUNIOR_EXPERT));
    }

    @Test
    public void testWithLowerRole() throws StrategyException {
        humanStrategy.setActor(new Actor("Chloé",Role.SENIOR_EXPERT));
    }
}
