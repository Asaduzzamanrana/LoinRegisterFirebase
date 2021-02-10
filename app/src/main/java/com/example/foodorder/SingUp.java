package com.example.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SingUp extends AppCompatActivity {
    private EditText singUpEmailEditext,singUppassEditText,firstNameEditText,lastNameEditText;
    private Button singupbutton;
    private TextView singintextview;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        mAuth = FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.progressBarId);
        singUpEmailEditext=findViewById(R.id.singUpEmailEditTextId);
        singUppassEditText=findViewById(R.id.singUpPassEditTextId);
        firstNameEditText=findViewById(R.id.firstNameEditTextId);
        lastNameEditText=findViewById(R.id.lastNameEditTextId);
        singupbutton=findViewById(R.id.singUpButtonId);
        singintextview=findViewById(R.id.singInTextViewId);
/*
        if (mAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class) );
            finish();
        }*/
        singintextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LogIn.class);
                startActivity(intent);
            }
        });
        singupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=singUpEmailEditext.getText().toString().trim();
                String password=singUppassEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    singUpEmailEditext.setError("Enter Email");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    singUppassEditText.setError("Enter Password");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                   singUpEmailEditext.setError("Enter Email Please !");
                   singUpEmailEditext.requestFocus();
                   return;
                }
                if (password.length()<6){
                    singUppassEditText.setError("password length minimum 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SingUp.this, "user create successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LogIn.class) );
                        }
                        else {
                            Toast.makeText(SingUp.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}