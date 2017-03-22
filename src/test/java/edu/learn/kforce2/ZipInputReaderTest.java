/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.learn.kforce2;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author ADP
 */
public class ZipInputReaderTest {

    
    @Test
    public void ReadFromFile() throws IOException{
        System.err.println("ReadFromFile");
        ZipRangeList expectedResult = new ZipRangeList();
        expectedResult.addRange(new Range<>(new Integer(0), new Integer(99999)));
        ZipInputReader zir = new ZipInputReader();
        System.err.println("Return from ReadFromFile:" + this.getClass().getClassLoader().getResource("dataset_001.json").getPath());
        try{
            ZipRangeList result = zir.readFromFile(this.getClass().getClassLoader().getResource("dataset_001.json").getPath());
            assertEquals(expectedResult.zipcodeRanges,result.zipcodeRanges);
        } catch (Exception ex ){
            Logger.getLogger(ZipInputReaderTest.class.getName()).log(Level.SEVERE, ex.toString() );
        }
        
    }
    
    @Test
    public void ReadFromCommandLine(){
        System.err.println("ReadFromCmdLine");
        ZipRangeList expectedResult = new ZipRangeList();
        String[] args = new String[5];
        args[0] = "[99,200]";
        args[1] = "[150,250]";
        args[2] = "[300,400]";
        args[3] = "[450,499]";
        args[4] = "[42,45]";
        expectedResult.addRange(new Range<>(new Integer(99), new Integer(200)));
        expectedResult.addRange(new Range<>(new Integer(150), new Integer(250)));
        expectedResult.addRange(new Range<>(new Integer(300), new Integer(400)));
        expectedResult.addRange(new Range<>(new Integer(450), new Integer(499)));
        expectedResult.addRange(new Range<>(new Integer(42), new Integer(45)));
        ZipInputReader zir = new ZipInputReader();
        try{
            ZipRangeList result = zir.readFromCmdLine(args);
            assertEquals(expectedResult.zipcodeRanges,result.zipcodeRanges);    
        } catch (Exception ex ){
            Logger.getLogger(ZipInputReaderTest.class.getName()).log(Level.SEVERE, ex.toString() );
        }
        
    }
    
    //-c [1,1] [1,1] [1,2] [3,4] [4,6] [5,10] [11,11] [11,12] [14,15] [15,16]
    @Test
    public void ReadFromCommandLine2(){
        System.err.println("ReadFromCmdLine2");
        ZipRangeList expectedResult = new ZipRangeList();
        String[] args = new String[10];
        args[0] = "[1,1]";
        args[1] = "[1,1]";
        args[2] = "[1,2]";
        args[3] = "[3,4]";
        args[4] = "[4,6]";
        args[5] = "[5,10]";
        args[6] = "[11,11]";
        args[7] = "[11,12]";
        args[8] = "[14,15]";
        args[9] = "[15,16]";
        expectedResult.addRange(new Range<>(new Integer(1), new Integer(12)));
        expectedResult.addRange(new Range<>(new Integer(14), new Integer(16)));
        ZipInputReader zir = new ZipInputReader();
        try{
            ZipRangeList result = zir.readFromCmdLine(args);
            assertEquals(expectedResult.zipcodeRanges,result.zipcodeRanges);    
        } catch (Exception ex ){
            Logger.getLogger(ZipInputReaderTest.class.getName()).log(Level.SEVERE, ex.toString() );
        }
    }
    
    
    @Test
    public void ReadFromUser(){
        System.err.println("ReadFromUser");
        ZipRangeList expectedResult = new ZipRangeList();
        expectedResult.addRange(new Range<>(new Integer(0), new Integer(99999)));
        ZipInputReader zir = new ZipInputReader();
        StringBuilder sb  = new StringBuilder();
        sb.append("1").append("\n");
        sb.append("0").append("\n");
        sb.append("99999").append("\n");
        InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
        try{
            ZipRangeList result = zir.readFromUser(is);
            assertEquals(expectedResult.zipcodeRanges,result.zipcodeRanges);    
        } catch (Exception ex ){
            Logger.getLogger(ZipInputReaderTest.class.getName()).log(Level.SEVERE, ex.toString() );
        }
    
    }
    
    @Test
    public void ReadFromFile2() throws IOException{
        System.err.println("ReadFromFile2");
        ZipRangeList expectedResult = new ZipRangeList();
        expectedResult.addRange(new Range<>(new Integer(99), new Integer(200)));
        expectedResult.addRange(new Range<>(new Integer(150), new Integer(250)));
        expectedResult.addRange(new Range<>(new Integer(300), new Integer(400)));
        expectedResult.addRange(new Range<>(new Integer(450), new Integer(499)));
        expectedResult.addRange(new Range<>(new Integer(42), new Integer(45)));
        ZipInputReader zir = new ZipInputReader();
        System.err.println("Return from ReadFromFile2:" + this.getClass().getClassLoader().getResource("dataset_002.json").getPath());
        try{
            ZipRangeList result = zir.readFromFile(this.getClass().getClassLoader().getResource("dataset_002.json").getPath());
            assertEquals(expectedResult.zipcodeRanges,result.zipcodeRanges);
        } catch (Exception ex ){
            Logger.getLogger(ZipInputReaderTest.class.getName()).log(Level.SEVERE, ex.toString() );
        }
        
    }
}
