package info.ups.fr.puzzlegame_template;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class LevelChooserActivity extends ActionBarActivity {
    public static Bitmap puzzleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_chooser);

        //Level 1
        final Button lvl1 = (Button) findViewById(R.id.button1);
        lvl1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelChooserActivity.this, GameActivity.class);
                puzzleImage = BitmapFactory.decodeResource(getResources(), R.drawable.fusee);

                startActivity(intent);
            }
        });

        //Level 2
        final Button lvl2 = (Button) findViewById(R.id.button2);
        lvl2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelChooserActivity.this, GameActivity.class);
                puzzleImage = BitmapFactory.decodeResource(getResources(), R.drawable.chat);

                startActivity(intent);
            }
        });

        //level 3
        final Button lvl3 = (Button) findViewById(R.id.button3);
        lvl3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelChooserActivity.this, GameActivity.class);
                puzzleImage = BitmapFactory.decodeResource(getResources(), R.drawable.aigle);

                startActivity(intent);
            }
        });

        //level 4
        final Button lvl4 = (Button) findViewById(R.id.button4);
        lvl4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelChooserActivity.this, GameActivity.class);
                puzzleImage = BitmapFactory.decodeResource(getResources(), R.drawable.plancton);
                startActivity(intent);
            }
        });

        //level 5
        final Button lvl5 = (Button) findViewById(R.id.button5);
        lvl5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelChooserActivity.this, GameActivity.class);
                puzzleImage = BitmapFactory.decodeResource(getResources(), R.drawable.coquelicot);
                startActivity(intent);
            }
        });


    }
}