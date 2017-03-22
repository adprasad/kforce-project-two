/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.learn.kforce2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runner class of ZipCodeParser program
 * @author ADP
 */
public class ZipCodeParser {
    // Read input from XXXX & store in ZZZZ
    // Determine if ZZZZ is optimized
    // return optimized ZZZZ
    
    /**
     * Main runner of ZipCodeParser program uses String arguments to determine 
     * what input type is being provided and how to parse that data. Options on 
     * how to run the program can be found by running the application with '-h' 
     * or '--help' switch. Final version of processed input will be returned
     * on a single line to standard out.
     * @param args Standard commandline string argument array.
     * @throws IOException If issue occurs when reading from a file
     */
    public static void main(String[] args) throws IOException, Exception{
        ZipInputReader zir = new ZipInputReader();
        ZipRangeList zrl = new ZipRangeList();

        try{
        // Read from User input
        if( args.length == 0 ){
            //System.err.println("No input provided, request input from user");
            zrl = zir.readFromUser(System.in);
        }
        // REad from file
        else if( args.length == 2 && args[0].toLowerCase().equals( "-f") ){
            Logger.getLogger(ZipCodeParser.class.getName()).log(Level.FINE,"Running read file");
            zrl = zir.readFromFile(args[1]);
        }
        // Read from command line
        else if ( args.length >= 2 && args[0].toLowerCase().equals("-c") ){
            List<String> args2 = new ArrayList<>();
            for(String arg : args){
                if( arg.contains("-c") == false){
                    args2.add(arg);
                }
            }
            args2.toArray(args);
            zrl = zir.readFromCmdLine(args);
        }
        // Print usage statement
        else if ( args.length > 0 && args[0].toLowerCase().equals("-h" ) || args[0].toLowerCase().equals( "--help" ) ){
            zir.printUsageStatement();
        }
        // Finally print mimized ranges if any are available
        if( zrl.getZipcodeRanges().isEmpty() == false )
            zrl.printRanges();
        else
            System.out.println("No valid zipcodes found");
        } catch( Exception ex ){
            Logger.getLogger(ZipCodeParser.class.getName()).log(Level.SEVERE, ex.toString());
        }
    }
    
    
}
