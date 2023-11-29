package com.example.lippowegame.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lippowegame.R;
import com.example.lippowegame.databinding.ActivityDeadTamogochiBinding;
import com.example.lippowegame.adapter.TamagochiAdapter;
import com.example.lippowegame.gameclass.Tamagochi;
import com.example.lippowegame.database.DataBaseManager;


import java.lang.reflect.Field;
import java.util.List;

public class  DeadTamogochiActivity extends AppCompatActivity {
    ActivityDeadTamogochiBinding binding;
    DataBaseManager dataBaseManager;
    List<Tamagochi> tamagochiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead_tamogochi);
        binding = ActivityDeadTamogochiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataBaseManager = new DataBaseManager(this);
        dataBaseManager.openDb();
        //setBestTime(tamagochiList);
        tamagochiList = dataBaseManager.getTamogochis();
        TamagochiAdapter tamagochiAdapter = new TamagochiAdapter(this, tamagochiList);
        binding.recyclerView.setAdapter(tamagochiAdapter);
        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeadTamogochiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDb();
    }
}