package com.example.tamziiprojekt_naklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "DBTAMZ2.db";
    private static final String ITEM_TABLE_NAME = "items";
    public static final String ITEM_COLUMN_NAME = "name";
    public static final String ITEM_COLUMN_COST = "cost";
    public static final String ITEM_COLUMN_TYPE = "type";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ITEM_TABLE_NAME + " (id INTEGER PRIMARY KEY, name TEXT, type INTEGER, cost INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertItem(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_COLUMN_NAME, item.getName());
        contentValues.put(ITEM_COLUMN_COST, item.getCost());
        contentValues.put(ITEM_COLUMN_TYPE, item.getType());

        long insertedId = db.insert(ITEM_TABLE_NAME, null, contentValues);
        if (insertedId == -1) return false;
        return true;
    }

    public boolean deleteItem (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

//        db.execSQL("DELETE FROM " + ITEM_TABLE_NAME + " WHERE id=" + id);
        String[] args = {String.valueOf(id)};
        db.delete(ITEM_TABLE_NAME, "id = ?", args);
        return true;
    }

    //Cursor representuje vracena data
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from items where id=" + id + "", null);
        return res;
    }

    public boolean updateItem (Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("UPDATE " + ITEM_TABLE_NAME + " SET " + ITEM_COLUMN_NAME + "=\"" + name + "\" WHERE id=" + id);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_COLUMN_NAME, item.getName());
        contentValues.put(ITEM_COLUMN_TYPE, item.getType());
        contentValues.put(ITEM_COLUMN_COST, item.getCost());
        String[] args = {String.valueOf(item.getId())};
        db.update(ITEM_TABLE_NAME, contentValues, "id = ?", args);
        return true;
    }

    public ArrayList<Item> getItemList()
    {
        ArrayList<Item> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from items", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String name = res.getString(res.getColumnIndex(ITEM_COLUMN_NAME));
            int cost = res.getInt(res.getColumnIndex(ITEM_COLUMN_COST));
            int type = res.getInt(res.getColumnIndex(ITEM_COLUMN_TYPE));
            int id = res.getInt(0);
            arrayList.add(new Item(id, name, type, cost));
            res.moveToNext();
        }

        return arrayList;
    }

    public int removeAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int nRecordDeleted = db.delete(ITEM_TABLE_NAME, null, null);
        return nRecordDeleted;
    }
}
