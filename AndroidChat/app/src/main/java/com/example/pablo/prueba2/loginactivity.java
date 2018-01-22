package com.example.pablo.prueba2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class loginactivity extends AppCompatActivity {

    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;
    private Button mLoginBtn;

    private Toolbar mToolBar;


    private FirebaseAuth mAuth;

    private ProgressDialog mLoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        mAuth = FirebaseAuth.getInstance();

        mToolBar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mLoginProgress = new ProgressDialog(this);

        mLoginPassword = (TextInputLayout) findViewById(R.id.regpasswordlogin);
        mLoginEmail = (TextInputLayout) findViewById(R.id.regemaillogin);
        mLoginBtn = (Button) findViewById(R.id.button_login);

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String myEmail = mLoginEmail.getEditText().getText().toString();
                String myPassword = mLoginPassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(myEmail) || TextUtils.isEmpty(myPassword)){

                }else{
                    mLoginProgress.setTitle("Logging in");
                    mLoginProgress.setMessage("Please wait while we check your credentials");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    loginUser(myEmail,myPassword);
                }
            }
        });

    }

    private void loginUser(final String myEmail, String myPassword) {

        mAuth.signInWithEmailAndPassword(myEmail,myPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Intent mainIntent =  new Intent ( loginactivity.this, MainActivity.class);
                   startActivity(mainIntent);
                   finish();
               }else{
                   mLoginProgress.hide();
                   Toast.makeText(loginactivity.this, "Cannot log in, check your credentials and ttry again.",
                           Toast.LENGTH_SHORT).show();
               }

            }
        });

    }
}
