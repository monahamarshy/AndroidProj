package com.example.monaproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity {
     EditText emailEditText;
     EditText passwordEditText;
     Button  loginButton;
     TextView newAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        emailEditText=findViewById(R.id.emailEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        loginButton=findViewById(R.id.loginButton);
        newAccountButton=findViewById(R.id.newAccountButton);
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,CreateNewAcountActivity.class));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseAuth mAuth=FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                } else {

                                    Log.d("error","erro");

                                }
                            }
                        });
            }
        });





   }
}