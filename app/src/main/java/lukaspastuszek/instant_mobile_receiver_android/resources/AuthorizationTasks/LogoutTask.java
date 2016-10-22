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
import lukaspastuszek.instant_mobile_receiver_android.resources.AuthorizationResource;
import lukaspastuszek.instant_mobile_receiver_android.resources.DeviceInformationResource;
import lukaspastuszek.instant_mobile_receiver_android.resources.LocalStorageResource;

import static android.R.attr.password;

/**
 * Created by lpastusz on 22-Oct-16.
 */

public class LogoutTask implements Runnable {

    Context context;

    AuthorizationResourceCallbackInterface callbackInterface;

    String bearerToken;

    public LogoutTask(Context mContext, AuthorizationResourceCallbackInterface mCallbackInterface, String mBearerToken) {
        context = mContext;
        callbackInterface = mCallbackInterface;
        bearerToken = mBearerToken;
    }


    @Override
    public void run() {


        StringBuffer chaine = new StringBuffer("");
        try{
            URL url = new URL(context.getResources().getString(R.string.URL_logout));


            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Authorization", bearerToken);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();


            JSONObject body = new JSONObject();
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

        callbackInterface.onLogoutRequestSuccess(chaine.toString());

    }


}
