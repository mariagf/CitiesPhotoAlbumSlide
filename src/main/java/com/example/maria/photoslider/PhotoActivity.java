package com.example.maria.photoslider;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PhotoActivity extends Activity {

    private String pictures[] = {"bee", "cat", "cow", "dog", "donkey", "duck", "elephant", "frog", "horse", "lion", "mouse", "pig", "rooster", "sheep", "snake", "tiger", "wolf"};
    private String sounds[] = {"buzz", "meow", "moo", "bark", "bray", "quack", "trumpet", "croak", "neigh", "roar", "squeak", "oink", "crow", "baa", "hiss", "growl", "howl"};
    private ImageView picture;
    private TextView text;
    private int nSec;
    private Subscription mTimer;
    private int index = 0;
    private Button setTimerButton;

    //Update pictures
    public void loadpicture(int id, CharSequence phrase) {

        Drawable drawable = ContextCompat.getDrawable(this, id);
        picture.setImageDrawable(drawable);
        text.setText(phrase);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        nSec = getIntent().getIntExtra(MainActivity.SECONDS_EXTRA, 5);
        picture = (ImageView) findViewById(R.id.picture);
        text = (TextView) findViewById(R.id.text);
        setTimerButton = (Button) findViewById(R.id.startButton);

        setTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        mTimer = Observable.interval(0, nSec, TimeUnit.SECONDS, Schedulers.io()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                final String pic = pictures[index];
                final String sound = sounds[index];
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Index ->" + (index + 1), Toast.LENGTH_LONG).show();
                        CharSequence phrase = "The " + pic + " goes " + sound + "!";
                        int imgId = getResources().getIdentifier("com.example.maria.photoslider:drawable/" + pic, null, null);
                        loadpicture(imgId, phrase);
                        index++;
                        if(index >= pictures.length){
                            index = 0;
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mTimer != null)
            mTimer.unsubscribe();
    }
}
