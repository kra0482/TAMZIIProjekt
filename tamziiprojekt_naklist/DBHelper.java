package com.example.tamziiprojekt_naklist;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "DBTAMZ.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_PRICE = "price";

    public static ArrayList<String> arrayList = new ArrayList<String>();

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contacts " + "(id INTEGER PRIMARY KEY, name TEXT, type INTEGER, cost INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        long insertedId = db.insert("contacts", null, contentValues);
        if (insertedId == -1) return false;
        return true;
    }

    public boolean deleteContact (int id)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete("contacts", "id=" + id, null);
        return true;
    }

    //Cursor representuje vracena data
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from contacts where id=" + id + "", null);
        return res;
    }

    public boolean updateContact (Integer id, String name)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);

        database.update("contacts", contentValues, "id=" + id, null);
        return true;
    }

    public ArrayList<String> getAllContacsName()
    {
        arrayList.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String name = res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME));
            int id = res.getInt(0);
            arrayList.add(name + "  id:" + id);
            res.moveToNext();
        }

        return arrayList;
    }

    public int removeAll()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        int nRecordDeleted = database.delete("contacts", null, null);
        return nRecordDeleted;
    }
}
