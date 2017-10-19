package com.example.khseob0715.puzzlegame;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.example.khseob0715.puzzlegame.FirstActivity.ls;
import static com.example.khseob0715.puzzlegame.FirstActivity.mp;
import static com.example.khseob0715.puzzlegame.FirstActivity.pls;
import static com.example.khseob0715.puzzlegame.FirstActivity.prs;
import static com.example.khseob0715.puzzlegame.FirstActivity.rs;
import static com.example.khseob0715.puzzlegame.MainActivity.backsound_ck;
import static com.example.khseob0715.puzzlegame.MainActivity.heartcount;

public class GameActivity extends AppCompatActivity {
    String Level = null;
    String score = null;
    String time = null;
    int itemct1 = 0, itemct2 = 0, itemct3 = 0, itemct4 = 0; // 각각의 아이템은 두번만 쓸수 있음.
    static MediaPlayer gmp = new MediaPlayer();

    SoundPool gms = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
    int sound2 = 2;
    int sound3 = 3;
    int sound4 = 4;
    int soundgo = 9;

    boolean crossbtn = false;       // 크로스 아이템 사용을 눌렀는지 아닌지
    boolean getTouch = false;       // 아이콘이 이미 터치 되어있는 상태인지 아닌지
    int pscore = 0;     // 현재 점수.  -> myscore로 값 전달 후 myscore를 GendActivity에 전달할 예정.

    int goalscore = 0;  // 목표 점수
    int timelimit = 0;  // 제한 시간
    int timelimitDown;
    int reset_timer = 15000;
    float progressbar_num;
    int addtime = 0;

    int saveimg = 0;
    int ra = 0,rb = 0; // 현재 클릭된 아이콘을 기억.
    int rx = 0,ry = 0; // 이전에 클릭된 아이콘을 기억.

    int stonenum = 0; // 벽돌 갯수  4단계 4개 5단계 9개

    TextView goaltext;  // 점수 표시 textview
    TextView timerText;

    Handler handler;            // 게임 진행 중 발생하는 핸들러
    Handler GameEnd;            // 게임의 최종 종료 핸들러
    ProgressBar progressBar;    // 시간 프로그레스 바
    ImageView icon[][] = new ImageView[8][6];
    int[][] record = new int[8][6];
    // icon 5개의 이름 저장 + 선택되었을때의 이미지 5개

    // 아이콘 이미지 저장
    int[] img = {R.drawable.icon6, R.drawable.icon7, R.drawable.icon8, R.drawable.icon9,
            R.drawable.icon10, R.drawable.icon1, R.drawable.icon2, R.drawable.icon3,
            R.drawable.icon4, R.drawable.icon5};

    // 아이템 아이콘 다크 버전
    int[] darkicon = {R.drawable.darkicon1,R.drawable.darkicon2,R.drawable.darkicon3,R.drawable.darkicon4};


    // 격자 ImageView의 ID
    Integer imageId[] = {R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5,
            R.id.img6, R.id.img7, R.id.img8, R.id.img9, R.id.img10,
            R.id.img11, R.id.img12, R.id.img13, R.id.img14, R.id.img15,
            R.id.img16, R.id.img17, R.id.img18, R.id.img19, R.id.img20,
            R.id.img21, R.id.img22, R.id.img23, R.id.img24, R.id.img25,
            R.id.img26, R.id.img27, R.id.img28, R.id.img29, R.id.img30,
            R.id.img31, R.id.img32, R.id.img33, R.id.img34, R.id.img35,
            R.id.img36, R.id.img37, R.id.img38, R.id.img39, R.id.img40,
            R.id.img41, R.id.img42, R.id.img43, R.id.img44, R.id.img45,
            R.id.img46, R.id.img47, R.id.img48};

