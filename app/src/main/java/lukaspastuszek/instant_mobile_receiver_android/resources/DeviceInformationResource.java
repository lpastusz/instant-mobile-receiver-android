package lukaspastuszek.instant_mobile_receiver_android.resources;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.util.UUID;

import static java.security.AccessController.getContext;

/**
 * Created by lpastusz on 22-Oct-16.
 */

public class DeviceInformationResource {

    Context context;

    public DeviceInformationResource(Context mContext) {
        context = mContext;
    }

    public String getDeviceUniqueId() {


        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        return deviceId;
    }

    public String getDeviceName() {
        return (Build.MANUFACTURER.equals("unknown") ? "Emulator" : Build.MANUFACTURER)  + " - " + Build.MODEL;
    }

}
