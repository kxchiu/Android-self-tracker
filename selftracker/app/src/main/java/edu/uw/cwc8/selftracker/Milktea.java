package edu.uw.cwc8.selftracker;

/**
 * A simple container for Milktea info
 */
public class Milktea {
    public String title;
    public int cup;
    public String time;
    public String desc;


    public Milktea(String title, int cup, String time, String desc){
        this.title = title;
        this.cup = cup;
        this.time = time;
        this.desc = desc;
    }

    //default constructor; empty milktea
    public Milktea(){}

    public String toString(){
        return this.title + " ("+this.cup+")";
    }
}
