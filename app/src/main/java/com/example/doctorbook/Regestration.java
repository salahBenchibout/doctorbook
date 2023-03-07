package com.example.doctorbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Regestration extends AppCompatActivity {
    private TextView loginpageQustion;
    private TextInputEditText regfullname,regidumber,phonenumber,loginPassword,regemail;
    private Button RegButton;
    //private CircleImageView profileimage;
    private Uri uri;
    private FirebaseAuth mauth;
    private DatabaseReference userdatabaseRef;
    private ProgressDialog loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);

        loginpageQustion=findViewById(R.id.loginpageQustion);
        regfullname=findViewById(R.id.regfullname);
        regidumber=findViewById(R.id.regidumber);
        phonenumber=findViewById(R.id.phonenumber);
        loginPassword=findViewById(R.id.loginPassword);
        RegButton=findViewById(R.id.RegButton);
       // profileimage=findViewById(R.id.profileimage);
        regemail=findViewById(R.id.regemail);
        loader=new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();


        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname=regfullname.getText().toString().trim();
                final String idnumber=regidumber.getText().toString().trim();
                final String phone=phonenumber.getText().toString().trim();
                final String password=loginPassword.getText().toString().trim();
                //  final String profileima=profileimage.getText().toString().trim();
                final String email=regemail.getText().toString().trim();
                if(TextUtils.isEmpty(fullname)){
                    regfullname.setError("Full Name is Required!");
                }
                if(TextUtils.isEmpty(idnumber)){
                    regidumber.setError("ID Number is Required!");
                }
                if(TextUtils.isEmpty(phone)){
                    phonenumber.setError("Phone Number is Required!");
                }
                if(TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is Required!");
                }
                if(TextUtils.isEmpty(email)){
                    regfullname.setError("Email is Required!");
                }
                //if(uri==null){
                   // Toast.makeText(Regestration.this,"Profile Image is Required",Toast.LENGTH_LONG).show();
               // }
                else{


                    loader.setMessage("Registration in Progress..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Regestration.this,"Error Ocured"+task.getException().toString(),Toast.LENGTH_LONG).show();

                            }
                            else {

                                Intent intent=new Intent(Regestration.this,MainActivity.class);

                                startActivity(intent);
                                finish();

                                String currentuserid=mauth.getCurrentUser().getUid();
                                userdatabaseRef= FirebaseDatabase.getInstance().getReference().
                                        child("users").child(currentuserid);
                                HashMap userinfo=new HashMap();
                                userinfo.put("id",currentuserid);
                                userinfo.put("fullname",fullname);
                                userinfo.put("idnumber",idnumber);
                                userinfo.put("phone",phone);
                                userinfo.put("password",password);
                                userinfo.put("email",email);
                                //userinfo.put("profilemage",uri);
                                userinfo.put("users","1");
                                userdatabaseRef.updateChildren(userinfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(Regestration.this,"Detailes Saved Succesfully.",Toast.LENGTH_LONG).show();

                                        }
                                        else{
                                            Toast.makeText(Regestration.this,"Error Ocured"+task.getException().toString(),Toast.LENGTH_LONG).show();
                                        }
                                        finish();
                                        loader.dismiss();
                                    }
                                });

                            }
                        }
                    });
                }


            }
        });
        loginpageQustion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Regestration.this,Login.class);
                startActivity(intent);
            }
        });
    }
    }
