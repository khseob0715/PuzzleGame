package com.example.khseob0715.puzzlegame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static com.example.khseob0715.puzzlegame.FirstActivity.bitmap;
import static com.example.khseob0715.puzzlegame.FirstActivity.play;
import static com.example.khseob0715.puzzlegame.FirstActivity.profileimgview;
import static com.example.khseob0715.puzzlegame.FirstActivity.uri;


public class ProfileimageActivity extends AppCompatActivity {
    private static int PICK_IMAGE_REQUEST = 1;
    // http://cosmosjs.blog.me/220940841567 갤러리 업로드
    ImageView imgView;

    // 카메라 기능
    static final int REQUEST_IMAGE_CAPTURE = 1;


    Button btn_takePicture;
    Uri photoURI = null;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profileimage);

        // 액티비티 크기 결정
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .6));
        // 액티비티 크기 소스 끝.

        // 카메라
        btn_takePicture = (Button)findViewById(R.id.button2);


        imgView = (ImageView) findViewById(R.id.imageView);
        if (bitmap != null)
            imgView.setImageBitmap(bitmap);
        else {
            imgView.setImageResource(R.drawable.profil);
        }
    }

    // 사진 찍기.
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            try{
                photoFile = createImageFile(); // 사진을 찍은 뒤 저장할 임시 파일
                loadImageFromGallery(imgView);     // 찍은 후에 갤러리를 불러와서 선택하면 THE.END
            }catch (IOException e){
                Log.e("createFile","fail");
            }
            if(photoFile !=null){
                photoURI = Uri.fromFile(photoFile); // 임시 파일의 위치, 경로를 가져온다.
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI); //  임시파일 위치에 저장
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    // 저장할 폴더 생성
    private File createImageFile() throws IOException{
        // 특정한 경로와 폴더 설정 X , 메모리 최상위 저장!
        String imageFileName = "tmp_"+String.valueOf(System.currentTimeMillis()) + ".jpg";
        File storageDir = new File(Environment.getExternalStorageDirectory(), imageFileName);
        mCurrentPhotoPath = storageDir.getAbsolutePath();
        return storageDir;
    }

    public void loadImageFromGallery(View view) {
        play();
        Log.e("s","aa");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); //이미지만 보이게
        //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
        Log.e("s","aaaa");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                uri = data.getData(); // 이미지 저장
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                imgView.setImageBitmap(bitmap);
                profileimgview.setImageBitmap(bitmap);  // ProfileActivity의 ImageView Resource 저장
            } else {
               // Toast.makeText(this, "취소됨", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "로딩 오류", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void basic(View view) {
        play();
        imgView.setImageResource(R.drawable.profil);
        profileimgview.setImageResource(R.drawable.profil);
        bitmap = null;
    }

    public void camera(View view) {
        dispatchTakePictureIntent();
    }
}
