package javatrainig.miura.ken.digitalclock;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.jar.Manifest;

/**
 * Created by Ken on 2016/10/01.
 */
public final class ClockInformationListeningService extends Service {

    private final static String TAG = "ClockInformationListeningService";
    private AudioManager mAudioManager;
    private SpeechRecognizer mSpeechRecognizer;
    private int tempMusicVolume;
    private int tempAlarmVolume;

    private static final String KEYWORD_TO_SHOW_CLOCK = "何時";
    private static final String KEYWORD_TO_CHANGE_FONT = "フォント";
    private static final String KEYWORD_TO_CHANGE_FONT_SIZE = "大きさ";
    private static final String KEYWORD_TO_CHANGE_COLOR = "時計の色";
    private static final String KEYWORD_TO_CHANGE_BACKGROUND_COLOR = "背景";
    private static final String KEYWORD_TO_HIDE_CLOCK = "消";

    // サポートするのはandroidシステムから取得できるこの三つの論理フォントのみ
    static final String SAN_SERIF = "サンセリフ";
    static final String SERIF = "セリフ";
    static final String MONO_SPACE = "monospace";

    private static final String ERROR_MESSAGE_NO_SUPPORTED_FONT = "サポートされていないフォントを指定しています";
    private static final String ERROR_MESSAGE_NO_RECOGNITION = "正しく音声が認識されていません。はっきりと発音してください";
    private static final String ERROR_MESSAGE_NO_SUPPORTED_FONT_SIZE = "サポートされていないフォントサイズを指定しています";

    private static final Map<String, Integer> supportedColors = new HashMap<>();
    static {
        supportedColors.put("黒", Color.BLACK);
        supportedColors.put("青", Color.BLUE);
        supportedColors.put("シアン", Color.CYAN);
        supportedColors.put("ダークグレイ", Color.DKGRAY);
        supportedColors.put("グレイ", Color.GRAY);
        supportedColors.put("緑", Color.GREEN);
        supportedColors.put("ライトグレイ", Color.LTGRAY);
        supportedColors.put("マジェンタ", Color.MAGENTA);
        supportedColors.put("赤", Color.RED);
        supportedColors.put("白", Color.WHITE);
        supportedColors.put("黄色", Color.YELLOW);
    }
    private static final String ERROR_MESSAGE_NO_SUPPORTED_COLOR = "サポートされていない色を指定しています。";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if (!SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {
            Log.d(TAG, "isRecognitionAvailable returns false");
            stopSelf();
            return;
        }
        startListening();
        return;
    }

    @Override
    public void onDestroy() {
        stopListening();
        super.onDestroy();
    }

    private void startListening(){
        // SpeechRecognizerの通知音を消すためにstartListeningする直前で、直前の音量を記憶し、ミュートにする
        tempMusicVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        tempAlarmVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);

