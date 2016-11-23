package com.rupeeright.displayjokeandroidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    public final static String INTENT_JOKE = "INTENT_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        // Processing the joke intent
        String joke = getIntent().getStringExtra(INTENT_JOKE);
        TextView tvJoke = (TextView) findViewById(R.id.joke_textview);
        tvJoke.setText(joke);
    }
}
