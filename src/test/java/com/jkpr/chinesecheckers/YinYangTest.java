package com.jkpr.chinesecheckers;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class YinYangTest extends TestCase {
    public static Test suite()
    {
        return new TestSuite( YinYangTest.class );
    }
    public void testBasic(){
        assertTrue(true);
    }
}
