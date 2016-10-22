package lukaspastuszek.instant_mobile_receiver_android.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lukaspastuszek.instant_mobile_receiver_android.R;
import lukaspastuszek.instant_mobile_receiver_android.interfaces.LogoutCallbackInterface;
import lukaspastuszek.instant_mobile_receiver_android.resources.AuthorizationResource;
import lukaspastuszek.instant_mobile_receiver_android.resources.LocalStorageResource;

public class HomepageActivity extends AppCompatActivity implements LogoutCallbackInterface {

    HomepageActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisActivity = this;

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
    }

    @Override
    public void onLogoutRequestSuccess(String token) {



    }
}
