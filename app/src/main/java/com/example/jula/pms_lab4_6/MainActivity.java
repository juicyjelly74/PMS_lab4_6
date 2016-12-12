package com.example.jula.pms_lab4_6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openActivityAll(View v){
        Intent intent = new Intent(this, ShowDBActivity.class);
        startActivity(intent);
    }

    public void openFilterActivity(View v){
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }
}
