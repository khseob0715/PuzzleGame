package com.example.khseob0715.puzzlegame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.khseob0715.puzzlegame.FirstActivity.friendname;
import static com.example.khseob0715.puzzlegame.FirstActivity.play;

public class friboxActivity extends AppCompatActivity {
    Integer[] textid = {R.id.friendname1,R.id.friendname2,R.id.friendname3,R.id.friendname4,
            R.id.friendname5,R.id.friendname6,R.id.friendname7,R.id.friendname8,R.id.friendname9,
            R.id.friendname10,R.id.friendname11,R.id.friendname12,R.id.friendname13,R.id.friendname14,
            R.id.friendname15,R.id.friendname16,R.id.friendname17,R.id.friendname18,R.id.friendname19,
            R.id.friendname20,R.id.friendname21,R.id.friendname22,R.id.friendname23,R.id.friendname24,
            R.id.friendname25,R.id.friendname26,R.id.friendname27,R.id.friendname28,R.id.friendname29,
            R.id.friendname30,R.id.friendname31,R.id.friendname32,R.id.friendname33,R.id.friendname34,
            R.id.friendname35,R.id.friendname36,R.id.friendname37,R.id.friendname38,R.id.friendname39,
            R.id.friendname40,R.id.friendname41,R.id.friendname42,R.id.friendname43,R.id.friendname44,
            R.id.friendname45,R.id.friendname46,R.id.friendname47,R.id.friendname48,R.id.friendname49,
            R.id.friendname50};

    int ct = 0;
    private Toast toast = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fribox);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .5));

        while(ct < 50) {
            TextView textView = (TextView) findViewById(textid[ct]);
            textView.setText(friendname[ct]);
            ct++;
        }

    }

    public void message(View view) {
        play();
        toastShow("친구에게 하트를 보냈습니다");
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