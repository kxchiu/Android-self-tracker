package edu.uw.cwc8.selftracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple container for Milktea info
 */
public class Milktea {
    public String title;
    public int cup;
    public String time;
    public int rating;
    public String timestamp;


    public Milktea(String title, int cup, String time, int rating, String tStamp){
        this.title = title;
        this.cup = cup;
        this.time = time;
        this.rating = rating;
        this.timestamp = tStamp;
    }

    //default constructor; empty milktea
    public Milktea(){}

    public String toString(){
        return this.title + " ("+this.cup+")";
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
