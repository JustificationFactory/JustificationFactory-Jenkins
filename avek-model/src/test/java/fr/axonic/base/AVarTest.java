package fr.axonic.base;


import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.format.*;
import fr.axonic.base.engine.FormatType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 07/07/16.
 */
public class AVarTest {

    @Test
    public void testCreateAVar(){

        if(!(AVar.create(new NumberFormat()) instanceof ANumber)){
            fail();
        }
        if(!(AVar.create(new BooleanFormat()) instanceof ABoolean)){
            fail();
        }
        if(!(AVar.create(new StringFormat()) instanceof AString)){
            fail();
        }
        if(!(AVar.create(new DateFormat()) instanceof ADate)){
            fail();
        }
        if(!(AVar.create(new EnumFormat()) instanceof AEnum)){
            fail();
        }
        if(!(AVar.create(new RangedEnumFormat()) instanceof ARangedEnum)){
            fail();
        }
        if(!(AVar.create(new BoundedNumberFormat()) instanceof AContinuousNumber)){
            fail();
        }
        if(!(AVar.create(new BoundedDateFormat()) instanceof AContiniousDate)){
            fail();
        }
        if(!(AVar.create(new RangedStringFormat()) instanceof ARangedString)){
            fail();
        }



        if(!(AVar.create(new BoundedDateFormat()) instanceof ADate)){
            fail();
        }
        if(!(AVar.create(new BoundedNumberFormat()) instanceof ANumber)){
            fail();
        }
        if(!(AVar.create(new RangedEnumFormat()) instanceof AEnum)){
            fail();
        }
        if(!(AVar.create(new RangedStringFormat()) instanceof AString)){
            fail();
        }
    }

}
