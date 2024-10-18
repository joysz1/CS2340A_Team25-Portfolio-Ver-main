package com.example.a2340team25game.model;

import java.io.Serializable;
import java.util.Date;

public class Score implements Comparable<Score>, Serializable {
    private int value;
    private String name;
    private final Date date;
    public Score(int value, String name) {
        this.value = value;
        this.name = name;
        date = new Date();
    } // Score

    public int getValue() {
        return this.value;
    } // getValue

    public String getName() {
        return this.name;
    } // getName

    public void setName(String name) {
        if (this.name.equals("")) {
            this.name = name;
        } // if
    } // setName

    public String getDate() {
        return date.toString();
    } // getDate

    public String toString() {
        return getName() + "\t" + getValue() + "\t" + getDate();
    } // toString

    public void setValue(int newValue) {
        this.value = newValue;
    } // setValue

    @Override
    public int compareTo(Score other) {
        // first, compare by name (player identity)
        int nameComparison = this.getName().compareTo(other.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }

        //if names are the same, then compare by score value in reverse order
        return other.getValue() - this.getValue();
    } // compareTo
} // Score
