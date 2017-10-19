package com.example.khseob0715.puzzlegame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import static com.example.khseob0715.puzzlegame.FirstActivity.ls;
import static com.example.khseob0715.puzzlegame.FirstActivity.mp;
import static com.example.khseob0715.puzzlegame.FirstActivity.play;
import static com.example.khseob0715.puzzlegame.FirstActivity.pls;
import static com.example.khseob0715.puzzlegame.FirstActivity.prs;
import static com.example.khseob0715.puzzlegame.FirstActivity.rs;
import static com.example.khseob0715.puzzlegame.GameActivity.gmp;
import static com.example.khseob0715.puzzlegame.MainActivity.backsound_ck;
import static com.example.khseob0715.puzzlegame.MainActivity.music_sound;
import static com.example.khseob0715.puzzlegame.MainActivity.sound_pool;
import static com.example.khseob0715.puzzlegame.R.id.seekBar;

public class setboxActivity extends AppCompatActivity {
    ImageView backsound,soundpool;
    SeekBar Seekbar1, Seekbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setbox);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.5));


        backsound = (ImageView)findViewById(R.id.backsound);
        soundpool = (ImageView)findViewById(R.id.soundpool);

        if(!mp.isPlaying() || (pls == 0.0))
            backsound.setImageResource(R.drawable.unsoundimg);

        if(ls == 0.0f && rs == 0.0f)
            soundpool.setImageResource(R.drawable.unsoundimg);

        Seekbar1 = (SeekBar)findViewById(seekBar);   // 배경음
        Seekbar2 = (SeekBar)findViewById(R.id.seekBar2);  // 효과음

        // 초기값 지정   // 값 수정후 엑티비티 재 호출하면 원래대로 돌아가는 것을 방지하기 위해 전역으로 선언
        Seekbar1.setProgress(music_sound);
        Seekbar2.setProgress(sound_pool);
        // 배경음
        Seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 탐색바의 값이 변할때
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 탐색바의 값이 변하기 시작할때
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                music_sound = Seekbar1.getProgress();  // 반환값이 int
                // 탐색바의 값의 변화가 끝났을때
                pls = (float)music_sound/100;
                prs = (float)music_sound/100;
                mp.setVolume(pls,prs);
                gmp.setVolume(pls,prs);
                if(!mp.isPlaying() || (pls == 0.0)){
                    backsound.setImageResource(R.drawable.unsoundimg);
                }else{
                    backsound.setImageResource(R.drawable.soundimg1);
                }
            }
        });
        // 효과음
        Seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sound_pool = Seekbar2.getProgress();  // 반환값이 int
                //Log.e("sound_pool",Integer.toString(sound_pool));
                ls = (float)sound_pool/100;
                rs = (float)sound_pool/100;
                Log.e("sound_pool",""+ls);
                if(ls == 0.0f && rs == 0.0f){
                    soundpool.setImageResource(R.drawable.unsoundimg);
                }else{
                    soundpool.setImageResource(R.drawable.soundimg2);
                }
            }
        });
    }

    public void backsound(View view) { // 배경음 조절
        play(); // 효과음
        if(mp.isPlaying()){  // 재생되는 중이면 끝다!!
            backsound_ck = -1;
            mp.pause();
            backsound.setImageResource(R.drawable.unsoundimg);
        }else{
            backsound_ck = 0;
            mp.start();
            backsound.setImageResource(R.drawable.soundimg1);
        }
    }

    public void soundpool(View view) { // 효과음 조절
        if(ls == 0.0f && rs == 0.0f){ // 소리가 꺼져있으면 킨다!!
            sound_pool = Seekbar2.getProgress();  // 반환값이 int
            //Log.e("sound_pool",Integer.toString(sound_pool));
            ls = (float)sound_pool/100;
            rs = (float)sound_pool/100;
            Seekbar2.setProgress(sound_pool);
            soundpool.setImageResource(R.drawable.soundimg2);
        }else { // 소리가 켜져있으면 끈다.
            ls = 0.0f;
            rs = 0.0f;
            soundpool.setImageResource(R.drawable.unsoundimg);
        }
        play(); // 효과음
    }

    public void advicebtn(View view) {
        play();
        Intent advice = new Intent(this, adviceActivity.class);
        startActivity(advice);
    }

    public void profile(View view) {
        play();
        Intent profile = new Intent(this, ProfileActivity.class);
        startActivity(profile);
    }

    public void tell(View view) {
        Toast.makeText(this,"개발자 버전입니다.",Toast.LENGTH_SHORT).show();
        return;
      // play();
      //  Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-9180-0657"));
      //  startActivity(myIntent);
    }

    public void link(View view) {
        play();
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www2.chosun.ac.kr"));
        startActivity(myIntent);
    }

    public void basicsound(View view) {  // 기본 음향으로 돌리기.
        music_sound = 50;
        sound_pool = 30;
        Seekbar1.setProgress(music_sound);
        Seekbar2.setProgress(sound_pool);
        backsound.setImageResource(R.drawable.soundimg1);
        soundpool.setImageResource(R.drawable.soundimg2);
        ls = 0.3f;
        rs = 0.3f;
        pls = 0.5f;
        prs = 0.5f;
        mp.setVolume(pls,prs);
        gmp.setVolume(pls,prs);
        mp.start();
        play();
    }

    public void rank(View view) {
        play();
        Intent rank = new Intent(getApplicationContext(),RankingActivity.class);
        startActivity(rank);
    }
}
