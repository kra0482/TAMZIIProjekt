package com.example.tamziiprojekt_naklist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper mydb;
    private ListView itemListView;
    public static ArrayList<Long> arrayListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);
        //ziskam do jedno listu vsechny polozky
        ArrayList arrayList = mydb.getItemList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);

        itemListView = findViewById(R.id.listView1);
        itemListView.setAdapter(arrayAdapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(MainActivity.this, String.valueOf(arg2), Toast.LENGTH_SHORT).show();
                Intent displayRecord = new Intent(getApplicationContext(), DisplayRecordActivity.class);
                displayRecord.putExtra("id", arg2+1);
                startActivity(displayRecord);
            }
        });

    }

    @Override
    public void onResume()
    {
        super.onResume();
        //ziskam do jedno listu vsechny polozky
        ArrayList arrayList = mydb.getItemList();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        itemListView = findViewById(R.id.listView1);
        itemListView.setAdapter(arrayAdapter);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MediaPlayer ping = MediaPlayer.create(this, R.raw.ping);
        int id = item.getItemId();
        if (id == R.id.Add_Item)
        {
            Intent displayRecord = new Intent(getApplicationContext(), DisplayRecordActivity.class);
            startActivity(displayRecord);
        }

        if (id == R.id.Delete_All)
        {
            int nRowDeleted = mydb.removeAll();
            Toast.makeText(getApplicationContext(), "Deleted records: " + nRowDeleted, Toast.LENGTH_SHORT).show();
            ping.start();
            recreate();
        }

        if (id == R.id.Settings)
        {
            Intent displaySettings = new Intent(getApplicationContext(), DisplaySettingsActivity.class);
            startActivity(displaySettings);
        }

        return super.onOptionsItemSelected(item);
    }
}