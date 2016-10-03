package javatrainig.miura.ken.digitalclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by Ken on 2016/10/03.
 */
public class ClockInformationReceiver extends BroadcastReceiver {

    private static final String TAG = "ClockInformationReceiver";

    static final String KEY_FONT_SIZE = "javatrainig.miura.ken.digitalclock.FONT_SIZE";
    private static final int ERROR_NO_RECEIVED_FONT_SIZE = -1;

    static final String KEY_COLOR = "javatrainig.miura.ken.digitalclock.COLOR";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.e(TAG, "intent is null");
            return;
        }
        if (intent.getAction() == null) {
            Log.e(TAG, "intent.getAction() is null");
            return;
        }
        if (!(context instanceof MainActivity)) {
            Log.e(TAG, "Unexpected context: " + context);
            return;
        }
        final String action = intent.getAction();
        switch (action) {
            case MainActivity.ACTION_CHANGE_FONT_SIZE:
                final int newFontSize = intent.getIntExtra(KEY_FONT_SIZE, ERROR_NO_RECEIVED_FONT_SIZE);
                if (newFontSize != ERROR_NO_RECEIVED_FONT_SIZE) {
                    ((MainActivity)context).changeFontSize(newFontSize);
                }
                break;
            case MainActivity.ACTION_CHANGE_COLOR:
                final int newColor = intent.getIntExtra(KEY_COLOR, Color.BLACK);
                ((MainActivity)context).changeColor(newColor);
                break;
            case MainActivity.ACTION_CHANGE_BACKGROUND_COLOR:
                final int newBackgroundColor = intent.getIntExtra(KEY_COLOR, Color.WHITE);
                ((MainActivity)context).changeBackgroundColor(newBackgroundColor);
                break;
            case MainActivity.ACTION_HIDE_CLOCK:
                ((MainActivity) context).finish();
                break;
            default:
                Log.e(TAG, "Unexpected ACTION");
        }
    }
}
