package edu.smarthealthcare.smarthealthcareapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import edu.smarthealthcare.smarthealthcareapp.R;

/**
 * Created by Brother on 14/09/2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {
    String recent_token="";
    public void onTokenRefresh() {
        recent_token= FirebaseInstanceId.getInstance().getToken();
//        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.putString(getString(R.string.FCM_PREF),recent_token);
//        editor.commit();
    }
}
