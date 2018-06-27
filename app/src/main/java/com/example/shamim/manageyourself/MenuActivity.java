package com.example.shamim.manageyourself;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    FloatingActionButton mFAB;
    TextView UserID,UserEmail;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mFAB = (FloatingActionButton) findViewById(R.id.myfab);
        UserID = (TextView) findViewById(R.id.id);
        UserEmail = (TextView) findViewById(R.id.email);

        Intent intent = getIntent();
        String U_name = intent.getStringExtra("Username");

        usergetInfo(U_name);

    }

    public void usergetInfo(String username) {

        databaseHelper = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = databaseHelper.getInfo(username,sqLiteDatabase);
        UserID.setText(username);
        if(cursor.moveToFirst()){
            String id = cursor.getString(0);
            String email = cursor.getString(1);
            UserEmail.setText(email);
            UserID.setText(id);
        }
    }


}