    private Toast toast = null;
    private Toast toastimg = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);

        heartcount--; // 게임을 했으니 하트 수 감소!

        mp.pause(); // 전체적인 앱의 배경음 멈춤

        // sound pool 등록.
        sound2 = gms.load(this, R.raw.matchsound, 1);
        sound3 = gms.load(this, R.raw.iconclicksound, 1);
        sound4 = gms.load(this, R.raw.iconaddsound, 1);
        soundgo = gms.load(this, R.raw.gosound, 1);

        gmp = MediaPlayer.create(this, R.raw.gamemusic);
        gmp.setVolume(pls,prs);
        gmp.setLooping(true); // 시간이 무한대로 추가될수 있으니. 음악은 계속 반복되어야 함.
        if(backsound_ck == 0)
            gmp.start();

        Intent it = getIntent();
        Level = it.getStringExtra("level"); // level에 저장되어있는 문자열을 가져와서 Level에 저장함.
        score = it.getStringExtra("score"); // 목표 점수.
        time = it.getStringExtra("time");

        goalscore = Integer.valueOf(score); // 목표 점수 정수 변환
        timelimit = Integer.valueOf(time);  // 제한 시간을 받음.

        // textview6, Level에 값을 지정
        TextView leveltext = (TextView) findViewById(R.id.textView6);
        if(Level.equals("사용자 지정")){
            leveltext.setText("사용자");
        }else {
            leveltext.setText(Level);
        }

        if(Level.equals("Level 4")){   // 4개 이하
            stonenum = 4;
        }else if(Level.equals("Level 5")){
            stonenum = 9;  // 9개이하의 돌땡이가 나타난다!!
        }else if(Level.equals("사용자 지정")){
            stonenum = Integer.valueOf(it.getStringExtra("stone"));
        }

        goaltext = (TextView) findViewById(R.id.scoretext);
        goaltext.setText(Integer.toString(pscore));

        // 아이콘 배치
        int image_c = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                icon[i][j] = (ImageView) findViewById(imageId[image_c++]);
                Type(i, j, -1, -1);
                if (record[i][j] == -2) {  // 이거는 돌땡이!!
                    icon[i][j].setImageResource(R.drawable.stone);
                } else {
                    icon[i][j].setImageResource(img[record[i][j]]);
                }
            }
        }
        stonenum = 0; // 돌땡이 개수는 처음에 나온걸로 고정!
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(100);
        // 게임 제한 시간 표시.
        timelimitDown = timelimit / 1000;   // 천 단위를 1단위로 바꿈!!
        timerText = (TextView) findViewById(R.id.timertext);
        timerText.setText(Integer.toString(timelimitDown));

        progressbar_num = timelimitDown;

        // ready->go! image toast
        View ready = View.inflate(GameActivity.this, R.layout.toast_layout2,null);
        Toast toast = new Toast(GameActivity.this);
        toast.setView(ready);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

        goplay();   // go sound

        // 게임 제한 시간 지정
        handler = new Handler();
        GameEnd = new Handler();
        handler.postDelayed(new CDown(), 1000);

        handler.postDelayed(new reset(), reset_timer);
        GameEnd.postDelayed(new closed(), timelimit+1000); // 게임 시간! 1000ms -> 1s
    }
    private void play() {
        // 매칭 사운드
        gms.play(sound2, ls, rs, 0, 0, 1);
    }

    private void play2() {
        // 클릭 사운드
        gms.play(sound3, ls, rs, 0, 0, 1);
    }
    private void play3() {
        // 추가 사운드  // 4번째 인자는 우선순위 // 다른 soundpool 보다 우선순위가 높음!ㅍ
        gms.play(sound4, ls, rs, 1, 0, 1);
    }
    private void goplay() {
        // go! 사운드
        gms.play(soundgo, ls, rs, 0, 0, 1);
    }
    @Override
    protected void onDestroy() {
        gmp.stop();
        gmp.release();
        super.onDestroy();
    }

    // 아이콘 결정.
    public void Type(int i, int j, int top, int left){
        Random rd = new Random();
        if(i >= 1)
            top = record[i-1][j];
        if(j >= 1)
            left = record[i][j-1];
        int recent = -1;
        while(true) { // 한번에 한 아이콘만 지정함.
            int sType = rd.nextInt()*100%5;
            if(sType<0) {
                if(sType == -2 && stonenum != 0){
                    record[i][j] = -2;
                    stonenum--;
                    return;
                }
                sType = -sType;
            }
            if(recent == sType){
                sType++;
                if(sType == 5)
                    sType=0;
            }
            //Log.e("TAG",String.valueOf(sType));
            if(sType != top && sType != left){
                record[i][j] = sType;
                return;
            }
            recent = sType;
        }
    }

    // 같은 아이콘이 나열되어있는지 확인
    public void group(int top, int bottom, int left, int right) {
        int check,Hstart,Vstart,Hct,Vct;
        for(int i = top ; i <= bottom ; i++) {
            for(int j = left ;j <= right; j++) {
                if(i >= 0 && i < 8 && j >= 0 && j < 6) {
                    Hct = 0; Vct = 0;
                    check = record[i][j];
                    Vstart = i - 2;  // 세로
                    for (int k = 0; k < 5; k++, Vstart++) {
                        if (Vstart >= 0 && Vstart <= 7) {
                            if (check == record[Vstart][j]) {
                                Vct++;
                            } else break; // 다른게 나오면 그냥 종료.
                        } else break;
                    }
                    Hstart = j - 2; // j-2가 시작임.// 가로
                    for (int k = 0; k < 5; k++, Hstart++) {
                        if (Hstart >= 0 && Hstart <= 5) {
                            if (check == record[i][Hstart]) {
                                Hct++;
                            } else break; // 다른게 나오면 그냥 종료.
                        } else break;
                    }
                    if (Vct >= 3) {
                        int s = i - 2;
                        pscore = pscore + (Vct * 100);
                        if(check != -1)
                            play();
                        for (int k = 0; k < Vct; k++) {
                            record[s][j] = -1;
                            icon[s++][j].setVisibility(View.INVISIBLE);
                        }
                    }
                    if (Hct >= 3) {
                        int s = j - 2;
                        pscore = pscore + (Hct * 100);
                        if(check != -1)
                            play();
                        for (int k = 0; k < Hct; k++) {
                            record[i][s] = -1;
                            icon[i][s++].setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }// 아이콘 하나씩 비교하는 반복문의 끝.
        }
        goaltext.setText(Integer.toString(pscore));
    }

    public void randomfive(View view) { // 랜덤하게 최대 7개 없애기 아이템.
        play2();
        if(itemct4 == 2){
            toastShow("이미 2회 사용하셨습니다");
            return;
        }
        Random rdfive = new Random();
        int rdct = 0;
        int rdfail = 0;
        if(pscore == 0) {
            toastShow("점수 부족");
            return;
        }

        itemct4++;      // 아이템을 사용했으니 카운트 증가.
        pscore -= 700;  // 일단 700점 감점.
        while(rdct != 7){
            int rdx = rdfive.nextInt(100)%8;
            if(rdx<0)
                rdx = -rdx;
            int rdy = rdfive.nextInt(50)%6;
            if(rdy < 0)
                rdy = -rdy;

            if(record[rdx][rdy] != -1 &&  record[rdx][rdy] != -2){
                record[rdx][rdy] = -1;
                icon[rdx][rdy].setVisibility(View.INVISIBLE);
                pscore += 200;
                rdfail++;
            } rdct++;
            if(rdfail == 0){
                toastShow("꽝");
            }else{
                toastShow("랜덤 사용!");
            }
        }
        if(pscore < 0)
            pscore = 0;

        goaltext.setText(Integer.toString(pscore));
        if(itemct4 == 2){  // 두번 이상 사용하였으면, 아이템을 감춰서 안 보이게 함.
            ImageView itemicon4 = (ImageView)findViewById(R.id.itemimg4);
            itemicon4.setImageResource(darkicon[3]);
        }

    }
    public void plustime(View view){ // 시간 추가 아이템  2
        play2();
        if(itemct2 == 2){
            toastShow("이미 2회 사용하셨습니다");
            return;
        }
        if(progressbar_num < timelimitDown + 5) {
            toastShow("시간제한");
            return;
        }
        if(pscore < 1000) {
            toastShow("점수부족");
            return;
        }
        toastShow("5초 추가");
        itemct2++;
        timelimit += 5000;
        timelimitDown += 5;
        addtime += 5000; // closed 함수를 늦게 실행하기 위한 값.
        pscore -= 1000;  // 천점 감점!
        goaltext.setText(Integer.toString(pscore));

        timerText.setText(Integer.toString(timelimitDown));
        progressBar.setProgress((int) ((timelimitDown * 100) / progressbar_num));
        if(itemct2 == 2){
            ImageView itemicon2 = (ImageView)findViewById(R.id.itemimg2);
            itemicon2.setImageResource(darkicon[1]);
        }
    }
    public void cross(View view) { // cross 아이템 실행. 3
        play2();
        if(itemct3 == 2){
            toastShow("이미 2회 사용하셨습니다");
            return;
        }
        if(pscore < 500) { // 점수 부족하면 실행 못함.
            toastShow("점수부족");
            return;
        }
        toastShow("망치사용! 아이콘을 선택해주세요");
        itemct3++;
        pscore -= 500;   // 500점 감소
        crossbtn = true; // crossbtnfunction을 실행하기 위해서 true로 바꿔줌.
        if(itemct3 == 2){
            ImageView itemicon3 = (ImageView)findViewById(R.id.itemimg3);
            itemicon3.setImageResource(darkicon[2]);
        }
    }

    public void reset(View view) { // 아이콘 초기화 아이템  1
        play2();
        if(itemct1 == 2){
            toastShow("이미 2회 사용하셨습니다");
            return;
        }
        if(pscore < 2000) { // 점수 부족하면 실행 못함.
            toastShow("점수부족");
            return;
        }
        toastShow("리셋 사용!");
        itemct1++;
        pscore -= 2000;
        int image_c = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                icon[i][j] = (ImageView) findViewById(imageId[image_c++]);
                icon[i][j].setVisibility(View.VISIBLE);
                if(record[i][j] != -2) {
                    Type(i, j, -1, -1);
                    icon[i][j].setImageResource(img[record[i][j]]);
                }else
                    icon[i][j].setImageResource(R.drawable.stone);
            }
        }
        goaltext.setText(Integer.toString(pscore)); // 점수 소비하고 보여줌.
        if(itemct1 == 2){
            ImageView itemicon1 = (ImageView)findViewById(R.id.itemimg1);
            itemicon1.setImageResource(darkicon[0]);
        }
    }

    private class reset implements Runnable{  // 15초 마다 아이콘을 새로 추가 시키는 핸들러 함수
        @Override
        public void run() {
            if(toast != null)
                toast.cancel();
            play3();
            View view = View.inflate(GameActivity.this, R.layout.toast_layout,null);
            Toast toastimg = new Toast(GameActivity.this);
            toastimg.setView(view);
            toastimg.setGravity(Gravity.CENTER,0,0);
            toastimg.setDuration(Toast.LENGTH_SHORT);
            toastimg.show();
            for(int i = 0 ; i < 8; i++){
                for(int j = 0 ; j < 6; j++){
                    if(record[i][j] != -2) {
                        if (record[i][j] == -1) {
                            Type(i, j, -1, -1);
                            icon[i][j].setImageResource(img[record[i][j]]);
                            icon[i][j].setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            group(0,7,0,5);  // 블록 추가 후 새로 생긴 3줄이상의 같은 아이콘 삭제!!

            if(timelimitDown > 20) // 15초 이하로 남았을때에는 이제 이 핸들러를 호출하지 않아도 된다.
                handler.postDelayed(this,reset_timer); // 15초 후에 이 함수 다시 호출!!
            // 핸들러 무한 루프 방지!! // 20으로 잡은 이유는 게임이 끝나고도 아이콘 추가 액티비티가 호출되기때문에!
        }
    }
    private class CDown implements Runnable {    // 카운트 다운 핸들러 함수
        @Override
        public void run() {
            progressBar.setProgress((int) ((timelimitDown * 100) / progressbar_num));
            if (timelimitDown != 0) {
                timerText.setText(Integer.toString(--timelimitDown));
                handler.postDelayed(this, 1000);  // 1초마다 수를 이 함수를 재 실행!!
                if(addtime != 0){
                    addtime -= 1000;
                }
            }
        }
    }
    // 게임 실행 시간 및 데이터 넘기는 클래스
    private class closed implements Runnable {
        @Override
        public void run() {
            if (timelimitDown != 0) {  // 시간 추가 아이템을 사용해서 시간이 남아있음.
                GameEnd.postDelayed(new closed(),addtime);
            } else {
                if(toast != null)
                    toast.cancel();

                play3();
                Intent intent = new Intent(getApplicationContext(), GendActivity.class);
                // 성공 실패 여부 putExtra() 전달해야되는 소스!!
                intent.putExtra("level", Level);
                String myscore = Integer.toString(pscore); // 획득한 점수를 문자열로 변경
                intent.putExtra("myscore", myscore);   // 획득한 점수를 보냄
                intent.putExtra("score", score);       // 기존 목표점수를 보냄.
                intent.putExtra("time", time);
                // 성공 실패 여부 전달
                if (goalscore <= pscore) {
                    intent.putExtra("result", "Clear!!");
                } else
                    intent.putExtra("result", "Fail!!");
                startActivity(intent);

                GameActivity.this.finish(); // 게임 화면 스택에서 제거
            }
        }
    }

    public void crossbtnfunction(){  // 크로스 아이템 사용하면 나오는 결과
        if(record[ra][rb] == -2) return; // 아무리 터치 해도 아무 일도 안 일어난다!! 이건 돌땡이니까
        icon[ra][rb].setVisibility(View.INVISIBLE);
        record[ra][rb] = -1;
        if(ra-1 != -1 && record[ra-1][rb] != -2) {
            icon[ra - 1][rb].setVisibility(View.INVISIBLE);
            record[ra - 1][rb] = -1;
        }
        if(ra+1 != 8 && record[ra+1][rb] != -2) {
            icon[ra + 1][rb].setVisibility(View.INVISIBLE);
            record[ra + 1][rb] = -1;
        }
        if(rb-1 != -1 && record[ra][rb-1] != -2) {
            icon[ra][rb - 1].setVisibility(View.INVISIBLE);
            record[ra][rb - 1] = -1;
        }
        if(rb+1 != 6 && record[ra][rb+1] != -2) {
            icon[ra][rb + 1].setVisibility(View.INVISIBLE);
            record[ra][rb + 1] = -1;
        }
        goaltext.setText(Integer.toString(pscore));
        crossbtn = false;
    }

    public void iconmath(){  // 아이콘 교환 함수
        if(record[ra][rb] == -2) return; // 아무리 터치 해도 아무 일도 안 일어난다!!
        if (getTouch) {
            if ((rx == ra + 1 && ry == rb) || (rx == ra && ry == rb + 1)
                    || (rx == ra - 1 && ry == rb) || (rx == ra && ry == rb - 1)) {
                // 이전에 클릭된 아이콘이 위에 해당하는 것이여만 교체를 한다. // 인접한 아이콘의 좌표
                int temp = record[ra][rb];
                record[ra][rb] = saveimg;
                record[rx][ry] = temp;
                icon[ra][rb].setImageResource(img[saveimg]);
                icon[rx][ry].setImageResource(img[temp]);
                group(ra - 2, ra + 2, rb - 2, rb + 2);
                // 값을 바꾸었을 때 세개이상 그룹이 될 경우 삭제 하는 메소드
            } else {
                icon[rx][ry].setImageResource(img[saveimg]);
            }
            getTouch = false;  // 이미지를 바꾸든 안 바꾸든 touch는 풀어준다.
        } else {
            saveimg = record[ra][rb];
            icon[ra][rb].setImageResource(img[saveimg + 5]);
            rx = ra;
            ry = rb;
            getTouch = true;
        }
    }
    // 각 아이콘에 해당하는 함수들.
    public void icon1(View view) {
        play2();
        ra = 0;
        rb = 0;
        if(crossbtn){
           crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon2(View view) {
        play2();
        ra = 0;
        rb = 1;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }

    public void icon3(View view) {
        play2();
        ra = 0;
        rb = 2;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon4(View view) {
        play2();
        ra = 0;
        rb = 3;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon5(View view) {
        play2();
        ra = 0;
        rb = 4;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon6(View view) {
        play2();
        ra = 0;
        rb = 5;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon7(View view) {
        play2();
        ra = 1;
        rb = 0;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon8(View view) {
        play2();
        ra = 1;
        rb = 1;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon9(View view) {
        play2();
        ra = 1;
        rb = 2;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon10(View view) {
        play2();
        ra = 1;
        rb = 3;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon11(View view) {
        play2();
        ra = 1;
        rb = 4;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon12(View view) {
        play2();
        ra = 1;
        rb = 5;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon13(View view) {
        play2();
        ra = 2;
        rb = 0;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon14(View view) {
        play2();
        ra = 2;
        rb = 1;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon15(View view) {
        play2();
        ra = 2;
        rb = 2;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon16(View view) {
        play2();
        ra = 2;
        rb = 3;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon17(View view) {
        play2();
        ra = 2;
        rb = 4;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon18(View view) {
        play2();
        ra = 2;
        rb = 5;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon19(View view) {
        play2();
        ra = 3;
        rb = 0;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon20(View view) {
        play2();
        ra = 3;
        rb = 1;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon21(View view) {
        play2();
        ra = 3;
        rb = 2;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon22(View view) {
        play2();
        ra = 3;
        rb = 3;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon23(View view) {
        play2();
        ra = 3;
        rb = 4;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon24(View view) {
        play2();
        ra = 3;
        rb = 5;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon25(View view) {
        play2();
        ra = 4;
        rb = 0;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon26(View view) {
        play2();
        ra = 4;
        rb = 1;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon27(View view) {
        play2();
        ra = 4;
        rb = 2;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon28(View view) {
        play2();
        ra = 4;
        rb = 3;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon29(View view) {
        play2();
        ra = 4;
        rb = 4;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon30(View view) {
        play2();
        ra = 4;
        rb = 5;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon31(View view) {
        play2();
        ra = 5;
        rb = 0;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon32(View view) {
        play2();
        ra = 5;
        rb = 1;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon33(View view) {
        play2();
        ra = 5;
        rb = 2;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon34(View view) {
        play2();
        ra = 5;
        rb = 3;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon35(View view) {
        play2();
        ra = 5;
        rb = 4;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon36(View view) {
        play2();
        ra = 5;
        rb = 5;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon37(View view) {
        play2();
        ra = 6;
        rb = 0;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon38(View view) {
        play2();
        ra = 6;
        rb = 1;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon39(View view) {
        play2();
        ra = 6;
        rb = 2;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon40(View view) {
        play2();
        ra = 6;
        rb = 3;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon41(View view) {
        play2();
        ra = 6;
        rb = 4;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon42(View view) {
        play2();
        ra = 6;
        rb = 5;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon43(View view) {
        play2();
        ra = 7;
        rb = 0;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon44(View view) {
        play2();
        ra = 7;
        rb = 1;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon45(View view) {
        play2();
        ra = 7;
        rb = 2;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon46(View view) {
        play2();
        ra = 7;
        rb = 3;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon47(View view) {
        play2();
        ra = 7;
        rb = 4;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    public void icon48(View view) {
        play2();
        ra = 7;
        rb = 5;
        if(crossbtn){
            crossbtnfunction();
        }else {
            iconmath();
        }
    }
    private void toastShow(String message){
        if(toastimg != null)
            toastimg.cancel();
        if(toast == null){
            toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        }else{
            toast.setText(message);
        }
        toast.show();
    }
}

