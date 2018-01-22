package com.example.pablo.prueba2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startactivity extends AppCompatActivity {

    private Button regBtn;
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);

        regBtn = (Button) findViewById(R.id.button2);
        loginBtn = (Button) findViewById(R.id.button6);

        regBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent( startactivity.this, registerActivity.class);
                startActivity(regIntent);
            }
        } );

        loginBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent( startactivity.this, loginactivity.class);
                startActivity(regIntent);
            }
        } );


    }

}
