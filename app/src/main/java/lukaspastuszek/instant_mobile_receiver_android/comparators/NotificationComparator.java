package lukaspastuszek.instant_mobile_receiver_android.comparators;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by lpastusz on 23-Oct-16.
 */

public class NotificationComparator implements Comparator<JSONObject> {
    @Override
    public int compare(JSONObject lhs, JSONObject rhs) {
        try {
             return new Long(rhs.getLong("dateTime")).compareTo(new Long(lhs.getLong("dateTime")));
        } catch (JSONException e) {
        }

        return 0;
    }

}
