package lukaspastuszek.instant_mobile_receiver_android.resources;


import android.content.Context;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import lukaspastuszek.instant_mobile_receiver_android.interfaces.AuthorizationResourceCallbackInterface;
import lukaspastuszek.instant_mobile_receiver_android.interfaces.LoginCallbackInterface;
import lukaspastuszek.instant_mobile_receiver_android.interfaces.LogoutCallbackInterface;
import lukaspastuszek.instant_mobile_receiver_android.resources.AuthorizationTasks.LoginTask;
import lukaspastuszek.instant_mobile_receiver_android.resources.AuthorizationTasks.LogoutTask;


/**
 * Created by lpastusz on 21-Oct-16.
 */

public class AuthorizationResource implements AuthorizationResourceCallbackInterface {

    Context context;

    LoginCallbackInterface loginCallbackInterface;
    LogoutCallbackInterface logoutCallbackInterface;

    public AuthorizationResource(Context mContext) {
        context = mContext;
    }

    @Override
    public void onLoginRequestSuccess(String resp) {

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

        loginCallbackInterface.onLoginRequestSuccess(authToken);
    }

    @Override
    public void onLogoutRequestSuccess(String resp) {

        // no need to do anything

    }

    public void sendLoginRequest(LoginCallbackInterface loginCallbackInterface, String username, String password) {

        this.loginCallbackInterface = loginCallbackInterface;

        Runnable r = new LoginTask(context, this, username, password);
        new Thread(r).start();

    }

    public void sendLogoutRequest(LogoutCallbackInterface logoutCallbackInterface, String bearerToken) {

        this.logoutCallbackInterface = logoutCallbackInterface;
        Runnable r = new LogoutTask(context, this, bearerToken);
        new Thread(r).start();

    };

}
