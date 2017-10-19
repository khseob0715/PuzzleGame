package com.example.khseob0715.puzzlegame;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.khseob0715.puzzlegame.FirstActivity.base_date;
import static com.example.khseob0715.puzzlegame.FirstActivity.bitmap;
import static com.example.khseob0715.puzzlegame.FirstActivity.data1;
import static com.example.khseob0715.puzzlegame.FirstActivity.data2;
import static com.example.khseob0715.puzzlegame.FirstActivity.db;
import static com.example.khseob0715.puzzlegame.FirstActivity.ls;
import static com.example.khseob0715.puzzlegame.FirstActivity.mp;
import static com.example.khseob0715.puzzlegame.FirstActivity.play;
import static com.example.khseob0715.puzzlegame.FirstActivity.rename;
import static com.example.khseob0715.puzzlegame.FirstActivity.rs;
import static com.example.khseob0715.puzzlegame.MainActivity.backsound_ck;
import static com.example.khseob0715.puzzlegame.MainActivity.heartcount;
import static com.example.khseob0715.puzzlegame.MainActivity.heartimg;

public class GendActivity extends AppCompatActivity{

    String Level = null;
    String score = null;
    String result = null;
    String myscore = null;
    String time = null;

    SoundPool end = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
    int sound5 = 5;
    int sound6 = 6;

    Vibrator mVibe; // 게임 종료시 진동!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gameend);

        Intent it = getIntent();
        Level = it.getStringExtra("level");
        score = it.getStringExtra("score"); // 기존 목표 점수
        result = it.getStringExtra("result");
        myscore = it.getStringExtra("myscore");
        time = it.getStringExtra("time");

        sound5 = end.load(this,R.raw.clear,1);
        sound6 = end.load(this,R.raw.fail,1);



        if(heartcount == 0){
            heartcount = 5;
            Toast.makeText(getApplicationContext(),"하트를 다섯개 모두 사용하셨어요 충전해드릴게요!",Toast.LENGTH_LONG).show();
        }

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
        if(Level.equals("사용자 지정")){
            textView3.setTextSize(42);
        }
        textView3.setText(Level); // 레벨 표시

        TextView textView4 = (TextView)findViewById(R.id.textView4);
        textView4.setText(result); // 성공 실패 여부

        ImageView textimg = (ImageView)findViewById(R.id.textview4img);

        if(result.equals("Fail!!")){
            textimg.setImageResource(R.drawable.fail);
            failplay();
        }else {
            clearplay();
            textimg.setImageResource(R.drawable.clear);
        }

        if(backsound_ck == 0) {
            SystemClock.sleep(500);
            mp.start();
        }
        TextView textView5 = (TextView)findViewById(R.id.endtextView5);
        textView5.setText(myscore); // 획득한 점수 표시

        data1 = Level + " " + myscore;
        if(rename == null)
            data2 = base_date;
        else
            data2 = rename + "     " + base_date;

    //    Toast.makeText(this,data1,Toast.LENGTH_SHORT).show();
        db.execSQL("INSERT INTO contact VALUES (null, '" + data1 + "', '" + data2 + "');");
        mVibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        mVibe.vibrate(1000);
    }

    private void clearplay() {
        // 매칭 사운드
        end.play(sound5, ls, rs, 0, 0, 1);
    }
    private void failplay() {
        // 매칭 사운드
        end.play(sound6, ls, rs, 0, 0, 1);
    }
    public void againStage(View view) {
        play();
        Intent it;
        if(Level.equals("사용자 지정")){
            it = new Intent(getApplicationContext(),GstartActivity2.class);
        }else {
            it = new Intent(getApplicationContext(), GstartActivity.class);
            it.putExtra("level",Level);
            it.putExtra("score",score);
            it.putExtra("time",time);
        }
        startActivity(it);
        GendActivity.this.finish();
    }

    public void nextStage(View view) {
        MainActivity.resetHeart();
        play();
        finish();
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
        nextStage(view);
    }


}
