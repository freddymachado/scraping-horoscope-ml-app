package com.example.horoscope2;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GlobalActivity extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor  editor = userInfo.edit();
        boolean loggedIn =  userInfo.getBoolean("loggedIn", false);
        boolean palmPhotoReceived =  userInfo.getBoolean("palmPhotoReceived", false);
        String todayDate = userInfo.getString("todayDate"," ");
        String todayDate2 = userInfo.getString("todayDate2"," ");
        if (loggedIn)
        {
            Intent intent = new Intent(this,PrincipalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}