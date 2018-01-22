package com.example.pablo.prueba2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerActivity extends AppCompatActivity {

    private TextInputLayout displayName;
    private TextInputLayout email;
    private TextInputLayout password;
    private Button createAccountButton;

    private FirebaseAuth mAuth;

        private Toolbar mToolBar;


        private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mToolBar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Create account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mRegProgress = new ProgressDialog(this);




        mAuth = FirebaseAuth.getInstance();



        displayName = (TextInputLayout) findViewById(R.id.regname);
        email = (TextInputLayout) findViewById(R.id.regemail);
        password = (TextInputLayout) findViewById(R.id.regpassword);
        createAccountButton = (Button) findViewById(R.id.button3);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name = displayName.getEditText().getText().toString();
                String myEmail = email.getEditText().getText().toString();
                String myPassword = password.getEditText().getText().toString();

                if(TextUtils.isEmpty(display_name) || TextUtils.isEmpty(myEmail) || TextUtils.isEmpty(myPassword)) {

                }else {
                    mRegProgress.setTitle("Registering user");
                    mRegProgress.setMessage("Please wait while your account is being created");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    register_user(display_name, myEmail, myPassword);
                }

            }
        });


    }

    private void register_user(String display_name, String myEmail, String myPassword) {
        mAuth.createUserWithEmailAndPassword(myEmail, myPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            mRegProgress.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Intent mainIntent = new Intent(registerActivity.this,MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                            // Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {

                            mRegProgress.hide();
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(registerActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
