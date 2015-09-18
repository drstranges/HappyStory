package com.drprog.happystory.db.model;

import android.support.annotation.IntRange;

public class TrackPoint {

    private static final int MIN_VALUE = -5;
    private static final int MAX_VALUE = 5;

    public Long id;
    public Long timestamp;
    public int value = 0;

    public TrackPoint() {
    }

    public TrackPoint(@IntRange (from = MIN_VALUE, to = MAX_VALUE) int _value) {
//        timestamp = System.currentTimeMillis();
        value = _value;
    }
}
