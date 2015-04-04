package info.ups.fr.puzzlegame_template;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameWinActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);


        final SharedPreferences sharedPreferences = getSharedPreferences("preferences",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final int lastLevel = sharedPreferences.getInt("current_level",0);

        int idImage = 0;
        switch ((lastLevel-1)%5){
            case 0 : idImage = R.drawable.plancton; break;
            case 1 : idImage = R.drawable.fusee; break;
            case 2 : idImage = R.drawable.chat; break;
            case 3 : idImage = R.drawable.aigle; break;
            case 4 : idImage = R.drawable.coquelicot; break;

        }
        ((TextView)findViewById(R.id.win_text)).setText("You win the level "+lastLevel);
        findViewById(R.id.final_image).setBackgroundResource(idImage);
        //Next level
        final Button nextLevelButton = (Button) findViewById(R.id.next_level_button);
        nextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameWinActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        //Redo
        final Button redoButton = (Button) findViewById(R.id.redo_button);
        redoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editor.putInt("current_level", lastLevel-1);
                editor.apply();
                editor.remove("pieces_ids");
                editor.commit();
                Intent intent = new Intent(GameWinActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        //Back to menu
        final Button backButton = (Button) findViewById(R.id.menu_button);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameWinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
