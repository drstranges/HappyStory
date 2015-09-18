package com.drprog.happystory.parse;

import android.content.Context;

import com.drprog.happystory.Config;
import com.drprog.happystory.parse.object.ServerTrackPoint;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseUtils {

    public static void initParse(Context _context) {
        initSubclasses();
        Parse.enableLocalDatastore(_context);
        Parse.initialize(_context, Config.PARSE_APPLICATION_ID, Config.PARSE_CLIENT_KEY);
    }

    private static void initSubclasses() {
        ParseObject.registerSubclass(ServerTrackPoint.class);
    }
}
