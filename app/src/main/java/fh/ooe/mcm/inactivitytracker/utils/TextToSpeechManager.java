package fh.ooe.mcm.inactivitytracker.utils;

import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

import fh.ooe.mcm.inactivitytracker.Activities;
import fh.ooe.mcm.inactivitytracker.activities.MainActivity;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;
import weka.gui.beans.DataVisualizerBeanInfo;

public class TextToSpeechManager implements Observer {
    TextToSpeech tts;
    MainActivity parent;

    public TextToSpeechManager(MainActivity activity) {
        parent = activity;

        tts = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        //ConvertTextToSpeech("");
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }

    public void convertTextToSpeech(String text) {
        if(text == null || "".equals(text))
        {
            text = "Content not available";
        }

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof Predictor) {
            if(object instanceof Integer) {
                parent.runOnUiThread(() -> convertTextToSpeech(Activities.getNameOf((int) object)));
            }
        } else if(observable instanceof DatabaseHandler) {
            if(object instanceof String) {
                parent.runOnUiThread(() -> convertTextToSpeech((String)object));
            }
        } else if(observable instanceof Recognizer) {
            if(object instanceof String) {
                parent.runOnUiThread(() -> convertTextToSpeech((String)object));
            }
        }
    }
}
