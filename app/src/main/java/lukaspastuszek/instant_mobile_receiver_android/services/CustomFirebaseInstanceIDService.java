package lukaspastuszek.instant_mobile_receiver_android.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import lukaspastuszek.instant_mobile_receiver_android.resources.LocalStorageResource;
import lukaspastuszek.instant_mobile_receiver_android.resources.NotificationTokenResource;

/**
 * Created by lpastusz on 22-Oct-16.
 */

public class CustomFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("lala", refreshedToken);

        LocalStorageResource localStorageResource = new LocalStorageResource(getBaseContext());
        localStorageResource.putFirebaseToken(refreshedToken);

    }
}
