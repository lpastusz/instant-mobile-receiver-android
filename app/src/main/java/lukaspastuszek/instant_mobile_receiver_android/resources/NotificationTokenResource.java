package lukaspastuszek.instant_mobile_receiver_android.resources;

import android.content.Context;
import android.util.Log;

/**
 * Created by lpastusz on 22-Oct-16.
 *
 *
 *
 * Implemented lated, should update the tingy on server, when its changed
 */

public class NotificationTokenResource {

    Context context;

    public NotificationTokenResource(Context mContext) {
        context = mContext;
    }

    public void sendNewToken(String token) {

        DeviceInformationResource deviceInformationResource = new DeviceInformationResource(context);

        String uniqueDeviceId = deviceInformationResource.getDeviceUniqueId();

        Log.d("token", token);
        Log.d("token", uniqueDeviceId);

    }

}
