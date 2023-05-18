package com.bahar.eogrenci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username =sharedPreferences.getString("username","").toString();
        Toast.makeText(getApplicationContext(),"Hoşgeldin"+username,Toast.LENGTH_SHORT).show();

        CardView exit=findViewById(R.id.cardExit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SharedPreferences.Editor editor=sharedPreferences.edit();
               editor.clear();
               editor.apply();
               startActivity(new Intent(HomePageActivity.this,LoginActivity.class));
            }
        });
        CardView tartisma =findViewById(R.id.cardTartisma);
        tartisma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this,TartismaActivity.class));
            }
        });
        CardView paylasim =findViewById(R.id.cardNotPaylasim);
        paylasim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this,PaylasimActivity.class));
            }
        });
        CardView kitapPaylasim =findViewById(R.id.cardKitapPaylasim);
        kitapPaylasim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this,KitapPaylasim.class));
            }
        });
    }
}