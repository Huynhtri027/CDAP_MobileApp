package edu.smarthealthcare.smarthealthcareapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by RG User on 07/27/17.
 */

public class SharedPreferenceReader {
    private static SharedPreferences preferences;
    private static SharedPreferences preferences1;
    private static int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SmartHealthCare";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_PASSWORD = "user_password";


    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public static void createLoginSession(Context context, String user_email, String user_password, String user_id,
                                          String user_name){
        preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_EMAIL,user_email);
        editor.putString(KEY_NAME,user_name);
        editor.putString(KEY_USER_ID,user_id);
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
//        editor.putString(KEY_FIREBASE_TOKEN, FirebaseInstanceId.getInstance().getToken());
        editor.putString(KEY_PASSWORD,user_password);
        editor.apply();
    }

    public static String getUserID(Context context){
        return getPreferences(context).getString(KEY_USER_ID,null);
    }

    public static String getUserEmail(Context context){
        return getPreferences(context).getString(KEY_EMAIL,"");
    }

    public static String getUserPassword(Context context){
        return getPreferences(context).getString(KEY_PASSWORD,"");
    }

    public static String getUserName(Context context){
        return getPreferences(context).getString(KEY_NAME,"");
    }

    public static boolean isLoggedIn(Context context){
        return getPreferences(context).getBoolean(KEY_IS_LOGGED_IN,false);
    }

    public static void clearPreferences(Context context){
        getPreferences(context).edit().clear().apply();
    }

    public static void setUserName(Context context, String userName){
        getPreferences(context).edit().putString(KEY_NAME,userName).apply();
    }

    public static void setUserID(Context context, String userID){
        getPreferences(context).edit().putString(KEY_USER_ID,userID).apply();
    }

//    public static String getFirebaseToken(Context context){
//        return getPreferences(context).getString(KEY_FIREBASE_TOKEN,"");
//    }
//
//    public static void setFirebaseToken(Context context, String token){
//        getPreferences(context).edit().putString(KEY_FIREBASE_TOKEN,token).apply();
//    }
}
