package com.example.tamziiprojekt_naklist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class DisplayFavorite extends AppCompatActivity {
    private DBHelper mydb;
    SharedPreferences sharedPreferences = null;

    int idToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        sharedPreferences = getSharedPreferences("night",0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode",true);
        if (booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        mydb = new DBHelper(this);
        Intent i = getIntent();
        if(i !=null)
        {
            int value = i.getIntExtra("id", 0);
            idToUpdate = value;
            if (idToUpdate > 0)
            {
                Cursor rs = mydb.getData(idToUpdate);
                rs.moveToFirst();

                String nameDB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_NAME));
                int type = rs.getInt(rs.getColumnIndex(DBHelper.ITEM_COLUMN_TYPE));
                int cost = rs.getInt(rs.getColumnIndex(DBHelper.ITEM_COLUMN_COST));

                if (!rs.isClosed())
                {
                    rs.close();
                }

            }
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void rohlikButtonAction(View view) {
        Item item = new Item(idToUpdate, "Roll", 0 , 4);
        final MediaPlayer err = MediaPlayer.create(this, R.raw.erro);
        final MediaPlayer ding = MediaPlayer.create(this, R.raw.ding);
        if(idToUpdate > 0){
            mydb.updateItem(item);
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else{
            if(mydb.insertItem(item)){
                Toast.makeText(getApplicationContext(), "Roll was added to shopping list", Toast.LENGTH_SHORT).show();
                ding.start();
            }

            else{
                Toast.makeText(getApplicationContext(), "Roll was not added to shopping list", Toast.LENGTH_SHORT).show();
                err.start();
            }
        }
    }

    public void waterButtonAction(View view) {
        Item item = new Item(idToUpdate, "Water", 1 , 25);
        final MediaPlayer err = MediaPlayer.create(this, R.raw.erro);
        final MediaPlayer ding = MediaPlayer.create(this, R.raw.ding);
        if(idToUpdate > 0){
            mydb.updateItem(item);
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else{
            if(mydb.insertItem(item)){
                Toast.makeText(getApplicationContext(), "Water was added to shopping list", Toast.LENGTH_SHORT).show();
                ding.start();
            }

            else{
                Toast.makeText(getApplicationContext(), "Water was not added to shopping list", Toast.LENGTH_SHORT).show();
                err.start();
            }
        }
    }

    public void batteryButtonAction(View view) {
        Item item = new Item(idToUpdate, "Batteries", 3 , 25);
        final MediaPlayer err = MediaPlayer.create(this, R.raw.erro);
        final MediaPlayer ding = MediaPlayer.create(this, R.raw.ding);
        if(idToUpdate > 0){
            mydb.updateItem(item);
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else{
            if(mydb.insertItem(item)){
                Toast.makeText(getApplicationContext(), "Batteries were added to shopping list", Toast.LENGTH_SHORT).show();
                ding.start();
            }

            else{
                Toast.makeText(getApplicationContext(), "Batteries were not added to shopping list", Toast.LENGTH_SHORT).show();
                err.start();
            }
        }
    }

    public void salamiButtonAction(View view) {
        Item item = new Item(idToUpdate, "Salami", 0 , 45);
        final MediaPlayer err = MediaPlayer.create(this, R.raw.erro);
        final MediaPlayer ding = MediaPlayer.create(this, R.raw.ding);
        if(idToUpdate > 0){
            mydb.updateItem(item);
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else{
            if(mydb.insertItem(item)){
                Toast.makeText(getApplicationContext(), "Salami was added to shopping list", Toast.LENGTH_SHORT).show();
                ding.start();
            }

            else{
                Toast.makeText(getApplicationContext(), "Salami was not added to shopping list", Toast.LENGTH_SHORT).show();
                err.start();
            }
        }
    }

    public void paperButtonAction(View view) {
        Item item = new Item(idToUpdate, "Toilet paper", 2 , 55);
        final MediaPlayer err = MediaPlayer.create(this, R.raw.erro);
        final MediaPlayer ding = MediaPlayer.create(this, R.raw.ding);
        if(idToUpdate > 0){
            mydb.updateItem(item);
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else{
            if(mydb.insertItem(item)){
                Toast.makeText(getApplicationContext(), "Toilet paper was added to shopping list", Toast.LENGTH_SHORT).show();
                ding.start();
            }

            else{
                Toast.makeText(getApplicationContext(), "Toilet paper was not added to shopping list", Toast.LENGTH_SHORT).show();
                err.start();
            }
        }
    }

    public void chocolateButtonAction(View view) {
        Item item = new Item(idToUpdate, "Chocolate", 0 , 35);
        final MediaPlayer err = MediaPlayer.create(this, R.raw.erro);
        final MediaPlayer ding = MediaPlayer.create(this, R.raw.ding);
        if(idToUpdate > 0){
            mydb.updateItem(item);
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else{
            if(mydb.insertItem(item)){
                Toast.makeText(getApplicationContext(), "Chocolate was added to shopping list", Toast.LENGTH_SHORT).show();
                ding.start();
            }

            else{
                Toast.makeText(getApplicationContext(), "Chocolate was not added to shopping list", Toast.LENGTH_SHORT).show();
                err.start();
            }
        }
    }

    public void mainButtonAction(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
