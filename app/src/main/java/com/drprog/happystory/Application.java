package com.drprog.happystory;

import com.drprog.happystory.parse.ParseUtils;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        // Enable Local Datastore.
        ParseUtils.initParse(this);

    }
}
