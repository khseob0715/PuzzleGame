package com.example.khseob0715.puzzlegame;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static com.example.khseob0715.puzzlegame.FirstActivity.db;


public class RankingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rangking);

        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM contact", null);
        startManagingCursor(cursor);
        String[] from = {"data1", "data2"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(RankingActivity.this,
                android.R.layout.simple_list_item_2, cursor, from, to);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

    }

}
