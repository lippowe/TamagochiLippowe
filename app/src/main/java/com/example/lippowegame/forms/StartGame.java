package com.example.lippowegame.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lippowegame.databinding.ActivityStartGameBinding;
import com.example.lippowegame.R;


public class StartGame extends AppCompatActivity {

    ActivityStartGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        binding = ActivityStartGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartGame.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}