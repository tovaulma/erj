package com.example.erjbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView cancel;
    private FirebaseAuth mAuth;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextConfirmpw;
    private Button registeruser;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        registeruser = findViewById(R.id.registeruser);
        registeruser.setOnClickListener(this);

        editTextFullName = findViewById(R.id.fullname);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmpw = findViewById(R.id.confirmpw);

        progressBar = findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registeruser:
                registeruser();
                break;
        }
    }

    private void registeruser() {
        String name = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String pw = editTextPassword.getText().toString().trim();
        String confirmpw = editTextConfirmpw.getText().toString().trim();

        if(name.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(pw.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(pw.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        if(!(confirmpw.equals(pw))){
            editTextConfirmpw.setError("Must match previous entry!");
            editTextConfirmpw.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name, email);
                            FirebaseDatabase.getInstance("https://erjbeta-d53af-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                        Toast.makeText(RegisterUser.this, "User has been registered successfully! Check your email to verify your account!", Toast.LENGTH_LONG).show();
                                        //Progressbar
                                        progressBar.setVisibility(View.GONE);
                                        //redirect to login
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                    }else{
                                        Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                        //Progressbar
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            //Progressbar
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}