        if (mSpeechRecognizer == null) {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mSpeechRecognizer.setRecognitionListener(new ClockInformationRecognitionListener());
        }
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
        mSpeechRecognizer.startListening(intent);
    }

    private void stopListening() {
        if (mSpeechRecognizer != null) {
            // TODO destroyするまえに何かする必要ある？
            //mSpeechRecognizer.stopListening();
            //mSpeechRecognizer.cancel();
            mSpeechRecognizer.destroy();
            mSpeechRecognizer = null;
        }
        // SpeechRecognizerの通知音を消すためにミュートにしていた音量をもとに戻す
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, tempMusicVolume, 0);
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, tempAlarmVolume, 0);
    }

    private void restartListening() {
        stopListening();
        startListening();
    }

    private class ClockInformationRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Log.d(TAG, "onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        @Override
        public void onRmsChanged(float v) {
            //Log.d(TAG, "onRmsChanged, RMS: " + v + " dB");
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            Log.d(TAG, "onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error) {
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    Log.e(TAG, "ERROR_AUDIO");
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    Log.e(TAG, "ERROR_CLIENT");
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    Log.e(TAG, "ERROR_INSUFFICIENT_PERMISSIONS");
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    Log.e(TAG, "ERROR_NETWORK");
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    Log.e(TAG, "ERROR_NETWORK_TIMEOUT");
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    // debug log due to normal case
                    Log.d(TAG, "ERROR_NO_MATCH");
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    Log.e(TAG, "ERROR_RECOGNIZER_BUSY");
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    Log.e(TAG, "ERROR_SERVER");
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    // debug log due to normal case
                    Log.d(TAG, "ERROR_SPEECH_TIMEOUT");
                    break;
                default:
                    Log.e(TAG, "UNEXPECTED_ERROR");
                    break;
            }
            restartListening();
        }

        @Override
        public void onResults(Bundle results) {
            List<String> recordedData = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (final String s : recordedData) {
                if (s.contains(KEYWORD_TO_SHOW_CLOCK)) {
                    Intent intent = new Intent(ClockInformationListeningService.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (s.contains(KEYWORD_TO_CHANGE_FONT )) {
                    Intent intent = new Intent(MainActivity.ACTION_CHANGE_FONT);
                    if (s.contains(SAN_SERIF)) {
                        intent.putExtra(ClockInformationReceiver.KEY_FONT, SAN_SERIF);
                        getBaseContext().sendBroadcast(intent);
                    } else if (s.contains(SERIF)) {
                        intent.putExtra(ClockInformationReceiver.KEY_FONT, SERIF);
                        getBaseContext().sendBroadcast(intent);
                    } else if (s.contains(MONO_SPACE)) {
                        intent.putExtra(ClockInformationReceiver.KEY_FONT, MONO_SPACE);
                        getBaseContext().sendBroadcast(intent);
                    } else {
                        Toast.makeText(ClockInformationListeningService.this, ERROR_MESSAGE_NO_SUPPORTED_FONT, Toast.LENGTH_SHORT).show();
                    }
                } else if (s.contains(KEYWORD_TO_CHANGE_FONT_SIZE)) {
                    try {
                        final int extractedFontSize = Integer.parseInt(s.replaceAll("[^0-9]", ""));
                        if (MainActivity.MINIMUM_FONT_SIZE <= extractedFontSize && extractedFontSize <= MainActivity.MAXIMUM_FONT_SIZE) {
                            Intent intent = new Intent(MainActivity.ACTION_CHANGE_FONT_SIZE);
                            intent.putExtra(ClockInformationReceiver.KEY_FONT_SIZE, extractedFontSize);
                            getBaseContext().sendBroadcast(intent);
                            break;
                        } else {
                            Toast.makeText(ClockInformationListeningService.this, ERROR_MESSAGE_NO_SUPPORTED_FONT_SIZE, Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(ClockInformationListeningService.this, ERROR_MESSAGE_NO_RECOGNITION, Toast.LENGTH_SHORT).show();
                    }
                } else if (s.contains(KEYWORD_TO_CHANGE_COLOR )) {
                    final Integer color = extractColor(s);
                    if (color == null) {
                        Toast.makeText(ClockInformationListeningService.this, ERROR_MESSAGE_NO_SUPPORTED_COLOR, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.ACTION_CHANGE_COLOR);
                        intent.putExtra(ClockInformationReceiver.KEY_COLOR, color);
                        getBaseContext().sendBroadcast(intent);
                        break;
                    }
                } else if (s.contains(KEYWORD_TO_CHANGE_BACKGROUND_COLOR)) {
                    final Integer color = extractColor(s);
                    if (color == null) {
                        Toast.makeText(ClockInformationListeningService.this, ERROR_MESSAGE_NO_SUPPORTED_COLOR, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.ACTION_CHANGE_BACKGROUND_COLOR);
                        intent.putExtra(ClockInformationReceiver.KEY_COLOR, color);
                        getBaseContext().sendBroadcast(intent);
                        break;
                    }
                } else if (s.contains(KEYWORD_TO_HIDE_CLOCK)) {
                    Intent intent = new Intent(MainActivity.ACTION_HIDE_CLOCK);
                    getBaseContext().sendBroadcast(intent);
                } else {
                    Log.d(TAG, "No supported keyword");
                }
            }
            restartListening();
        }

        @Nullable
        private Integer extractColor(String s) {
            for (final String colorString: supportedColors.keySet()) {
                if (s.contains(colorString)) {
                    return supportedColors.get(colorString);
                }
            }
            return null;
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            Log.d(TAG, "onPartialResults");
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            Log.d(TAG, "onEvent");
        }
    }
}
