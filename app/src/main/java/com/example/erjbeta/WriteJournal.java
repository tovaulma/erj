package com.example.erjbeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WriteJournal extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private ImageView cover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_journal);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        cover = findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                ImagePicker.Companion.with(this).crop()
                        .maxResultSize(1080, 1080)
                        .start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        cover.setImageURI(uri);
    }
}