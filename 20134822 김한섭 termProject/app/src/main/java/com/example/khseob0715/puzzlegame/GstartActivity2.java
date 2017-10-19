package com.example.khseob0715.puzzlegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import static com.example.khseob0715.puzzlegame.FirstActivity.bitmap;
import static com.example.khseob0715.puzzlegame.FirstActivity.play;
import static com.example.khseob0715.puzzlegame.FirstActivity.rename;
import static com.example.khseob0715.puzzlegame.MainActivity.heartcount;
import static com.example.khseob0715.puzzlegame.MainActivity.heartimg;

public class GstartActivity2 extends AppCompatActivity {
    String Level = "사용자 지정", score = "6000", time = "30000";
    String stone = "0";
    String timelist[] = {"30000","40000","50000","60000"};
    String scorelist[] = {"6000","12000","24000","40000"};
    String stonelist[] = {"0","3","7","9"};

    // 내부 클래스에서 참조함으로 final로 선언이 되어야 함
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gamestart2);
        /*  // 이 단계는 넘겨 받는 데이터 값 없음. // 대신 넘겨야 하는 값은 있음.
        Intent intent = getIntent();
        Level = intent.getStringExtra("level");
        score = intent.getStringExtra("score");
        time = intent.getStringExtra("time");
        */

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

        TabHost tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 =
                ArrayAdapter.createFromResource(this, R.array.score_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter3 =
                ArrayAdapter.createFromResource(this, R.array.stone_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_time);
        spinner.setAdapter(adapter);

        TabHost.TabSpec tab1=tabHost.newTabSpec("tab1").setContent(R.id.tab1).setIndicator("제한시간");
        tabHost.addTab(tab1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time = timelist[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_score);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                score = scorelist[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
        TabHost.TabSpec tab2=tabHost.newTabSpec("tab2").setContent(R.id.tab2).setIndicator("목표점수");
        tabHost.addTab(tab2);

        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_stone);
        spinner3.setAdapter(adapter3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stone = stonelist[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        TabHost.TabSpec tab3=tabHost.newTabSpec("tab3").setContent(R.id.tab3).setIndicator("돌맹이수");
        tabHost.addTab(tab3);

    }



    // 아래는 버튼 이벤트들
    public void startgame(View view) {
        play();
        Intent game = new Intent(getApplicationContext(),GameActivity.class);
        game.putExtra("level",Level);
        game.putExtra("score",score);
        game.putExtra("time",time);
        game.putExtra("stone",stone);
        startActivity(game);
        GstartActivity2.this.finish();
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
