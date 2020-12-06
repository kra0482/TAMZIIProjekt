package com.example.tamziiprojekt_naklist;

import android.os.Bundle;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayRecordActivity extends AppCompatActivity {
    private DBHelper mydb;
    TextView nameTextView;
    int id_update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_record);

        nameTextView = findViewById(R.id.editTextName);
        mydb = new DBHelper(this);
        Intent i = getIntent();
        if(i != null)
        {
            //ziskam ID, ktere se ma editovat/zobrazit/mazat - poslane z main aktivity
            int value = i.getIntExtra("id", 0);
            if (value >0)
            {
                //z db vytahnu zaznam pod hledanym ID a ulozim do id_update
                id_update = value;
                Cursor rs = mydb.getData(id_update);
                rs.moveToFirst();

                //z DB vytahnu jmeno zaznamu
                String nameDB = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));

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

            }
        }
    }
}
