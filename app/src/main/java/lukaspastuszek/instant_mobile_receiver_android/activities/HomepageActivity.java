package lukaspastuszek.instant_mobile_receiver_android.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;

import lukaspastuszek.instant_mobile_receiver_android.R;
import lukaspastuszek.instant_mobile_receiver_android.adapters.NotificationListAdapter;
import lukaspastuszek.instant_mobile_receiver_android.interfaces.LogoutCallbackInterface;
import lukaspastuszek.instant_mobile_receiver_android.resources.AuthorizationResource;
import lukaspastuszek.instant_mobile_receiver_android.resources.InternalStorageResource;
import lukaspastuszek.instant_mobile_receiver_android.resources.LocalStorageResource;

public class HomepageActivity extends AppCompatActivity implements LogoutCallbackInterface {

    HomepageActivity thisActivity;

    BroadcastReceiver updateUIReciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisActivity = this;

        Intent i = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        Button buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalStorageResource localStorageResource = new LocalStorageResource(getApplicationContext());
                String bearerToken = localStorageResource.getBearerToken();

                AuthorizationResource authorizationResource = new AuthorizationResource(getApplicationContext());
                authorizationResource.sendLogoutRequest(thisActivity, bearerToken);

                localStorageResource.removeBearerToken();

                Intent intent = new Intent(thisActivity, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });


        populateListViewWithData();

        registerForNotificationUpdateBroadcast();
    }

    @Override
    public void onLogoutRequestSuccess(String token) {



    }

    private void populateListViewWithData() {
        ListView notificationListView = (ListView) findViewById(R.id.notificationListView);
        InternalStorageResource internalStorageResource = new InternalStorageResource(getApplicationContext());
        final NotificationListAdapter notificationListAdapter = new NotificationListAdapter(internalStorageResource.getNotificationDataAsJSON(), getApplicationContext());
        notificationListView.setAdapter(notificationListAdapter);
        notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String notificationId = null;
                try {
                    notificationId = notificationListAdapter.getAllData().get(position).getString("id");
                } catch (JSONException e) {
                    notificationId = null;
                }

                if (notificationId != null) {
                    Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                    i.putExtra("notificationId", notificationId);
                    startActivity(i);
                }
            }
        });
    }

    private void registerForNotificationUpdateBroadcast() {
        IntentFilter filter = new IntentFilter();

        filter.addAction("lukaspastuszek.instant_mobile_receiver_android.update_homepage");

        updateUIReciver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                populateListViewWithData();

            }
        };
        registerReceiver(updateUIReciver,filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(updateUIReciver);
        super.onDestroy();
    }
}
