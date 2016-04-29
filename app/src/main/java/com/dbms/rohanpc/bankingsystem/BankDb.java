package com.dbms.rohanpc.bankingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rohanpc on 4/27/2016.
 */
public class BankDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Banking System";

    // Contacts table name
    private static final String TABLE_NAME= "login";

    private static final String ID = "username";
    private static final String NAME="password";


    private static final String COUNT="balance";

    public BankDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE login(username TEXT PRIMARY KEY, password TEXT, balance INTEGER);";
        db.execSQL(CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    void add (String username, String password, int count){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(ID,username);
        values.put(COUNT,count);
        values.put(NAME,password);
        db.insert(TABLE_NAME, null, values);
        Log.v(BankDb.class.getSimpleName(), "Added an item");
    }

    String getBalance(String username)
    {
        int bal=0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM login WHERE username = '" + username + "';", null);
        if(cursor!=null&&cursor.moveToFirst()) {
            cursor.moveToFirst();

             bal = cursor.getInt(cursor.getColumnIndex(COUNT));
        }
        return String.valueOf(bal);
        }
//    boolean isEmpty(){
//        SQLiteDatabase db=this.getReadableDatabase();
//        Cursor cursor=db.query(TABLE_NAME, new String[]{ID, NAME, COUNT}, null, null, null, null, null);
//        cursor.moveToFirst();
//        if(cursor.getCount()>0)
//            return false;
//        else
//            return true;
//    }
//    ArrayList<EndangeredItem> getAllDetails(){
//        SQLiteDatabase db= this.getReadableDatabase();
//        Cursor cursor=db.rawQuery("SELECT * FROM counter ORDER BY count DESC",null);
//
//        if (cursor != null&&cursor.moveToFirst()) {
//            ArrayList<EndangeredItem> arrayList=new ArrayList<>();
//            cursor.moveToFirst();
//
//            for (int i=0; i<cursor.getCount();i++ ) {
//                cursor.moveToPosition(i);
//                EndangeredItem item=new EndangeredItem();
//                Log.v(Counter.class.getSimpleName(), "getAllDetails " + cursor.getInt(cursor.getColumnIndex(COUNT))
//                        + " " + cursor.getCount() + " " + cursor.getInt(cursor.getColumnIndex(ID)) + " " + cursor.getString(cursor.getColumnIndex(NAME)));
//                item.setThumbnail(cursor.getInt(cursor.getColumnIndex(ID)));
//                item.setName(cursor.getString(cursor.getColumnIndex(NAME)));
//                arrayList.add(item);
//            }
//            return arrayList;
//        }
//        else Log.v(Counter.class.getSimpleName(),"Nothing returned");
//        return null;
//    }
    boolean checkLogin(String username,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM login WHERE username = '" + username + "' and password = '" + password + "';", null);
//        Cursor cursor=db.query(TABLE_NAME,new String[] {COUNT},ID,new String[]{""+thumbnail},null,null,null);
        if(cursor!=null&&cursor.moveToFirst()) {
//            cursor.moveToFirst();
            return true;
        }

//            Log.v(Counter.class.getSimpleName(),"getDetail "+cursor.getInt(cursor.getColumnIndex(COUNT))
//                    +" "+cursor.getCount()+" "+cursor.getInt(cursor.getColumnIndex(ID))+" "+cursor.getString(cursor.getColumnIndex(NAME)));
//        }
//        else Log.v(Counter.class.getSimpleName(),"Nothing returned");

        return false;
    }

    boolean checkPassword(String username, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT password FROM login WHERE username = '" + username + "';", null);
        //Cursor cursor=db.query(TABLE_NAME,new String[] {COUNT},ID,new String[]{""+thumbnail},null,null,null);
        if(cursor!=null&&cursor.moveToFirst()){
//            cursor.moveToFirst();
            return true;

//            Log.v(Counter.class.getSimpleName(),"getDetail "+cursor.getInt(cursor.getColumnIndex(COUNT))
//                    +" "+cursor.getCount()+" "+cursor.getInt(cursor.getColumnIndex(ID))+" "+cursor.getString(cursor.getColumnIndex(NAME)));
        }
//        else Log.v(Counter.class.getSimpleName(),"Nothing returned");

        return false;
    }
    void update(String username,int count){
        SQLiteDatabase db=this.getWritableDatabase();

        String update="UPDATE login SET balance =" + count + " WHERE username='" + username + "';";
        //Cursor i=db.rawQuery("UPDATE "+TABLE_NAME+" SET "+COUNT+" = " + newCount + " WHERE "+ID+" = "+thumbnail+";",null);
        db.execSQL(update);
//        Log.v(Counter.class.getSimpleName(),thumbnail+" Updated "+newCount);

    }
}
