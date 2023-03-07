package com.example.adm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admlogin extends AppCompatActivity {
    private TextView loginpageQustion;
    private FirebaseAuth mAuth;
    TextInputEditText loginEmail,loginPassword;
    private Button loginButton;
    //private boolean exist;
    private ProgressDialog loader;
    private DatabaseReference userdatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admlogin);

        loader=new ProgressDialog(this);
        loginEmail=findViewById(R.id.loginEmail);
        loginPassword=findViewById(R.id.loginPassword);
        mAuth = FirebaseAuth.getInstance();
        loginpageQustion=findViewById(R.id.loginpageQustion);

        loginpageQustion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admlogin.this,Admregistration.class);
                startActivity(intent);
            }
        });


        loginButton=findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=loginEmail.getText().toString().trim();
                final String password=loginPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    loginEmail.setError("Email is Required!");
                }
                else if(TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is Required!");
                    loginPassword.requestFocus();
                }
                else{

                    loader.setMessage("Registration in Progress..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    authmethode(email,password);




                    /**   mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                    // checkuserlevel(authResult.getUser().getUid());

                    }
                    }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this,"Error Ocured"+e.toString(),Toast.LENGTH_LONG).show();

                    }
                    });
                     */

                }


            }

        });
    }

    private void authmethode(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String currentuserid=task.getResult().getUser().getUid();
                     DatabaseReference df=
                             FirebaseDatabase.getInstance().getReference();
                            df.child("admin").child("admin1").child("isadmin").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()) {
                                        startActivity(new Intent(Admlogin.this,AdmMainActivity.class));
                                  finish();
                                    }
                                    else{
                                        Toast.makeText(Admlogin.this,"Error Ocured",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(Admlogin.this,AdmMainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Admlogin.this, "error"+task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}