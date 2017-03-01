package com.example.maria.photoslider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static String SECONDS_EXTRA = "seconds_extra";

    private SeekBar mPhotoSlider;
    private Button mStartButton;
    private TextView mNSeconds;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhotoSlider = (SeekBar) findViewById(R.id.photoSlider);
        mStartButton = (Button) findViewById(R.id.startButton);
        mNSeconds = (TextView) findViewById(R.id.nSeconds);

        mPhotoSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
               progress = progressValue;
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {
               String sec = Integer.toString(progress);
               mNSeconds.setText(sec);
           }

       });

        progress = mPhotoSlider.getProgress();

        mStartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhotoActivity.class);
                i.putExtra(SECONDS_EXTRA, progress);
                startActivity(i);
            }
        });
    }
}
