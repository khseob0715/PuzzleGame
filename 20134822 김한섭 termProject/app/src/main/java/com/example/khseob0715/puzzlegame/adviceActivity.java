package com.example.khseob0715.puzzlegame;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.example.khseob0715.puzzlegame.FirstActivity.ls;
import static com.example.khseob0715.puzzlegame.FirstActivity.rs;

public class adviceActivity extends AppCompatActivity {
    int adviceimg[] = {R.drawable.adviceimg1,R.drawable.adviceimg2,R.drawable.adviceimg3};
    LinearLayout linearLayout;
    int num = 0;
    boolean move = false;
    int DownX = 0, UpX = 0;
    SoundPool ad = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int soundad = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_advice);
        Toast.makeText(this,"드래그하시면 다음 페이지로 넘어갑니다!",Toast.LENGTH_SHORT).show();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));
        soundad = ad.load(this,R.raw.advicesound,1);
        linearLayout = (LinearLayout)findViewById(R.id.activity_itemboxs);

    }
    private void adplay(){
        ad.play(soundad, ls, rs, 0, 0, 1);
    }
    public boolean onTouchEvent(MotionEvent event){
        int out;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                DownX = (int)event.getX();
                out = (int)event.getY();
                if(out > 640 || out < 0) {
                    finish();   // 영역 밖을 드래그 할 경우 엑티비티 종료!
                }else adplay();
                // Log.e("out",Integer.toString(out));
                //  Toast.makeText(this,"recent "+Integer.toString(DownX),Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_MOVE:
                move = true;
                break;
            case MotionEvent.ACTION_UP:
                UpX = (int)event.getX();
                //  Toast.makeText(this,"pre "+Integer.toString(UpX),Toast.LENGTH_SHORT).show();
                break;

        }
        SystemClock.sleep(150); // 드래그 입력이 들어갈때까지의 대기 시간!
        // 없으면 양 끝만 왔다갔다함.
        if(move){
            if (DownX > UpX && num < 2) {
                num++;
                //   Toast.makeText(this,"1num"+Integer.toString(num),Toast.LENGTH_SHORT).show();
                linearLayout.setBackground(this.getResources().getDrawable(adviceimg[num]));
            }
            if (DownX < UpX && num > 0) {
                num--;
                //   Toast.makeText(this,"2num"+Integer.toString(num),Toast.LENGTH_SHORT).show();
                linearLayout.setBackground(this.getResources().getDrawable(adviceimg[num]));
            }
            move = false;
            Log.e("num",Integer.toString(num));
        }
        return false;
    }

}
