package com.example.firebaseassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MainActivity extends AppCompatActivity {

        EditText et_email , et_password ,et_name,et_username;
        Button signUp;
        ProgressBar progressBar ;
        FirebaseAuth mAuth ;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mAuth = FirebaseAuth.getInstance();
            progressBar = (ProgressBar) findViewById(R.id.progressbar);

            et_name=findViewById(R.id.name);
            et_email = findViewById(R.id.email);
            et_username = findViewById(R.id.username);
            et_password = findViewById(R.id.passwd);
            signUp = findViewById(R.id.btnregister);

       signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String name = et_name.getText().toString();
                String username = et_username.getText().toString();

                New helperClass = new New(name, email, username, password);
                reference.child(username).setValue(helperClass);
                Toast.makeText(MainActivity.this, "signup successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

        class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        getFirebaseMessage(message.getNotification().getTitle(), message.getNotification().getBody());

    }

public void getFirebaseMessage(String title, String msg) {

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myFirebaseChannel")
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true);


    NotificationManagerCompat manager = NotificationManagerCompat.from(this);
    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

        return;
    }
    manager.notify(101, builder.build());
}
}
        }
   });


//            if(TextUtils.isEmpty(email)){
//                Toast.makeText(MainActivity.this, "Please fill email", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if(TextUtils.isEmpty(password)){
//                Toast.makeText(MainActivity.this, "Please fill password", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            mAuth.createUserWithEmailAndPassword(email,password )
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(MainActivity.this, "welcome", Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
//                                Intent intent = new Intent(MainActivity.this, Login.class);
//                                startActivity(intent);
//
//                            }else{
//                                Toast.makeText(MainActivity.this, "sign up failed", Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
//
//                            }
//                        }
//                    });

        }
    }

