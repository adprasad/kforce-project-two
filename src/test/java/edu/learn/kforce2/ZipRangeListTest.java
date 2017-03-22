/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.learn.kforce2;

import java.util.logging.Level;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ADP
 */
public class ZipRangeListTest {
    
    public ZipRangeListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testAddRange() {
        System.out.println("addRange");
        Range<Integer> nZip = new Range<>(new Integer(10), new Integer(10));
        ZipRangeList instance = new ZipRangeList();
        instance.addRange(nZip);
        Assert.assertEquals(nZip, (instance.getZipcodeRanges()).get(0));
    }

    @Test
    public void testPrintRanges() {
        System.out.println("printRanges");
        Level logLevel = Level.INFO;
        ZipRangeList instance = new ZipRangeList();
        instance.printRanges(logLevel);
//        fail("The test case is a prototype.");
    }
    /*
EXAMPLES:
If the input = [94133,94133] [94200,94299] [94600,94699]
Then the output should be = [94133,94133] [94200,94299] [94600,94699]
    */
    @Test
    public void testAddRangeMulti(){
        System.out.println("testAddRangeMulti");
        Level logLevel = Level.INFO;
        Range<Integer> nZip = new Range<>(new Integer(94133), new Integer(94133));
        ZipRangeList instance = new ZipRangeList();
        assertEquals(instance.getZipcodeRanges().size(), 0);
        instance.addRange(nZip);
        instance.printRanges(logLevel);
        assertEquals(instance.getZipcodeRanges().size(), 1);
        instance.addRange(nZip);
        instance.printRanges(logLevel);
        assertEquals(instance.getZipcodeRanges().size(), 1);
        instance.addRange(new Range<>(new Integer(94200),new Integer(94299)));
        instance.printRanges(logLevel);
        assertEquals(instance.getZipcodeRanges().size(), 2);
        instance.addRange(new Range<>(new Integer(94600),new Integer(94699)));
        instance.printRanges(logLevel);
        assertEquals(instance.getZipcodeRanges().size(), 3);
    }
    
    /*
     * If the input = [94133,94133] [94200,94299] [94226,94399] 
Then the output should be = [94133,94133] [94200,94399]
     */
    @Test
    public void testAddRangeMulti2(){
        System.out.println("testAddRangeMulti2");
        Level logLevel = Level.INFO;
        Range<Integer> nZip = new Range<>(new Integer(94133), new Integer(94133));
        ZipRangeList instance = new ZipRangeList();
        assertEquals(instance.getZipcodeRanges().size(), 0);
        instance.addRange(nZip);
        instance.printRanges(logLevel);
        assertEquals(instance.getZipcodeRanges().size(), 1);
        instance.addRange(nZip);
        instance.printRanges(logLevel);
        assertEquals(instance.getZipcodeRanges().size(), 1);
        instance.addRange(new Range<>(new Integer(94200),new Integer(94299)));
        instance.printRanges(logLevel);
        assertEquals(instance.getZipcodeRanges().size(), 2);
        instance.addRange(new Range<>(new Integer(94226),new Integer(94399)));
        instance.printRanges(logLevel);
        assertEquals(instance.getZipcodeRanges().size(), 2);
    }
    
}
