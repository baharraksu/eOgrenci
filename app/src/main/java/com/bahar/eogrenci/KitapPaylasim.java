package com.bahar.eogrenci;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KitapPaylasim extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap_paylasim);

        recyclerView=findViewById(R.id.recyclerView);
        add_button=findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(KitapPaylasim.this,AddActivity.class);
                startActivity(intent);
            }
        });

    }
}