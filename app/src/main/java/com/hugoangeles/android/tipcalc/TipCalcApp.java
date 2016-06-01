package com.hugoangeles.android.tipcalc;

import android.app.Application;

/**
 * Created by Hugo on 30/05/16.
 */
public class TipCalcApp extends Application {

    private static String ABOUT_URL = "https://about.me/adriancatalan";

    public String getAboutUrl() {
        return ABOUT_URL;
    }
}
