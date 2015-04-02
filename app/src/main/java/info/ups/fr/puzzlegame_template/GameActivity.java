package info.ups.fr.puzzlegame_template;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by clement on 27/03/2015.
 */
public class GameActivity extends ActionBarActivity{

    private SensorManager manager = null;
    private Sensor accelerometer = null;

    private View puzzle;
    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        Queue<Float> lastValues = new ArrayDeque<Float>();

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] val = sensorEvent.values;

            float max=0;
            for (float f : val) {
                if (f>max)
                    max = f;
                else if(-1*f>max)
                    max = -1*f;
            }

            lastValues.offer(max);
            if(lastValues.size()>8)
                lastValues.poll();

            float moyenne = 0;
            max = 20;
            for (float f : lastValues) {
                if (f<max)
                    max = f;
                moyenne += f;
            }
            moyenne = (moyenne-max) / (lastValues.size()-1);

            if(moyenne > 16) {
                SharedPreferences preferences = getSharedPreferences("preferences", 0);
                final SharedPreferences.Editor editor = preferences.edit();

                editor.remove("pieces_ids");
                editor.commit();

                Puzzle.shuffle();

                findViewById(R.id.view).invalidate();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.manager.registerListener(this.mSensorEventListener, this.accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.manager.unregisterListener(this.mSensorEventListener, this.accelerometer);
    }
}
