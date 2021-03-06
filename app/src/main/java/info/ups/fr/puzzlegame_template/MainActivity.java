package info.ups.fr.puzzlegame_template;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_application);
        SharedPreferences sharedPreferences = getSharedPreferences("preferences",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        //New Game
        final Button startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editor.putInt("current_level", 0);
                editor.apply();
                editor.remove("pieces_ids");
                editor.commit();
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        //Continue
        final Button continueButton = (Button) findViewById(R.id.continue_game);
        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
        if (! sharedPreferences.contains("current_level") || sharedPreferences.getInt("current_level",0) == 0) {
            continueButton.setEnabled(false);
            continueButton.invalidate();
        }

        //Exit
        final Button exitButton = (Button) findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        //Reset
        final Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editor.putInt("current_level",0);
                editor.apply();
                editor.putInt("unlock",0);
                editor.apply();
                editor.remove("pieces_ids");
                editor.commit();

                continueButton.setEnabled(false);
                continueButton.invalidate();
            }
        });

        // Selection level
        final Button levelSelectButton = (Button) findViewById(R.id.selection_level);
        levelSelectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelChooserActivity.class);
                editor.remove("pieces_ids");
                editor.commit();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (getSharedPreferences("preferences",0).getInt("current_level", 0)>0) {
            findViewById(R.id.continue_game).setEnabled(true);
            findViewById(R.id.continue_game).invalidate();
        }
    }
}
