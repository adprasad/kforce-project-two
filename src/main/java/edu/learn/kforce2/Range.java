/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.learn.kforce2;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * A set defined by minimum and maximum limit
 * @author ADP
 */
public class Range<T extends Comparable<T> > implements Comparable<Range<T>>{

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Range<?> other = (Range<?>) obj;
        if (!Objects.equals(this.min, other.min)) {
            return false;
        }
        if (!Objects.equals(this.max, other.max)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Range{" + "min=" + min + ", max=" + max + '}';
    }
    
    public Range(){
    }
    
    /**
     * Range constructor that defines the JSON properties for later definition
     * if ranges are parsed from a JSON file.
     * @param min Lower limit of range
     * @param max Upper limit of range
     */
    @JsonCreator
    public Range(@JsonProperty("min") T min,@JsonProperty("max")  T max){
        this.min = min;
        this.max = max;
    }
    
    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
    private T min;
    
    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
    private T max;

    /**
     * Returns lower limit of range
     * @return T min
     */
    public T getMin(){ return min; }
    /**
     * Returns upper limit of range
     * @return T max
     */
    public T getMax(){ return max; }
    
    
    /**
     * Check to see if range passed in is inside of this range
     * @param o Other Range to compare against
     * @return true or false
     */
    public boolean inSideRange(Range<T> o){
        boolean r = false;
        // If min <= o.min && 
        
        if( (min.compareTo(o.getMin()) == -1 || min.compareTo(o.getMin()) == 0 ) 
                && (max.compareTo(o.getMax()) == 1 || max.compareTo(o.getMax()) == 0  )){
            Logger.getLogger(Range.class.getName()).log(Level.FINE, "insideRange is true");
            r = true;
        }
        return r;
    }

    /**
     * Check to see if supplied range can be collapsed into this range
     * @param two Other Range to compare against
     * @return true or false
     */
    public boolean collapsableRanges(Range<T> two){
        boolean r = false;
        Logger.getLogger(Range.class.getName()).log(Level.FINE, "Comparing Ranges");
        // Collapsable if one is inside other or equal
        if( two.max.getClass() == Integer.class && two.min.getClass() == Integer.class){
            if( (Integer) two.min + 1 == (Integer) this.max || (Integer) two.max + 1 == (Integer) this.min  )
                r = true;
        }
        if( this.equals(two) || two.inSideRange(this) || this.inSideRange(two) ){
            r = true;
        }
        if( this.InRange(two, max) || this.InRange(two, min) ){
            r = true;
        }
        // check distance from lower max to higher min if <= 0 then collaspable
        return r;
    }
    
    /**
     * Check to see if value is in this range
     * @param two Range to compare against
     * @param value any value (min or max)
     * @return true or false
     */
    private boolean InRange(Range<T> two, T value){
        return ( two.getMin().compareTo(value) <= 0 && two.getMax().compareTo(value) >= 0 )? true: false;
    }
    
    /**
     * Collapses one range into another
     * @param two Other range
     * @return Range or null if not collapsable
     */
    public Range<T> collapseRange(Range<T> two){
        // if inside then return larger
        Range<T> result = new Range<T>();
        if( collapsableRanges(two) == true || two.collapsableRanges(this) == true ){
            Logger.getLogger(Range.class.getName()).log(Level.FINE, "Collaspable ranges found");
            if( inSideRange(two) ) result = this;
            else if( two.inSideRange(this) ) result = two;
            // Need to see if range can be collapased
            else  
            {// neither range is completed inside the other
            result.min = ( min.compareTo(two.getMin()) == -1 || min.compareTo(two.getMin()) == 0 )? min : two.min;
            result.max = ( max.compareTo(two.getMax()) == 1 || max.compareTo(two.getMax()) == 0 )? max : two.max;
            }
        } else{
            Logger.getLogger(Range.class.getName()).log(Level.FINE, "Ranges cannot be collapsed");
            result = null;
        }
        return result;
    }

    /**
     * Override on how to compare tow ranges based on min and max values
     * @param o Other range
     * @return Positive if this is larger, zero if equal, and negative if this is smaller.
     */
    @Override
    public int compareTo(Range<T> o) {
        int r = 0;
        if( getMax() == o.getMax() && getMin() == o.getMin() ) r = 0;
        else if( getMax().compareTo(o.getMin()) == -1  ) r = -1;
        else if ( getMin().compareTo(o.getMax()) == 1 ) r = 1;
        return r;
    }
    

}
