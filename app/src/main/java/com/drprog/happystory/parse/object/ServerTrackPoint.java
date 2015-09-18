package com.drprog.happystory.parse.object;

import android.support.annotation.IntRange;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.Calendar;

@ParseClassName("TrackPoint")
public class ServerTrackPoint extends ParseObject{
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_VALUE = "value";
    public static final String FIELD_COUNTRY_CODE = "countryCode";
    public static final String FIELD_DATE_YEAR = "year";
    public static final String FIELD_DATE_MONTH = "month";
    public static final String FIELD_DATE_DAY = "day";
    public static final String FIELD_TIMESTAMP = "timestamp";
    public static final String FIELD_LOCATION = "location";

    private static final int MIN_VALUE = -5;
    private static final int MAX_VALUE = 5;


    public void setUserId(String _userId){
        put(FIELD_USER_ID, _userId);
    }
    public String getUserId(){
        return getString(FIELD_USER_ID);
    }

    public void setValue(@IntRange(from = MIN_VALUE, to = MAX_VALUE) int _value){
        put(FIELD_VALUE, _value);
    }

    /**
     * @return value or 0 if not exists
     */
    public int getValue(){
        return getInt(FIELD_VALUE);
    }

    public void setCountryCode(int _code){
        put(FIELD_COUNTRY_CODE, _code);
    }

    public int getCountryCode(){
        return getInt(FIELD_COUNTRY_CODE);
    }

    public void setTimestamp(long _timestamp){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_timestamp);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        put(FIELD_TIMESTAMP, _timestamp);
        put(FIELD_DATE_YEAR, year);
        put(FIELD_DATE_MONTH, month);
        put(FIELD_DATE_DAY, day);
    }

    public long getTimestamp(){
        return getLong(FIELD_TIMESTAMP);
    }

    public void setLocation(double _latitude, double _longitude){
        put(FIELD_LOCATION, new ParseGeoPoint(_latitude, _longitude));
    }

    public ParseGeoPoint getLocation(){
        return getParseGeoPoint(FIELD_LOCATION);
    }

}
