package lukaspastuszek.instant_mobile_receiver_android.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lukaspastuszek.instant_mobile_receiver_android.R;
import lukaspastuszek.instant_mobile_receiver_android.activities.DetailActivity;
import lukaspastuszek.instant_mobile_receiver_android.activities.HomepageActivity;
import lukaspastuszek.instant_mobile_receiver_android.resources.InternalStorageResource;

import static android.app.PendingIntent.getActivity;
import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by lpastusz on 22-Oct-16.
 */

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> hash = remoteMessage.getData();
        String title = hash.get("title");
        String text = hash.get("text");
        String id = hash.get("id");

        long dateTime = remoteMessage.getSentTime();

        InternalStorageResource internalStorageResource = new InternalStorageResource(getApplicationContext());
        internalStorageResource.putNotificationData(dateTime, id, title, text);

        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("notificationId", id);
        intent.putExtra("fromNotification", true);
        Random generator = new Random();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), generator.nextInt(), intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(getUniqueNotificationId(), mBuilder.build());

        Intent local = new Intent();
        local.setAction("lukaspastuszek.instant_mobile_receiver_android.update_homepage");
        this.sendBroadcast(local);

    }

    private int getUniqueNotificationId() {
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        return Integer.valueOf(last4Str);
    }


}
