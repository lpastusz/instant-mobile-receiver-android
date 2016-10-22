package lukaspastuszek.instant_mobile_receiver_android.resources;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lpastusz on 22-Oct-16.
 */

public class LocalStorageResource {

    Context context;

    public LocalStorageResource(Context mContext) {
        context = mContext;
    }

    private final String RESOURCE_FILE_NAME = "auth_resource_file";

    private final String KEY_AUTH_TOKEN = "authorization_token_bearer";
    private final String KEY_FIREBASE_TOKEN = "firebase_token";

    public void putBearerToken(String token) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(RESOURCE_FILE_NAME, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(KEY_AUTH_TOKEN, token);
        sharedPreferencesEditor.commit();

    }

    public void putFirebaseToken(String token) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(RESOURCE_FILE_NAME, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(KEY_FIREBASE_TOKEN, token);
        sharedPreferencesEditor.commit();

    }

    public String getBearerToken() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(RESOURCE_FILE_NAME, 0);
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);

    }

    public String getFirebaseToken() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(RESOURCE_FILE_NAME, 0);
        return sharedPreferences.getString(KEY_FIREBASE_TOKEN, null);

    }

    public boolean isBearerTokenSet() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(RESOURCE_FILE_NAME, 0);
        return sharedPreferences.contains(KEY_AUTH_TOKEN);

    }

    public void removeBearerToken() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(RESOURCE_FILE_NAME, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.remove(KEY_AUTH_TOKEN);
        sharedPreferencesEditor.commit();

    }


}
