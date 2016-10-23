package lukaspastuszek.instant_mobile_receiver_android.resources;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.PriorityQueue;

import static android.R.attr.id;
import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * Created by lpastusz on 23-Oct-16.
 */

public class InternalStorageResource {

    private final String FILE_NAME = "notification_data";

    private Context context;

    public InternalStorageResource(Context mContext) {
        context = mContext;
    }

    public void putNotificationData(long dateTime, String id, String title, String text) {


        JSONObject notificationData = new JSONObject();
        JSONArray arr = null;
        String fileData = null;
        try {

            notificationData.put("dateTime", dateTime);
            notificationData.put("title", title);
            notificationData.put("text", text);
            notificationData.put("id", id);

            if (text.length() > 80) {
                notificationData.put("perex", text.substring(0, 76).concat(" ..."));
            }


            fileData = getNotificationData();
            if (fileData != null) {
                arr = new JSONArray(fileData);
                arr.put(notificationData);
            }

        } catch (JSONException e) {
            Log.e("Internal storage error", "JSONException: " + e.toString());
            arr = null;
        }

        if (arr == null) {
            arr = new JSONArray();
            arr.put(notificationData);
        }

        String newFileData = arr.toString();

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(newFileData.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
                Log.e("Internal storage error", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Internal storage error", "Can not read file: " + e.toString());
        }
    }

    public String getNotificationData() {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Internal storage error", "File not found: " + e.toString());
            return null;
        } catch (IOException e) {
            Log.e("Internal storage error", "Can not read file: " + e.toString());
            return null;
        }

        return ret;
    }

    public JSONArray getNotificationDataAsJSON() {

        String data = getNotificationData();
        JSONArray result;

        try {
            if (data == null) {
                result = new JSONArray();
            }
            else {
                result = new JSONArray(data);
            }
        } catch (JSONException e) {
            result = new JSONArray();
        }

        return result;
    }

    public JSONObject getNotificationDataAsJSON(String notificationId) {
        JSONArray arr = getNotificationDataAsJSON();

        try {
            for (int i = 0; i < arr.length(); i++) {
                if (arr.getJSONObject(i).has("id") && arr.getJSONObject(i).get("id").equals(notificationId)) {
                    return arr.getJSONObject(i);
                }
            }
        } catch (JSONException e) {
            return  null;
        }

        return null;
    }

}
