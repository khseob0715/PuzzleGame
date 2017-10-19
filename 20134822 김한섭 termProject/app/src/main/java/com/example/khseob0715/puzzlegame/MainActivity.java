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

public class MainActivity extends AppCompatActivity {

    static ImageView heart;
    static int heartimg[] = {R.drawable.new_heart1,R.drawable.new_heart2,R.drawable.new_heart3,R.drawable.new_heart4,R.drawable.new_heart5};
    static int heartcount = 5;
    static int backsound_ck = 0;   // 설정에서 배경음을 지우면 -1이 된다!
    static int music_sound = 50, sound_pool = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        TextView nametext = (TextView) findViewById(R.id.nametext);
        if(rename != null) {// 별명
            nametext.setText(rename);
        }else {
            nametext.setVisibility(View.INVISIBLE);
        }
        if(bitmap != null) { // 프로필
            ImageView mainprofile = (ImageView) findViewById(R.id.mainprofil);
            mainprofile.setImageBitmap(bitmap);
        }
        heart = (ImageView)findViewById(R.id.heartUI);// 하트 UI
        heart.setImageResource(heartimg[heartcount-1]);

    }
    static public void resetHeart(){
        // 다른 엑티비티 종료후 Main ACtivity로 돌아왔을 경우 하트 변경! // 다른 액티비티에서 호출을 함.
        heart.setImageResource(heartimg[heartcount-1]);
    }


    public void game1(View view) {
        play();
        Intent intent = new Intent(MainActivity.this, GstartActivity.class);
        intent.putExtra("level", "Level 1");
        intent.putExtra("score", "6000");
        intent.putExtra("time","30000");
        startActivity(intent);
    }

    public void game2(View view) {
        play();
        Intent intent = new Intent(MainActivity.this, GstartActivity.class);
        intent.putExtra("level", "Level 2");
        intent.putExtra("score", "12000");
        intent.putExtra("time","30000");
        startActivity(intent);
    }


    public void game3(View view) {
        play();
        Intent intent = new Intent(MainActivity.this, GstartActivity.class);
        intent.putExtra("level", "Level 3");
        intent.putExtra("score", "24000");
        intent.putExtra("time","40000");
        startActivity(intent);
    }

    public void game4(View view) {
        play();
        Intent intent = new Intent(MainActivity.this, GstartActivity.class);
        intent.putExtra("level", "Level 4");
        intent.putExtra("score", "36000");
        intent.putExtra("time","50000");
        startActivity(intent);
    }

    public void game5(View view) {
        play();
        Intent intent = new Intent(MainActivity.this, GstartActivity.class);
        intent.putExtra("level", "Level 5");
        intent.putExtra("score", "50000");
        intent.putExtra("time","60000");
        startActivity(intent);
    }


    public void gameselect(View view) {  // spinner로 게임 난이도 직접 설정
        play();
        Intent intent = new Intent(MainActivity.this, GstartActivity2.class);
        startActivity(intent);
    }
    public void itemBox(View view) {
        play();
        Intent itemintent = new Intent(MainActivity.this, ItemboxActivity.class);
        startActivity(itemintent);
    }

    public void friBox(View view) {
        play();
        Intent itemintent = new Intent(MainActivity.this, friboxActivity.class);
        startActivity(itemintent);
    }

    public void preBox(View view) {
        play();
        Intent itemintent = new Intent(MainActivity.this, preboxActivity.class);
        startActivity(itemintent);
    }

    public void setting(View view) {
        play();
        Intent itemintent = new Intent(MainActivity.this, setboxActivity.class);
        startActivity(itemintent);
    }


}
