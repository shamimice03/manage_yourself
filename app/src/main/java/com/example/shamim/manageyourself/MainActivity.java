package com.example.shamim.manageyourself;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper databaseHelper;

    Button ButtonSignIn;
    TextView ForgetPass,NewAccount;
    EditText UserName,Password;
    AppCompatImageView NormalCircle,FocusCircle,FocusCircleWrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonSignIn = (Button) findViewById(R.id.buttonSignIn);
        UserName = (EditText) findViewById(R.id.EditUsername);
        Password = (EditText) findViewById(R.id.EditPassword);
        NormalCircle =(AppCompatImageView) findViewById(R.id.imageViewNormal);
        FocusCircle =(AppCompatImageView) findViewById(R.id.imageViewFocus);
        ForgetPass = (TextView) findViewById(R.id.TextViewforgetPassword);
        NewAccount= (TextView) findViewById(R.id.TextViewCreateAccount);
        FocusCircleWrong = (AppCompatImageView) findViewById(R.id.imageViewFocusWrong);

        Intent intent = getIntent();
        String U_name = intent.getStringExtra("Username");
        String P_word = intent.getStringExtra("Password");

        UserName.setText(U_name);
        Password.setText(P_word);



        NormalCircle.setVisibility(View.VISIBLE);
        FocusCircle.setVisibility(View.GONE);
        FocusCircleWrong.setVisibility(View.GONE);

        ButtonSignIn.setOnClickListener(this);
        ForgetPass.setOnClickListener(this);
        NewAccount.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(this);

    }

    @Override
    public void onClick(View view) {

        String username = UserName.getText().toString();
        String password = Password.getText().toString();



        if(view.getId()== R.id.buttonSignIn ){

            if(username.isEmpty()) {UserName.setError("Username Required");}
            if(password.isEmpty()){Password.setError("Password Required");}

            Boolean result = databaseHelper.findPassword(username,password);

            if(result == true){

                NormalCircle.setVisibility(View.GONE);
                FocusCircle.setVisibility(View.VISIBLE);

                Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }else{
                if(username.isEmpty()) {UserName.setError("Username Required");}
                if(password.isEmpty()){Password.setError("Password Required");}

                NormalCircle.setVisibility(View.GONE);
                FocusCircleWrong.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show();

            }




        }

        if(view.getId() == R.id.TextViewCreateAccount){

            Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
