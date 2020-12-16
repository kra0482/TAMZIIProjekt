package com.example.tamziiprojekt_naklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.menu.MenuBuilder;

public class DisplayRecordActivity extends AppCompatActivity {
    private DBHelper mydb;
    TextView nameTextView;
    TextView costTextView;
    Spinner spinnerType;
    SharedPreferences sharedPreferences = null;

    int idToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_record);

        sharedPreferences = getSharedPreferences("night",0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode",true);
        if (booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        nameTextView = findViewById(R.id.editTextName);
        costTextView = findViewById(R.id.editTextPrice);
        spinnerType = findViewById(R.id.spinnerType);

        List<String> types = Arrays.asList("Food","Water","Household essentials","Electronics");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        spinnerType.setAdapter(adapter);


        mydb = new DBHelper(this);
        Intent i = getIntent();
        if(i !=null)
        {
            //ziskam ID, ktere se ma editovat/zobrazit/mazat - poslane z hlavni aktivity
            int value = i.getIntExtra("id", 0);
            idToUpdate = value;
            if (idToUpdate > 0)
            {
                //z db vytahnu zaznam pod hledanym ID a ulozim do idToUpdate
                Cursor rs = mydb.getData(idToUpdate);
                rs.moveToFirst();

                //z DB vytahnu jmeno zaznamu
                String nameDB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_NAME));
                int type = rs.getInt(rs.getColumnIndex(DBHelper.ITEM_COLUMN_TYPE));
                int cost = rs.getInt(rs.getColumnIndex(DBHelper.ITEM_COLUMN_COST));

                if (!rs.isClosed())
                {
                    rs.close();
                }
                Button b = findViewById(R.id.buttonSave);
                b.setVisibility(View.INVISIBLE);

                nameTextView.setText(nameDB);
                nameTextView.setEnabled(false);
                nameTextView.setFocusable(false);
                nameTextView.setClickable(false);

                spinnerType.setSelection(type);
                spinnerType.setEnabled(false);
                spinnerType.setFocusable(false);
                spinnerType.setClickable(false);

                costTextView.setText(String.valueOf(cost));
                costTextView.setEnabled(false);
                costTextView.setFocusable(false);
                costTextView.setClickable(false);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(idToUpdate>0){
            getMenuInflater().inflate(R.menu.display_record_menu, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Edit_Contact) {
            Button b = findViewById(R.id.buttonSave);
            b.setVisibility(View.VISIBLE);

            nameTextView.setEnabled(true);
            nameTextView.setFocusableInTouchMode(true);
            nameTextView.setClickable(true);

            spinnerType.setEnabled(true);
            spinnerType.setFocusableInTouchMode(true);
            spinnerType.setClickable(true);

            costTextView.setEnabled(true);
            costTextView.setFocusableInTouchMode(true);
            costTextView.setClickable(true);


        }
        if (id == R.id.Delete_Contact)
        {
            mydb.deleteItem(idToUpdate);
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        return true;
    }

    public void saveButtonAction(View view)
    {
        Item item = new Item(idToUpdate, nameTextView.getText().toString(), (int) spinnerType.getSelectedItemId(), Integer.parseInt(costTextView.getText().toString()));
        final MediaPlayer err = MediaPlayer.create(this, R.raw.erro);
        final MediaPlayer ding = MediaPlayer.create(this, R.raw.ding);
        if(idToUpdate > 0){
            mydb.updateItem(item);
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else{
            //vlozeni zaznamu
            if(mydb.insertItem(item)){
                Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
                ding.start();
            }

            else{
                Toast.makeText(getApplicationContext(), "not saved", Toast.LENGTH_SHORT).show();
                err.start();
            }
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

}
