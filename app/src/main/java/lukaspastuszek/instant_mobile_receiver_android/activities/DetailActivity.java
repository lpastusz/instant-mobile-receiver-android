package lukaspastuszek.instant_mobile_receiver_android.activities;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lukaspastuszek.instant_mobile_receiver_android.R;
import lukaspastuszek.instant_mobile_receiver_android.resources.InternalStorageResource;

import static android.R.attr.id;

public class DetailActivity extends AppCompatActivity {

    boolean isFromNotification = false;

    @Override
    public void onBackPressed() {
        if(isFromNotification) {
            Intent intent = new Intent(this, HomepageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
        TextView textViewText = (TextView) findViewById(R.id.textViewText);

        Intent intent = getIntent();
        String notificationId = intent.getExtras().getString("notificationId", null);
        isFromNotification = intent.getExtras().getBoolean("fromNotification", false);

        if (notificationId != null) {
            InternalStorageResource internalStorageResource = new InternalStorageResource(getApplicationContext());
            JSONObject item = internalStorageResource.getNotificationDataAsJSON(notificationId);

            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");

            String title = null;
            String text = null;
            Long dateTime = null;

            try {
                title = item.getString("title");
            } catch (JSONException e) {
                title = null;
            }

            try {
                text = item.getString("text");
            } catch (JSONException e) {
                text = null;
            }

            try {
                dateTime = item.getLong("dateTime");
            } catch (JSONException e) {
                dateTime = null;
            }

            if (dateTime != null) {
                textViewDate.setText(format.format(new Date(dateTime)));
            }

            if (title != null) {
                textViewTitle.setText(title);
            }

            if (text != null) {
                textViewText.setText(text);
            }
        }

    }
}
