package com.example.shamim.manageyourself;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Shamim on 10-Jun-18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userdetails.db";
    private static final String TABLE_NAME = "user_details";
    private static final String ID = "Id";
    private static final String EMAIL = "Email";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final int VERSION_NUMBER = 1;

    private Context context;

    private static final String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+EMAIL+" TEXT NOT NULL,"+USERNAME+" TEXT NOT NULL, "+PASSWORD+" TEXT NOT NULL )";

    private static final String DROP_TABLE = " DROP TABLE IF EXISTS "+TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null , VERSION_NUMBER );
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {

            sqLiteDatabase.execSQL(CREATE_TABLE);
            Toast.makeText(context, "Table is created", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            Toast.makeText(context, "Exception :"+e , Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {

            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
            Toast.makeText(context, "Table is Upgraded created", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            Toast.makeText(context, "Exception :"+e , Toast.LENGTH_SHORT).show();
        }

    }

    public long insertData(UserDetails userDetails){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME,userDetails.getUsername());
        contentValues.put(EMAIL,userDetails.getEmail());
        contentValues.put(PASSWORD,userDetails.getPassword());

        long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowId;
    }

    public Boolean findPassword(String user, String pass){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        Boolean result = false;

        if (cursor.getCount()==0){
            Toast.makeText(context, "No data is found ", Toast.LENGTH_SHORT).show();
        }else{

            while (cursor.moveToNext()){

                String username = cursor.getString(2);
                String password = cursor.getString(3);

                if (password.equals(pass) && username.equals(user) ){

                    result = true;
                    break;
                }
            }
        }

        return result;

    }

    // Searching a single element
    public Cursor getInfo(String user_name,SQLiteDatabase sqLiteDatabase){

        String[] projection = {ID,EMAIL};
        String selction=USERNAME+" LIKE ?";
        String[] selction_args = {user_name};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,projection,selction,selction_args,null,null,null);
        return cursor;
    }
}
