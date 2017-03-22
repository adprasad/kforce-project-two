/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.learn.kforce2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data structure for holding Integer ranges of five digit zip-codes.
 * @author ADP
 */
public class ZipRangeList {
    List<Range<Integer>> zipcodeRanges;
    
    public ZipRangeList(){
        zipcodeRanges = new ArrayList<>();
    }
    
    public List<Range<Integer>> getZipcodeRanges(){
        return this.zipcodeRanges;
    }
    
    /**
     * Add a new zipcode range such that it is sorted in ranges list. If the 
     * zipcode range can be collapsed into an existing range then that will 
     * happen instead of adding an new entry.
     * @param nZip new Range to be added 
     */
    public void addRange(Range<Integer> nZip){
        // First entry just add it in
        if( zipcodeRanges.isEmpty() ){
            zipcodeRanges.add(nZip);
        }
        // before adding check if it can be collapsed
        else {
            // Check if nZip can be collapsed into any current ranges
            // or if current ranges can be collaped into nzip
            boolean added = false;
            for(Range r : zipcodeRanges){
                if(r.collapsableRanges(nZip) || nZip.collapsableRanges(r) ){
                    Range<Integer> tmp = r.collapseRange(nZip);
                    int index = zipcodeRanges.indexOf(r);
                    zipcodeRanges.remove(index);
                    zipcodeRanges.add(tmp);
                    added = true;
                    break;
                }
            }
            // If therew as no where to collapse the range into
            if( added == false ){
                zipcodeRanges.add(nZip);
            }
            Collections.sort(zipcodeRanges);
        }
    }
    
    /**
     * Print current list of ranges to log
     * @param logLevel Logging level to output
     */
    public void printRanges(Level logLevel){
        StringBuilder sb = new StringBuilder();
        for(Range r : zipcodeRanges){
            if( sb.length() == 0 ){
                sb.append(r.toString());
            } else {
                sb.append(";").append(r.toString());
            }
        }
        Logger.getLogger(ZipRangeList.class.getName()).log(logLevel, sb.toString());
    }
    
    /**
     * Print current list of ranges to standard out
     */
    public void printRanges(){
        StringBuilder sb = new StringBuilder();
        for(Range r : zipcodeRanges){
            if( sb.length() == 0 ){
                sb.append("[" + r.getMin() +"," + r.getMax() +"]");
            } else {
                sb.append(" ").append("[" + r.getMin() +"," + r.getMax() +"]");
            }
        }
        System.out.println(sb.toString());
    }
    
}
