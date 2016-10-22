package lukaspastuszek.instant_mobile_receiver_android.resources;


import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lukaspastuszek.instant_mobile_receiver_android.R;
import lukaspastuszek.instant_mobile_receiver_android.interfaces.LoginCallbackInterface;


/**
 * Created by lpastusz on 21-Oct-16.
 */

public class AuthorizationResource {

    Context context;

    LoginCallbackInterface callbackInterface;

    public AuthorizationResource(Context mContext) {
        context = mContext;
    }

    public class AuthThread implements Runnable {

        String username, password;

        public AuthThread(String mUername, String mPassword) {
            username = mUername;
            password = mPassword;
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

                JSONObject cred = new JSONObject();
                cred.put("username", username);
                cred.put("password", password);
                cred.put("grant_type", "password");

                OutputStream os = connection.getOutputStream();
                os.write(cred.toString().getBytes("UTF-8"));
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

            onAuthRequestDone(chaine.toString());
        }
    }


    private void onAuthRequestDone(String resp) {

        String authToken = "";
        try {
            JSONObject jObject = new JSONObject(resp);
            String accessToken = jObject.getString("access_token");
            String tokenType = jObject.getString("token_type");
            authToken = tokenType + " " + accessToken;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        callbackInterface.onLoginRequestCompleted(authToken);
    }


    public void authenticateLogin(LoginCallbackInterface callbackInterface, String username, String password) {

        this.callbackInterface = callbackInterface;
        Runnable r = new AuthThread(username, password);
        new Thread(r).start();

    }

}
