package com.example.khseob0715.puzzlegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.khseob0715.puzzlegame.FirstActivity.bitmap;
import static com.example.khseob0715.puzzlegame.FirstActivity.play;
import static com.example.khseob0715.puzzlegame.FirstActivity.rename;
import static com.example.khseob0715.puzzlegame.MainActivity.heartcount;
import static com.example.khseob0715.puzzlegame.MainActivity.heartimg;

public class GstartActivity extends AppCompatActivity {
    String Level,score,time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gamestart);

        Intent intent = getIntent();
        Level = intent.getStringExtra("level");
        score = intent.getStringExtra("score");
        time = intent.getStringExtra("time");

        // 별명
        TextView nametext = (TextView) findViewById(R.id.nametext);
        if(rename != null) {
            nametext.setText(rename);
        }else{
            nametext.setVisibility(View.INVISIBLE);
        }
        // 프로필
        if(bitmap != null) {
            ImageView mainprofile = (ImageView) findViewById(R.id.mainprofil);
            mainprofile.setImageBitmap(bitmap);
        }

        ImageView heart = (ImageView)findViewById(R.id.heartUI);
        heart.setImageResource(heartimg[heartcount-1]);

        TextView textView3 = (TextView)findViewById(R.id.textView3);
        textView3.setText(Level);

        TextView textView5 = (TextView)findViewById(R.id.textView5);
        textView5.setText(score);

    }

    public void startgame(View view) {
        play();
        Intent game = new Intent(getApplicationContext(),GameActivity.class);
        game.putExtra("level",Level);
        game.putExtra("score",score);
        game.putExtra("time",time);
        startActivity(game);
        GstartActivity.this.finish();
    }

    public void itemBox(View view) {
        play();
        Intent itemintent = new Intent(getApplicationContext(), ItemboxActivity.class);
        startActivity(itemintent);
    }

    public void friBox(View view) {
        play();
        Intent itemintent = new Intent(getApplicationContext(), friboxActivity.class);
        startActivity(itemintent);
    }

    public void preBox(View view) {
        play();
        Intent itemintent = new Intent(getApplicationContext(), preboxActivity.class);
        startActivity(itemintent);
    }

    public void setting(View view) {
       play();
        Intent itemintent = new Intent(getApplicationContext(), setboxActivity.class);
        startActivity(itemintent);
    }

    public void xbutton(View view) {
        play();
        MainActivity.resetHeart();
        finish();
    }

}
