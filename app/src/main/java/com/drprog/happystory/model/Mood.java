package com.drprog.happystory.model;

public enum Mood {
    VERY_BAD (-5)
    ;


    private int moodLevel;

    Mood(int moodLevel) {
        this.moodLevel = moodLevel;
    }
}
