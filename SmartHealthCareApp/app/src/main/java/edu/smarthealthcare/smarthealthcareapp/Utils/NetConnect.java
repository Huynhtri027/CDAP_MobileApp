package edu.smarthealthcare.smarthealthcareapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Admin on 3/15/2017.
 */

public class NetConnect {

    public static boolean isNetworkConnected(Context mContext) {

        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return(cm.getActiveNetworkInfo()!=null);

    }
}
