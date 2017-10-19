package com.example.khseob0715.puzzlegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.khseob0715.puzzlegame.FirstActivity.bitmap;
import static com.example.khseob0715.puzzlegame.FirstActivity.play;
import static com.example.khseob0715.puzzlegame.FirstActivity.prebitmap;
import static com.example.khseob0715.puzzlegame.FirstActivity.profileimgview;
import static com.example.khseob0715.puzzlegame.FirstActivity.rename;
import static com.example.khseob0715.puzzlegame.R.id.editText;

public class ProfileActivity extends AppCompatActivity {
    String recent;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile);


        edit = (EditText) findViewById(editText);
        prebitmap = bitmap;
        profileimgview = (ImageView)findViewById(R.id.profileimg);
        if(bitmap!=null){
            profileimgview.setImageBitmap(bitmap);
        }else {  //Toast.makeText(this,"fail",Toast.LENGTH_SHORT).show();
        }
        if(rename!=null) {
            recent = rename;
            edit.setText(rename);
        }else recent = edit.getText().toString();

        //Toast.makeText(getApplicationContext(),recent, Toast.LENGTH_LONG).show();
        // 저장되고 있는 값 확인
        Toast.makeText(getApplicationContext(),"프로필 사진을 클릭시 사진을 선택할 수 있습니다.",Toast.LENGTH_LONG).show();
    }
    public void image(View view) { // 이미지 클릭 메소드
        play();
        Intent intent = new Intent(ProfileActivity.this ,ProfileimageActivity.class);
        startActivity(intent);
    }

    public void cancelbtn(View view) { // 취소 클릭 메소드
        play();
        Intent intent = new Intent(getApplicationContext(),FirstActivity.class);
        intent.putExtra("rename",recent);
        bitmap = prebitmap;
        ProfileActivity.this.finish(); // 액티비티에서 제거
    }

    public void save(View view) { // 저장 클릭 메소드
        play();
        EditText edit = (EditText)findViewById(R.id.editText);
        rename = edit.getText().toString();
        ProfileActivity.this.finish(); // 액티비티에서 제거
    }
}
