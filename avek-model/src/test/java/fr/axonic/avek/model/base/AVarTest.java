package fr.axonic.avek.model.base;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 07/07/16.
 */
public class AVarTest {

    @Test
    public void testCreateAVar(){

        if(!(AVar.create(new Format(FormatType.NUMBER)) instanceof ANumber)){
            fail();
        }
        if(!(AVar.create(new Format(FormatType.BOOLEAN)) instanceof ABoolean)){
            fail();
        }
        if(!(AVar.create(new Format(FormatType.STRING)) instanceof AString)){
            fail();
        }
        if(!(AVar.create(new Format(FormatType.DATE)) instanceof ADate)){
            fail();
        }
    }
}
