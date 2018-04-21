package com.res.easyorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class LoginActivity extends AppCompatActivity {

    private EditText mail;
    private EditText pass;
    private Button login;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = (EditText) findViewById(R.id.gmail);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = mail.getText().toString().trim();
                password = pass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){

                    Toast.makeText(getApplicationContext(),"Enter your Gmail.",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(password)){

                    Toast.makeText(getApplicationContext(),"Enter your password.",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){

                    Toast.makeText(getApplicationContext(),"Enter your Gmail & password.",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Please wait..");
                progressDialog.show();

                //Registering the user with email and password
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //checking if registration is successful
                                if(task.isSuccessful()){

                                    progressDialog.dismiss();
                                    finish();
                                    Toast.makeText(getApplicationContext(),"Login successfull!",Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                                    startActivity(intent);
                                }

                                else{
                                    //checking if user is already registered
                                    firebaseAuth.fetchSignInMethodsForEmail(email)
                                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                                                    boolean check = !task.getResult().getSignInMethods().isEmpty();
                                                    //signing in if user is already registered
                                                    if(check){
                                                        firebaseAuth.signInWithEmailAndPassword(email,password)
                                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                        //if signing in is successful, user is being entered into the app
                                                                        if(task.isSuccessful()){

                                                                            progressDialog.dismiss();
                                                                            finish();
                                                                            Toast.makeText(getApplicationContext(),"Login successful!",Toast.LENGTH_LONG).show();

                                                                            Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                                                                            startActivity(intent);
                                                                        }
                                                                        //if signing in is unsuccessful, user is notified
                                                                        else{

                                                                            progressDialog.dismiss();
                                                                            Toast.makeText(getApplicationContext(),"Could not login! Please try again.",Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    //if user is not already registered and registration was unsuccessful
                                                    else{
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getApplicationContext(),"Could not login! Please try again.",Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        });
    }

    public void onBackPressed(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
