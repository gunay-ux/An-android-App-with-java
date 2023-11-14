package com.example.camefridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Activity2 extends AppCompatActivity {
    private ImageView fbtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        fbtn = (ImageView)findViewById(R.id.btnFavorite );
        fbtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
               openActivity2();
            }});
    }

    public void openActivity2(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


}