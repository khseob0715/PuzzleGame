package com.example.khseob0715.puzzlegame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FirstActivity extends AppCompatActivity {
    static String DATABASE_NAME = "puzzledn.db";
    static SQLiteDatabase db;
    static String data1, data2;

    static MediaPlayer mp = new MediaPlayer();
    static SoundPool ms = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
    static int sound1;
    static float ls=0.3f, rs=0.3f;          // 효과음 크기
    static float pls = 0.5f, prs = 0.5f;    // 배경음 크기
    static String rename;                   // 별명저장
    static Uri uri;
    static Bitmap bitmap,prebitmap;             // profileimageActivity
    static ImageView profileimgview;  // profileActivity
    static String base_date;
    TextView tv;

    int ct = 0;        // 연락처 사람수

    static String[] friendname = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first);


        // sound
        mp = MediaPlayer.create(this,R.raw.mainmusic);
        mp.setVolume(pls,prs);
        mp.setLooping(true);
        mp.start();

        // 효과음
        sound1 = ms.load(this,R.raw.clicksound,1);

        //API
        SimpleDateFormat sdt = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();

        String serviceUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData";
        String serviceKey = "Ieby7EZIfklVeAIjd2fpaZnG6O0JQXa27QUlKMTAjvIxqFVI9KqTdzxJnP8c9X136DaoKNOr%2BJbb56R%2BdjJUXg%3D%3D";
        String test = "TEST_SERVICE_KEY";
        base_date =sdt.format(cal.getTime());
        //Toast.makeText(this,String.valueOf(base_date),Toast.LENGTH_SHORT).show();
        //Integer.toString(cal.get(Calendar.YEAR))+Integer.toString((cal.get(Calendar.MONTH)+1))+Integer.toString(cal.get(Calendar.DATE));
        String base_time = "0500";
        String nx = "60";
        String ny = "74";
        String strUrl = serviceUrl + "?ServiceKey=" + serviceKey + "&ServiceKey=TEST_SERVICE_KEY" + "&base_date=" + base_date + "&base_time=" + base_time + "&nx=" + nx + "&ny="+ny
                +"&numOfRows=1&pageNo=1&_type=xml";

        new DownloadWebpageTask().execute(strUrl);
        tv = (TextView)findViewById(R.id.weather);
        // LoadContacts();    // 연락처 가져오기.

        db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE if not exists contact ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "data1 TEXT, data2 TEXT);");
    }
    @Override
    protected void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }
    static public void play(){  // 기본 클릭 효과음
        ms.play(sound1,ls,rs,0,0,1);
        // 2,3 번째가 소리 크기
        // 뒤에서 두번째가 반복 여부 이며 -1은 무한반복 0은 반복 X
    }

    public void gotoMain(View view) {
        play();   // 클릭 효과음
        Intent mainlayout = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mainlayout);
    }

    public void profilebtn(View view) {
        play();
        Intent profileintent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileintent.putExtra("rename",rename); // 이전에 설정한 별칭 전달
        startActivity(profileintent);
    }
    private class DownloadWebpageTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                return (String) downloadUrl((String) urls[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            String category = "";
            String fcstvalue;

            boolean bSet_sky = false;

            boolean bSet_category = false;
            boolean bSet_fcstvalue = false;

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                Log.e("e","1");
                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        ;
                    } else if (eventType == XmlPullParser.START_TAG) {
                        // 찾는 태그와 같은 태그가 있는지!

                        String tag_name = xpp.getName();
                        if(tag_name.equals("category"))
                            bSet_category = true;
                        if(tag_name.equals("fcstValue")) {
                            bSet_fcstvalue = true;
                            Log.e("fcstvalue","true");
                        }

                    } else if (eventType == XmlPullParser.TEXT) {
                        // 찾은 태그값에 해당하는 내용 가져오기!
                        if(bSet_category){

                            category = xpp.getText();
                            if(category.equals("SKY")) bSet_sky = true;
                            else bSet_sky = false;
                        }

                        if(bSet_fcstvalue) {
                            fcstvalue = xpp.getText();
                            int value = Integer.valueOf(fcstvalue);
                            Log.e("값", fcstvalue);
                            switch (value % 4 + 1) {
                                case 1:
                                    tv.append("오늘 날씨는 맑음! 희망찬 하루를 보내세요 >,<");
                                    break;
                                case 2:
                                    tv.append("오늘은 구름이 조금있는 날씨에요 선선할 것 같아요!!");
                                    break;
                                case 3:
                                    tv.append("오늘은 평소와 달리 구름이 많이 보이는 날씨네요!");
                                    break;
                                case 4:
                                    tv.append("오늘은 많이 어둡네요. 비가 올지도 모르겠어요!");
                                    break;
                            }
                            break;
                        }

                    } else if (eventType == XmlPullParser.END_TAG) {
                        ;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {

            }
        }

        private String downloadUrl(String myurl) throws IOException {

            HttpURLConnection conn = null;
            try {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                }

                return page;
            } finally {
                conn.disconnect();
            }
        }
    }

    private void LoadContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER };

        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(2) == 1)
                    LoadPhoneNumbers(cursor.getString(0));
            }

        }

    }
    private void LoadPhoneNumbers(String id) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        Cursor cursor = managedQuery(uri, projection, selection, new String[] {id}, null);

        while (cursor.moveToNext() && ct < 100) {
            // Log.e("TAG", "Number Type ==> " + cursor.getString(0));
            // Log.e("TAG", "Number ==> " + cursor.getString(1));
            if(cursor.getString(2).charAt(1)=='1' && cursor.getString(1).length() == 3) {
                // 휴대폰 그리고 이름이 세글자인 사람만 불러옴.
                friendname[ct++] = cursor.getString(1);
            }
        }
    }
}
