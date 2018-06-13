package com.example.shamim.manageyourself;

import android.content.Intent;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper databaseHelper;

    Button ButtonSignUp;
    TextView SignInPage;
    EditText UserName,Password,Email,ConfirmPass;
    AppCompatImageView NormalCircle,FocusCircle,FocusCircleWrong;

    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButtonSignUp = (Button) findViewById(R.id.buttonSignUp);
        Email = (EditText) findViewById(R.id.EditEmailSignUp);
        UserName = (EditText) findViewById(R.id.EditUsernameSignUp);
        Password = (EditText) findViewById(R.id.EditPasswordSignUp);
        ConfirmPass = (EditText) findViewById(R.id.EditConfirmPasswordSignUp);
        NormalCircle =(AppCompatImageView) findViewById(R.id.imageViewNormalSignUp);
        FocusCircle =(AppCompatImageView) findViewById(R.id.ImageViewFocusSignUp);
        FocusCircleWrong =(AppCompatImageView) findViewById(R.id.ImageViewFocusWrongSignUp);
        SignInPage = (TextView) findViewById(R.id.TextViewSignUp);

        ButtonSignUp.setOnClickListener(this);
        SignInPage.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        userDetails = new UserDetails();

        NormalCircle.setVisibility(View.VISIBLE);
        FocusCircle.setVisibility(View.GONE);
        FocusCircleWrong.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {

        String email = Email.getText().toString();
        String username = UserName.getText().toString();
        String password = Password.getText().toString();
        String confirm_password = ConfirmPass.getText().toString();

        userDetails.setEmail(email);
        userDetails.setPassword(password);
        userDetails.setUsername(username);





        if(view.getId() == R.id.buttonSignUp){

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                Email.setError("Enter valid Email address");
                NormalCircle.setVisibility(View.GONE);
                FocusCircleWrong.setVisibility(View.VISIBLE);
                return;
            }

            if(email.isEmpty()) {

                Email.setError("Email Required");
                NormalCircle.setVisibility(View.GONE);
                FocusCircleWrong.setVisibility(View.VISIBLE);
                return; }

            if(username.isEmpty()) {
                UserName.setError("Username Required");
                NormalCircle.setVisibility(View.GONE);
                FocusCircleWrong.setVisibility(View.VISIBLE);
                return;}

            if(password.isEmpty()){
                Password.setError("Password Required");
                NormalCircle.setVisibility(View.GONE);
                FocusCircleWrong.setVisibility(View.VISIBLE);
                return; }

            if(confirm_password.isEmpty()){ConfirmPass.setError("Confirm password Required");
                NormalCircle.setVisibility(View.GONE);
                FocusCircleWrong.setVisibility(View.VISIBLE);
                return;}

            if(password.equals(confirm_password)) {

                long rowID = databaseHelper.insertData(userDetails);
                if (rowID > 0) {

                    NormalCircle.setVisibility(View.GONE);
                    FocusCircleWrong.setVisibility(View.GONE);
                    FocusCircle.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    intent.putExtra("Username",username);
                    intent.putExtra("Password",password);
                    startActivity(intent);
                    finish();

                    Toast.makeText(this, "Successfully signed up", Toast.LENGTH_SHORT).show();
                } else {

                    NormalCircle.setVisibility(View.GONE);
                    FocusCircleWrong.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Failed to sign up", Toast.LENGTH_SHORT).show();
                }
            }else{
                NormalCircle.setVisibility(View.GONE);
                FocusCircleWrong.setVisibility(View.VISIBLE);
                ConfirmPass.setError("Password didn't matched");
            }


        }
        if(view.getId()==R.id.TextViewSignUp){

            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
        

        }

    }
}
