package com.wjjiang.materialdesigns;

import android.app.Application;
import android.content.Context;

import com.wjjiang.materialdesigns.common.util.Logger;

/**
 * Created by Jiangwenjin on 16/8/14.
 */
public class CustomApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.d("onLowMemory...");
        System.gc();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.setLogFileFolder(getApplicationContext());

        Logger.d("application onCreate...");
    }
}
