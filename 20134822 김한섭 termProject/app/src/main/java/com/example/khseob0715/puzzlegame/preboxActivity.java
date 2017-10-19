package com.example.khseob0715.puzzlegame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import static com.example.khseob0715.puzzlegame.FirstActivity.play;

public class preboxActivity extends AppCompatActivity {
    private Toast toast = null; // 토스트 전용 메소드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_prebox);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));
    }

    public void toss(View view) {
        play();
        toastShow("선물을 받았습니다.");
    }

    private void toastShow(String message){
        if(toast == null){
            toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        }else{
            toast.setText(message);
        }
        toast.show();
    }
}
