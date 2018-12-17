package com.example.sergiogarrido.citynet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.json.JSONException;

public class UtilsApp {

    /**
     * Check if user has Internet
     * @return boolean if has internet
     */
    public static boolean checkNetworkConnection(ConnectivityManager connMgr) {

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            // show "Connected" & type of network "WIFI or MOBILE"
            //Toast.makeText(this, "Connected "+networkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
        } else {
            // show "Not Connected"
           //
        }

        return isConnected;
    }

    public static String convertTo8UTF(String json) {
        String temp;
        temp = json.replace("{", "%7B");
        temp =  temp.replace("}", "%7D");
        temp =  temp.replace("\"", "%22");
        return temp;
    }
}
