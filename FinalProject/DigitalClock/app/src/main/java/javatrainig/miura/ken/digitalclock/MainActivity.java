package javatrainig.miura.ken.digitalclock;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.Toast;

public final class MainActivity extends AppCompatActivity {

    static int MINIMUM_FONT_SIZE = 10;
    static int MAXIMUM_FONT_SIZE = 80;

    private static final Typeface DEFAULT_TYPEFACE = Typeface.SANS_SERIF;
    private static final int DEFAULT_FONT_SIZE = MAXIMUM_FONT_SIZE;
    private static final int DEFAULT_COLOR = Color.BLACK;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private TextClock textClock;
    private ViewGroup container;
    private final BroadcastReceiver clockInformationReceiver = new ClockInformationReceiver();

    static final String ACTION_CHANGE_FONT = "javatrainig.miura.ken.digitalclock.action.CHANGE_FONT";
    static final String ACTION_CHANGE_FONT_SIZE = "javatrainig.miura.ken.digitalclock.action.CHANGE_FONT_SIZE";
    static final String ACTION_CHANGE_COLOR = "javatrainig.miura.ken.digitalclock.action.CHANGE_COLOR";
    static final String ACTION_CHANGE_BACKGROUND_COLOR = "javatrainig.miura.ken.digitalclock.action.CHANGE_BACKGROUND_COLOR";
    static final String ACTION_HIDE_CLOCK = "javatrainig.miura.ken.digitalclock.action.HIDE_CLOCK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textClock = (TextClock) findViewById(R.id.textClock);
        textClock.setTypeface(DEFAULT_TYPEFACE);
        textClock.setTextSize(DEFAULT_FONT_SIZE);
        textClock.setTextColor(DEFAULT_COLOR);
        textClock.setFormat24Hour("HH:mm:ss");
        textClock.setFormat12Hour("HH:mm:ss");

        container = (ViewGroup) findViewById(R.id.container);
        container.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);

        startService(new Intent(getBaseContext(), ClockInformationListeningService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filterToChangeFont = new IntentFilter(ACTION_CHANGE_FONT);
        registerReceiver(clockInformationReceiver, filterToChangeFont);
        IntentFilter filterToChangeFontSize = new IntentFilter(ACTION_CHANGE_FONT_SIZE);
        registerReceiver(clockInformationReceiver, filterToChangeFontSize);
        IntentFilter filterToChangeColor = new IntentFilter(ACTION_CHANGE_COLOR);
        registerReceiver(clockInformationReceiver, filterToChangeColor);
        IntentFilter filterToChangeBackgroundColor = new IntentFilter(ACTION_CHANGE_BACKGROUND_COLOR);
        registerReceiver(clockInformationReceiver, filterToChangeBackgroundColor);
        IntentFilter filterToHideClock = new IntentFilter(ACTION_HIDE_CLOCK);
        registerReceiver(clockInformationReceiver, filterToHideClock);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(clockInformationReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getBaseContext(), ClockInformationListeningService.class));
        super.onDestroy();
    }

    void changeFontSize(int newFontSize) {
        textClock.setTextSize(newFontSize);
    }

    void changeColor(int newColor) {
        textClock.setTextColor(newColor);
    }

    void changeBackgroundColor (int newColor) {
        container.setBackgroundColor(newColor);
    }
}
