package lukaspastuszek.instant_mobile_receiver_android.components;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import lukaspastuszek.instant_mobile_receiver_android.R;

/**
 * Created by lpastusz on 24-Oct-16.
 */

public class CustomToast {

    public enum ToastType {
        SUCCESS, ERROR
    }

    public static Toast construct(Context context, LayoutInflater inflater, String text, ToastType type) {
        View view = inflater.inflate(R.layout.toast_error, null);

        if (type == ToastType.SUCCESS) {
            view = inflater.inflate(R.layout.toast_success, null);
        }
        else {
            view = inflater.inflate(R.layout.toast_error, null);
        }

        TextView txtEdit = (TextView) view.findViewById(R.id.txtMessage);
        txtEdit.setText(text);

        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 60);
        return toast;
    }

}
