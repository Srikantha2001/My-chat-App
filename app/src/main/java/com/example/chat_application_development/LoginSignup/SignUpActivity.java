package com.example.chat_application_development.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_application_development.R;

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
                Intent launchSignupActivity = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(launchSignupActivity);
            }
        };
        spans.setSpan(clickSpan, 0, spans.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void clickedSignupButton(){signup();}

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }



    private void signup(){

    }

    private Boolean isValidSignup(){return true;}



}