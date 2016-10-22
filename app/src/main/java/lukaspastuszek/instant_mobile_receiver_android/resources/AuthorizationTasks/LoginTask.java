package lukaspastuszek.instant_mobile_receiver_android.resources.AuthorizationTasks;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lukaspastuszek.instant_mobile_receiver_android.R;
import lukaspastuszek.instant_mobile_receiver_android.interfaces.AuthorizationResourceCallbackInterface;
import lukaspastuszek.instant_mobile_receiver_android.resources.DeviceInformationResource;
import lukaspastuszek.instant_mobile_receiver_android.resources.LocalStorageResource;

/**
 * Created by lpastusz on 22-Oct-16.
 */

public class LoginTask implements Runnable {

    Context context;

    AuthorizationResourceCallbackInterface callbackInterface;

    String username, password;

    public LoginTask(Context mContext, AuthorizationResourceCallbackInterface mCallbackInterface, String mUername, String mPassword) {
        context = mContext;
        username = mUername;
        password = mPassword;
        callbackInterface = mCallbackInterface;
    }

    public void run() {
        StringBuffer chaine = new StringBuffer("");
        try{
            URL url = new URL(context.getResources().getString(R.string.URL_login));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Authorization", context.getResources().getString(R.string.AUTH_basic_token));
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            DeviceInformationResource deviceInformationResource = new DeviceInformationResource(context);
            LocalStorageResource localStorageResource = new LocalStorageResource(context);

            String deviceId = deviceInformationResource.getDeviceUniqueId();
            String firebaseToken = localStorageResource.getFirebaseToken();
            String deviceName = deviceInformationResource.getDeviceName();

            JSONObject body = new JSONObject();
            body.put("username", username);
            body.put("password", password);
            body.put("deviceId", deviceId);
            body.put("firebaseToken", firebaseToken);
            body.put("deviceName", deviceName);
            body.put("grant_type", "password");

            OutputStream os = connection.getOutputStream();
            os.write(body.toString().getBytes("UTF-8"));
            os.close();


            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        callbackInterface.onLoginRequestSuccess(chaine.toString());
    }
}