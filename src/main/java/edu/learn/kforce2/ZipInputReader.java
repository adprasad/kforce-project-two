/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.learn.kforce2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.json.JSONObject;

/**
 * Provides several methods to parse input and returns input as a ZipRangeList object
 * @author ADP
 */
class ZipInputReader {

    /**
     * Reads input from user for specified number of cases
     * @param InputStream Source InputStream to get zipcodes from
     * @return ZipRangeList of parsed ranges
     * @throws Exception if error occurs while reading user input
     */
    ZipRangeList readFromUser(InputStream is) throws Exception {
        ZipRangeList zrl = new ZipRangeList();
        try{
            Scanner scan = new Scanner(is);
            int ranges = 0;
            System.out.printf("Enter number of ranges:");
            ranges = scan.nextInt();
            for(int i=0; i < ranges; i++)
            {
                System.out.printf( "[" + i + "] Enter lower limit:");
                int low = scan.nextInt();
                System.out.printf( "[" + i + "] Enter upper limit:");
                int high = scan.nextInt();
                if( low > high){
                    int tmp = low;
                    low = high;
                    high = tmp;
                }
                zrl.addRange(new Range<>(new Integer(low),new Integer(high)));
            }
        }
        catch( Exception ex){
            Logger.getLogger(ZipInputReader.class.getName()).log(Level.SEVERE, ex.toString());
            throw new Exception("Failed to parse user input");
        }
        return zrl;
    }

    /**
     * Reads input from user for specified number of cases
     * @param args String array of [min,max] combinations
     * @return ZipRangeList of parsed ranges
     * @throws Exception if error occurs during parsing of strings
     */
    ZipRangeList readFromCmdLine(String[] args) throws Exception {
        ZipRangeList zrl = new ZipRangeList();
        for(String s : args){
            if( s != null && s.matches("\\[[0-9]{1,5},[0-9]{1,5}\\]") ){
                try{
                    s = s.replaceAll("[\\[\\]]", "");
                    String[] split = s.split(",");
                    Integer min = Integer.parseInt(split[0]);
                    Integer max = Integer.parseInt(split[1]);
                    Range<Integer> tmp = new Range<>(min,max);
                    zrl.addRange(tmp);
                } catch( Exception ex){
                    Logger.getLogger(ZipInputReader.class.getName()).log(Level.SEVERE, ex.toString());
                    throw new Exception("Failed to parse command line argument");
                }
            }
        }
        return zrl;
    }

    /**
     * Reads input from file for json array
     * @param filename Json formated file to get a list of input from
     * @return ZipRangeList of parsed ranges
     * @throws Exception If file cannot be read
     */
    ZipRangeList readFromFile(String filename) throws IOException, Exception {
        ZipRangeList zrl = new ZipRangeList();
        
        try{
            File file = new File(filename);
            Logger.getLogger(ZipInputReader.class.getName()).log(Level.FINE, "File Path:" + file.getCanonicalPath());
            if( file.exists() ){
               ObjectMapper objMapper = new ObjectMapper();
               List<JsonNode> jsonRanges = objMapper.readValue(file, objMapper.getTypeFactory().constructCollectionType(
                           List.class, JsonNode.class));
               for(JsonNode jnode : jsonRanges){
                   //System.err.println(jnode);
                   Range<Integer> tmpRange = new Range<>((jnode.get("min")).getIntValue(), (jnode.get("max")).getIntValue());
                   zrl.addRange(tmpRange);
               }
            }else{
                Logger.getLogger(ZipInputReader.class.getName()).log(Level.SEVERE, "File not found or readable");
                Logger.getLogger(ZipInputReader.class.getName()).log(Level.SEVERE, file.toString());
            }
        }  catch (Exception ex ){
            Logger.getLogger(ZipInputReader.class.getName()).log(Level.SEVERE, ex.toString());
            throw new Exception("Exception occurred, failed to parse JSON file");
        }
        Logger.getLogger(ZipInputReader.class.getName()).log(Level.FINE, "Count: " + zrl.zipcodeRanges.size());
        return zrl;
    }
    
    /**
     * Outputs the usage statement for the application
     */
    public void printUsageStatement(){
        StringBuilder sb = new StringBuilder();
        sb.append("Usage: ");
        sb.append("Reduce provided set of ZipCodes into an optimized set. If no switch is provided it is assumed data will be provided by user at runtime.\n\n");
        sb.append("Valid Optional Switches:\n");
        sb.append("\t-f filename.json\n");
        sb.append("\t-h || --help\t Prints this usage statement\n");
        sb.append("\t-c '[99999,99999]'\n");
        sb.append("\n\nExamples:\n");
        sb.append("java.exe -jar zipcodeParser.jar -c [99009,99011] [99007,99007]\n");
        sb.append("java.exe -jar zipcodeParser.jar -f dataset_001.json\n");

        System.err.println(sb.toString());
    }
}
