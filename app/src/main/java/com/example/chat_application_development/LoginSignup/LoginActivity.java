package com.example.chat_application_development.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.chat_application_development.Constants.constants.KEY_COLLECTION_USERS;
import static com.example.chat_application_development.Constants.constants.KEY_PASSWORD;
import static com.example.chat_application_development.Constants.constants.KEY_PHONE_NUMBER;
import static com.example.chat_application_development.Constants.constants.KEY_USERNAME;
import static com.example.chat_application_development.Constants.constants.LOGIN_PASSWORD;
import static com.example.chat_application_development.Constants.constants.LOGIN_USERNAME;
import static com.example.chat_application_development.Constants.constants.LOGIN_PHONE_NUMBER;

import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chat_application_development.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginToSignup();
    }

    public void loginToSignup() {
        TextView login = (TextView) findViewById(R.id.LoginToSignupLink);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans = (Spannable) login.getText();
        ClickableSpan clickSpan = new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                onBackPressed();
            }
        };
        spans.setSpan(clickSpan, 0, spans.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void clickedLogin(View view) {
        login();
    }

    private void login() {
        if (isValidLogin()) {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection(KEY_COLLECTION_USERS)
                    .whereEqualTo("phone_number", LOGIN_PHONE_NUMBER)
                    .whereEqualTo("password", LOGIN_PASSWORD)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            KEY_PASSWORD = LOGIN_PASSWORD;
                            KEY_USERNAME = LOGIN_USERNAME;
                            KEY_PHONE_NUMBER = LOGIN_PHONE_NUMBER;
                            Log.d("Login", "Login Successful : ");
                            showToast("Welcome");
                        } else {
                            showToast("Invalid Login Details");
                        }
                    });
        }
    }

    private boolean isValidLogin() {
        EditText phone_number = (EditText) findViewById(R.id.login_phone_number);
        if (phone_number.getText().toString().isEmpty()) {
            //Log.d("phone_number",phone_number.getText().toString());
            showToast("Enter Phone Number");
            return false;
        }
        if (phone_number.getText().toString().length() != 10) {
            //Log.d("phone_number",phone_number.getText().toString());
            showToast("Enter valid Phone number");
            return false;
        }
        EditText password = (EditText) findViewById(R.id.loginPassword);
        if (password.getText().toString().isEmpty()) {
            showToast("Enter valid password");
            return false;
        }
        LOGIN_PHONE_NUMBER = phone_number.getText().toString();
        LOGIN_PASSWORD = password.getText().toString();
        return true;
    }

}