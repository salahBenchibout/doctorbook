package com.example.doctorbook;

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
import com.google.firebase.database.DatabaseReference;

public class Login extends AppCompatActivity {
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
        setContentView(R.layout.activity_login);
        loader=new ProgressDialog(this);
        loginEmail=findViewById(R.id.loginEmail);
        loginPassword=findViewById(R.id.loginPassword);
        mAuth = FirebaseAuth.getInstance();
        loginpageQustion=findViewById(R.id.loginpageQustion);
        loginpageQustion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Regestration.class);
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
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "error"+task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    /**
     *
     *
     *  String currentuserid=task.getResult().getUser().getUid();
     *                 DatabaseReference df=
     *                         FirebaseDatabase.getInstance().getReference();
     *                 df.child("users").child(currentuserid).child("isadmin").addListenerForSingleValueEvent(new ValueEventListener() {
     *                     @Override
     *                     public void onDataChange(DataSnapshot dataSnapshot) {
     *                         if(dataSnapshot.exists()) {
     * //comes here if exit
     *                             startActivity(new Intent(getApplicationContext(),Admin.class));
     *                             finish();
     *                         }
     *                         else {
     * //if not exist
     *                             startActivity(new Intent(getApplicationContext(),MainActivity.class));
     *                             finish();
     *                         }
     *                     }
     *
     *
     *
     *
     *
     *
     *
     *
     * private void checkuserlevel(String s){
    String currentuserid=FirebaseAuth.getInstance().getCurrentUser().getUid();


    DatabaseReference df= FirebaseDatabase.getInstance().getReference().
    child("users").child(currentuserid);
    df.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
    @Override
    public void onSuccess(DataSnapshot dataSnapshot) {
    if(dataSnapshot.child("isadmin")!=null){
    startActivity(new Intent(getApplicationContext(),Admin.class));
    finish();

    }
    else{
    startActivity(new Intent(getApplicationContext(),MainActivity.class));
    finish();
    }
    }
    });
    }
     */

/**  @Override
protected void onStart() {
super.onStart();
if(FirebaseAuth.getInstance().getCurrentUser() !=null){
startActivity(new Intent(getApplicationContext(),MainActivity.class));
finish();
}
}
 */
}