package lukaspastuszek.instant_mobile_receiver_android.interfaces;

/**
 * Created by lpastusz on 22-Oct-16.
 */

public interface AuthorizationResourceCallbackInterface {

    public void onLoginRequestSuccess(String resp);

    public void onLogoutRequestSuccess(String resp);

}