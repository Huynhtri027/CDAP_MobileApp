package edu.smarthealthcare.smarthealthcareapp.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import edu.smarthealthcare.smarthealthcareapp.R;

/**
 * Created by Brother on 14/09/2017.
 */

public class FcmMessagingService extends FirebaseMessagingService {

    private static int id = 0;

    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        String date = remoteMessage.getData().get("date");
        String click_action = remoteMessage.getData().get("clickAction"); //to handle notification click action
        showNotification(title, message,click_action);
    }

    private void showNotification(String title, String message, String click_action) {
        Intent i = new Intent(click_action);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);
        int greenColorValue = Color.GREEN;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_cross)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.FLAG_SHOW_LIGHTS)
                .setSound(alarmSound)
                .setLights(greenColorValue,3000,1000)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(id,builder.build());
        id++;

    }
}
