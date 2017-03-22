/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.learn.kforce2;

import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ADP
 */
public class RangeTest {
    
    public RangeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testGetMin() {
        System.out.println("getMin");
        Range instance = new Range(new Integer(10),new Integer(20));
        Integer expResult = 10;
        Integer result = (Integer) instance.getMin();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMax() {
        System.out.println("getMax");
        Range instance = new Range(new Integer(10),new Integer(20));
        Integer expResult = 20;
        Integer result = (Integer) instance.getMax();
        assertEquals(expResult, result);
    }

    @Test
    public void testInSideRange() {
        System.out.println("inSideRange");
        Range r1 = new Range(new Integer(10),new Integer(20));
        Range r2 = new Range(new Integer(12),new Integer(15));
        boolean expResult = false;
        boolean result = r2.inSideRange(r1);
        assertEquals(expResult, result);
        expResult = true;
        result = r1.inSideRange(r2);
        assertEquals(expResult, result);
    }

    @Test
    public void testCollapsableRanges() {
        System.out.println("collapsableRanges");
        Range r1 = new Range(new Integer(10),new Integer(20));
        Range r2 = new Range(new Integer(12),new Integer(23));
        boolean expResult = true;
        boolean result = r2.collapsableRanges(r1);
        assertEquals(expResult, result);
        assertEquals(r1.collapsableRanges(r2), r2.collapsableRanges(r1));
        assertEquals(expResult, r2.collapsableRanges(new Range(Integer.valueOf(23),Integer.valueOf(23))));

    }

    @Test
    public void testCollapseRange() {
        System.out.println("collapseRange");
        Range r1 = new Range(new Integer(10),new Integer(20));
        Range r2 = new Range(new Integer(12),new Integer(23));        
        Range expResult = new Range(new Integer(10), new Integer(23));
        Range result = r2.collapseRange(r1);
        assertEquals(expResult, result);
        assertEquals(r1.collapseRange(r2), r2.collapseRange(r1));
        Range r3 = new Range(new Integer(29),new Integer(29));
        assertEquals(null, r3.collapseRange(result));

    }
    
}
