package com.example.firebaseassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

            EditText inusername , inpassword;
            Button signIn;
            ProgressBar progressBar ;
            FirebaseAuth mAuth ;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login);

                mAuth = FirebaseAuth.getInstance();
                inusername = findViewById(R.id.username);
                inpassword = findViewById(R.id.password);
                signIn = findViewById(R.id.login);
                progressBar =(ProgressBar)  findViewById(R.id.progressBar1);

                signIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!validateusername() | !validatePassword()) {
                        } else {
                            checkUser();
                        }
                    }
                });

            }
    public Boolean validateusername() {
        String val = inusername.getText().toString();
        if (val.isEmpty()) {
            inusername.setError("username cannot be empty");
            return false;
        } else {
            inusername.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = inpassword.getText().toString();
        if (val.isEmpty()) {
            inpassword.setError("Password cannot be empty");
            return false;
        } else {
            inpassword.setError(null);
            return true;
        }
    }



    public void checkUser(){
        String userUsername = inusername.getText().toString().trim();
        String userPassword = inpassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    inusername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        inusername.setError(null);
                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                        Intent intent = new Intent(Login.this, myprofile.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        inpassword.setError("Invalid Credentials");
                        inpassword.requestFocus();
                    }
                } else {
                    inusername.setError("User does not exist");
                    inusername.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    ////////////////////////////////////////
//                signIn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        GetUser();
//                    }
//                });
//            }
//
//            public void GetUser(){
//                progressBar.setVisibility(View.VISIBLE);
//                String email = et_email.getText().toString();
//                String password = et_password.getText().toString();
//
//                if(TextUtils.isEmpty(email)){
//                    Toast.makeText(Login.this, "Please fill email", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(password)){
//                    Toast.makeText(Login.this, "Please fill password", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                mAuth.signInWithEmailAndPassword(email,password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()){
//                                    Toast.makeText(Login.this, "welcome", Toast.LENGTH_SHORT).show();
//                                    progressBar.setVisibility(View.GONE);
//                                    Intent intent = new Intent(Login.this, myprofile.class);
//                                    startActivity(intent);
//                                }else{
//                                    Toast.makeText(Login.this, "login failed", Toast.LENGTH_SHORT).show();
//                                    progressBar.setVisibility(View.GONE);
//                                }
//                            }
//                        });
            }}