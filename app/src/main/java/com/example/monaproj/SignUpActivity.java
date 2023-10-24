package com.example.monaproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    EditText  fullNameEditText;
    EditText   repasswordEditText;
    Button  registerButton;
    EditText admincode;

    Switch isadmin;
    TextView errorText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameEditText=findViewById(R.id.fullNameEditText);
        emailEditText=findViewById(R.id.emailEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        repasswordEditText=findViewById(R.id.repasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        errorText2 = findViewById(R.id.errorText2);
        isadmin = findViewById(R.id.SwAdmin);
        admincode = findViewById(R.id.etAdminCode);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }
    private void createNewAccount(){
        errorText2.setVisibility(View.GONE);
        if(fullNameEditText.getText().toString().equals("")){
            errorText2.setVisibility(View.VISIBLE);
            errorText2.setText("please enter your full name");
            return;
        }
        if(emailEditText.getText().toString().equals("")){
            errorText2.setVisibility(View.VISIBLE);
            errorText2.setText("please enter your email");
            return;
        }
        if(passwordEditText.getText().toString().equals("")){
            errorText2.setVisibility(View.VISIBLE);
            errorText2.setText("please enter your password");
            return;
        }
        if(repasswordEditText.getText().toString().equals("")){
            errorText2.setVisibility(View.VISIBLE);
            errorText2.setText("please confirm your password");
            return;
        }
        if(!repasswordEditText.getText().toString().equals(passwordEditText.getText().toString())){
            errorText2.setVisibility(View.VISIBLE);
            errorText2.setText("password dosn't match");
            return;
        }
        if(isadmin.isChecked() && !admincode.getText().toString().equals("13579")){
            errorText2.setVisibility(View.VISIBLE);
            errorText2.setText("admin code is incorrect");
            return;
        }

        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            String name=fullNameEditText.getText().toString();
                            if(isadmin.isChecked()){
                                name = "admin: "+name;
                            }
                            UserProfileChangeRequest  profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                            }
                                        }
                                    });
                        } else {

                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            errorText2.setVisibility(View.VISIBLE);
                            errorText2.setText(task.getException().getMessage());

                        }
                    }
                });
    }
}








