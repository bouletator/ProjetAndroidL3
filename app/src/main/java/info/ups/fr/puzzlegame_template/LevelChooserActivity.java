package info.ups.fr.puzzlegame_template;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class LevelChooserActivity extends ActionBarActivity {
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
                startActivity(intent);
            }
        });
    }
}