package com.apprevelations.floatingapps;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class PInfo {
    public String appname = "";
    public String pname = "";
    public Drawable icon;
    public void prettyPrint() {
        Log.v("tag", appname + "\t" + pname + "\t");
    }
}