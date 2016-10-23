package lukaspastuszek.instant_mobile_receiver_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lukaspastuszek.instant_mobile_receiver_android.R;
import lukaspastuszek.instant_mobile_receiver_android.comparators.NotificationComparator;

import static android.R.attr.format;

/**
 * Created by lpastusz on 23-Oct-16.
 */

public class NotificationListAdapter extends BaseAdapter {

    List<JSONObject> notifications;
    Context context;

    public NotificationListAdapter(JSONArray mNotifications, Context mContext) {

        notifications = new ArrayList<JSONObject>();

        try {
            for (int i = 0; i < mNotifications.length(); i++) {
                notifications.add(mNotifications.getJSONObject(i));
            }

            Collections.sort(notifications, new NotificationComparator());

        } catch (JSONException e) {
            // none
        }

        context = mContext;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return notifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.notification_single_list_item, null);

        JSONObject item = (JSONObject) getItem(position);

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");

        TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        TextView textViewText = (TextView) view.findViewById(R.id.textViewText);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);

        String title = null;
        String text = null;
        String perex = null;
        Long dateTime = null;

        try {
            title = item.getString("title");
        } catch (JSONException e) {
            title = null;
        }

        try {
            text = item.getString("text");
        } catch (JSONException e) {
            text = null;
        }

        try {
            perex = item.getString("perex");
        } catch (JSONException e) {
            perex = null;
        }

        try {
            dateTime = item.getLong("dateTime");
        } catch (JSONException e) {
            dateTime = null;
        }

        if (dateTime != null) {
            textViewDate.setText(format.format(new Date(dateTime)));
        }

        if (title != null) {
            textViewTitle.setText(title);
        }

        if (perex != null) {
            textViewText.setText(perex);
        }
        else if (text != null) {
            textViewText.setText(text);
        }

        return view;
    }

    public List<JSONObject> getAllData() {
        return notifications;
    }
}
