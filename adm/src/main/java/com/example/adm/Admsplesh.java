package com.example.adm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Admsplesh extends AppCompatActivity {
    Handler h=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admsplesh);
    }
    public void onStart() {
        super.onStart();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
               if(currentUser != null){
                    startActivity(new Intent(Admsplesh.this,AdmMainActivity.class));
                    finish();
                }
               else {
                    startActivity(new Intent(Admsplesh.this,Admlogin.class));
                    finish();
               }
            }
        },3000);


        // Check if user is signed in (non-null) and update UI accordingly.

    }
}