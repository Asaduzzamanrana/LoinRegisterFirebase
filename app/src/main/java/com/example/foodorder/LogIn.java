package com.example.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    private EditText userNameEditText,passEditText;
    private Button loginButton;
    private TextView singUpTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        singUpTextView=findViewById(R.id.singUpTextViewId);
        userNameEditText=findViewById(R.id.userNameEditTextId);
        passEditText=findViewById(R.id.passwordEditTextId);
        loginButton=findViewById(R.id.logInButtonId);
        progressBar=findViewById(R.id.progressbarid);

        singUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogIn.this,SingUp.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=userNameEditText.getText().toString().trim();
                String password=passEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    userNameEditText.setError("Enter Email");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    passEditText.setError("Enter Password");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    userNameEditText.setError("Enter Email Please !");
                    userNameEditText.requestFocus();
                    return;
                }
                if (password.length()<6){
                    passEditText.setError("password length minimum 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LogIn.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class) );
                        }
                        else {
                            Toast.makeText(LogIn.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}