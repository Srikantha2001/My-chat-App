package com.example.chat_application_development.LoginSignup;

import static com.example.chat_application_development.Constants.constants.KEY_COLLECTION_USERS;
import static com.example.chat_application_development.Constants.constants.SIGNUP_PASSWORD;
import static com.example.chat_application_development.Constants.constants.SIGNUP_USERNAME;
import static com.example.chat_application_development.Constants.constants.SIGNUP_PHONE_NUMBER;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_application_development.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupToLogin();
    }

    public void signupToLogin() {
        TextView signup = (TextView) findViewById(R.id.SignupToLoginLink);
        signup.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans = (Spannable) signup.getText();
        ClickableSpan clickSpan = new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                Intent launchSignupActivity = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(launchSignupActivity);
            }
        };
        spans.setSpan(clickSpan, 0, spans.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void clickedSignupButton(View view){signup();}

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }



    private void signup(){
        if(isValidSignup()){
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            HashMap<String ,Object> data = new HashMap<>();
            data.put("user_name",SIGNUP_USERNAME);
            data.put("phone_number",SIGNUP_PHONE_NUMBER);
            data.put("password",SIGNUP_PASSWORD);
            database.collection(KEY_COLLECTION_USERS)
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        showToast("Welcome "+SIGNUP_USERNAME);
                    })
                    .addOnFailureListener(exception -> {
                        showToast(exception.getMessage());
                    });
        }
    }

    @NonNull
    private Boolean isValidSignup(){
        EditText phone_number = (EditText) findViewById(R.id.signupPhone_number);
        if(phone_number.getText().toString().isEmpty()){
            //Log.d("phone_number",phone_number.getText().toString());
            showToast("Enter Phone Number");
            return false;
        }
        if(phone_number.getText().toString().length()!=10){
            //Log.d("phone_number",phone_number.getText().toString());
            showToast("Enter valid Phone number");
            return false;
        }
        EditText user_name =(EditText) findViewById(R.id.signupUsername);
        if(user_name.getText().toString().isEmpty()){
            showToast("Enter User Name");
            return false;
        }
        EditText password=(EditText) findViewById(R.id.signupPassword);
        if(password.getText().toString().isEmpty()){
            showToast("Enter valid password");
            return false;
        }
        SIGNUP_USERNAME = user_name.getText().toString();
        SIGNUP_PHONE_NUMBER = phone_number.getText().toString();
        SIGNUP_PASSWORD = password.getText().toString();
        return true;
    }

}