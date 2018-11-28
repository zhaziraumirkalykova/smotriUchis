package com.example.zhaziraun.cleanit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String TAG = "RegistrationActivitylogs";
    EditText username, login, password;
    Button registerButton;
    TextView loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
        loginActivity = findViewById(R.id.loginActivity);

        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameString = username.getText().toString();
                String loginString = login.getText().toString();
                String passwordString = password.getText().toString();

                if(!TextUtils.isEmpty(usernameString) && !TextUtils.isEmpty(loginString) && !TextUtils.isEmpty(passwordString)){
                    signUp(loginString, passwordString, usernameString);
                }
            }
        });
    }

    public void signUp(String loginString, String password, final String usernameString){
        mAuth.createUserWithEmailAndPassword(loginString, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: Success ");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users");

                            UserDataHelper userDataHelper = new UserDataHelper();
                            userDataHelper.setUsername(usernameString);
                            myRef.child(mAuth.getCurrentUser().getUid()).setValue(userDataHelper);

//                            Toast.makeText(getApplicationContext(), "Успешно зарегистрированы!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

                        }else{
                            Log.d(TAG, "onComplete: Error");
                        }
                    }
                });

    }
}
