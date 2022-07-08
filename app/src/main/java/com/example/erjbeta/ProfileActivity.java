package com.example.erjbeta;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        welcome = findViewById(R.id.welcome);
        welcome.setText("Welcome, "+getIntent().getStringExtra("uid")+"!");
    }
}