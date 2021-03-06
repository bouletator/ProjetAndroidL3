package info.ups.fr.puzzlegame_template;


import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class LevelChooserActivity extends ActionBarActivity {
    private ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_chooser);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        SharedPreferences sharedPreferences = getSharedPreferences("preferences",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        buttons.add((Button) findViewById(R.id.button0));
        buttons.add((Button) findViewById(R.id.button1));
        buttons.add((Button) findViewById(R.id.button2));
        buttons.add((Button) findViewById(R.id.button3));
        buttons.add((Button) findViewById(R.id.button4));
        buttons.add((Button) findViewById(R.id.button5));
        buttons.add((Button) findViewById(R.id.button6));
        buttons.add((Button) findViewById(R.id.button7));
        buttons.add((Button) findViewById(R.id.button8));
        buttons.add((Button) findViewById(R.id.button9));
        buttons.add((Button) findViewById(R.id.button10));
        buttons.add((Button) findViewById(R.id.button11));
        buttons.add((Button) findViewById(R.id.button12));
        buttons.add((Button) findViewById(R.id.button13));
        buttons.add((Button) findViewById(R.id.button14));
        buttons.add((Button) findViewById(R.id.button15));
        buttons.add((Button) findViewById(R.id.button16));
        buttons.add((Button) findViewById(R.id.button17));
        buttons.add((Button) findViewById(R.id.button18));
        buttons.add((Button) findViewById(R.id.button19));
        buttons.add((Button) findViewById(R.id.button20));
        buttons.add((Button) findViewById(R.id.button21));
        buttons.add((Button) findViewById(R.id.button22));
        buttons.add((Button) findViewById(R.id.button23));
        buttons.add((Button) findViewById(R.id.button24));

        int unlockedLevel = sharedPreferences.getInt("unlock", 0);
        for (Button b : buttons) {
            final int level =  Integer.parseInt(b.getText().toString())- 1;

            // Modification taille des boutons
            ViewGroup.LayoutParams param = b.getLayoutParams();
            param.width = metrics.widthPixels / 2;
            param.height = metrics.widthPixels / 2;
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32.0f);
            b.setTextColor(Color.WHITE);

            b.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(LevelChooserActivity.this, GameActivity.class);
                     editor.putInt("current_level",level);
                     editor.commit();

                     startActivity(intent);
                 }
            });

            if (level>unlockedLevel) {
                b.setEnabled(false);
                b.setAlpha(0.32f);
            }

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        SharedPreferences sharedPreferences = getSharedPreferences("preferences",0);
        int unlockedLevel = sharedPreferences.getInt("unlock", 0);

        for (Button b : buttons) {
            final int level =  Integer.parseInt(b.getText().toString())- 1;
            if (level>unlockedLevel) {
                b.setEnabled(false);
                b.setAlpha(0.32f);
            } else {
                b.setEnabled(true);
                b.setAlpha(1.0f);
            }
            b.invalidate();
        }
    }
}