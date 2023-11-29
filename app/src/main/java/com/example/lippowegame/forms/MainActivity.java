package com.example.lippowegame.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.lippowegame.databinding.ActivityMainBinding;
import com.example.lippowegame.gameclass.Tamagochi;
import com.example.lippowegame.database.DataBaseManager;

import com.example.lippowegame.R;
import com.example.lippowegame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Tamagochi tamagochi = new Tamagochi();
    Handler handler = new Handler();
    int secondsAlive = 0;
    DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataBaseManager = new DataBaseManager(MainActivity.this);
        dataBaseManager.openDb();
        setTamagochi();
        updProgressBars();
        threadTime.start();

        binding.buttonHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tamagochi.getHappy() > 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Ваш тамагочи и так счастлив!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    tamagochi.setHappy(tamagochi.getHappy() + 10);
                    updProgressBars();
                }
                //checkState();
            }
        });
        binding.buttonBore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tamagochi.getBore() > 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Ваш тамагочи не хочет веселиться!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    tamagochi.setBore(tamagochi.getBore() + 10);
                    tamagochi.setTired(tamagochi.getTired() - 5);
                    updProgressBars();
                }
                //checkState();
            }
        });
        binding.buttonHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tamagochi.getHp() > 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Ваш тамагочи и так здоров!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    tamagochi.setHp(tamagochi.getHp() + 10);
                    tamagochi.setBore(tamagochi.getBore() - 5);
                    updProgressBars();
                }
                //checkState();
            }
        });
        binding.buttonTired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tamagochi.getTired() > 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Ваш тамагочи не хочет спать!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    tamagochi.setTired(tamagochi.getTired() + 10);
                    tamagochi.setHunger(tamagochi.getHunger() - 5);
                    updProgressBars();
                }
                //checkState();
            }
        });
        binding.buttonHunger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tamagochi.getHunger() > 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Ваш тамагочи не голоден!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                {
                    tamagochi.setHunger(tamagochi.getHunger() + 10);
                    tamagochi.setHappy(tamagochi.getHappy() - 5);
                    updProgressBars();
                }
                //checkState();
            }
        });
    }
    public void checkState() {
        //hunger - Сытость
        if (tamagochi.getHunger() < 50)
            binding.imageView.setImageResource(R.drawable.foodtamagochi);
        else
        //hp - Здоровье
        if (tamagochi.getHp() < 50)
            binding.imageView.setImageResource(R.drawable.badtamagochi);
        else
        //bore - Веселье
        if (tamagochi.getBore() < 50)
            binding.imageView.setImageResource(R.drawable.badtamagochi);
        else
        //happy - Счастье
        if (tamagochi.getHappy() < 50)
            binding.imageView.setImageResource(R.drawable.badtamagochi);
        else
        //tired - Бодрость
        if (tamagochi.getTired() < 50)
            binding.imageView.setImageResource(R.drawable.badtamagochi);
        else
        //dead
        if (tamagochi.getHp() < 10)
            binding.imageView.setImageResource(R.drawable.deadtamagochi);
        else binding.imageView.setImageResource(R.drawable.smiletamagochi);
    }

    public void setTamagochi() {
        tamagochi.setHappy(100);
        tamagochi.setTired(100);
        tamagochi.setHp(100);
        tamagochi.setBore(100);
        tamagochi.setHunger(60);
    }

    public void updProgressBars() {
        binding.progressBarHappy.setProgress(tamagochi.getHappy());
        binding.progressBarBore.setProgress(tamagochi.getBore());
        binding.progressBarHealth.setProgress(tamagochi.getHp());
        binding.progressBarTired.setProgress(tamagochi.getTired());
        binding.progressBarHunger.setProgress(tamagochi.getHunger());
    }

    Thread threadTime = new Thread(new Runnable() {
        @Override
        public void run() {
            for (; tamagochi.getDead() == 0; ) {
                if (tamagochiAlive(tamagochi)) {
                    secondsAlive++;
                    scaleTime(secondsAlive);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.textViewTimer.setText(timeToString(secondsAlive));
                            checkState();
                        }
                    });
                    updProgressBars();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    tamagochi.setDead(1);
                    tamagochi.setDays(secondsAlive);
                    Log.d("TAG", tamagochi.getDays() + " " + secondsAlive);
                    dataBaseManager.addTamogochi(tamagochi);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Ваш тамагочи умер!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, DeadTamogochiActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    });

    public boolean tamagochiAlive(Tamagochi tamagochi) {
        return tamagochi.getHappy() > 0 &&
                tamagochi.getTired() > 0 &&
                tamagochi.getHp() > 0 &&
                tamagochi.getBore() > 0 &&
                tamagochi.getHunger() > 0;
    }

    @SuppressLint("DefaultLocale")
    public String timeToString(int secondsAlive){
        long hour = secondsAlive / 3600,
                min = secondsAlive / 60 % 60,
                sec = secondsAlive / 1 % 60;
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }

    public void scaleTime(int secondsAlive){
        if (secondsAlive <= 60){
            tamagochi.setHappy(tamagochi.getHappy() - 3);
            tamagochi.setTired(tamagochi.getTired() - 3);
            tamagochi.setHp(tamagochi.getHp() - 3);
            tamagochi.setBore(tamagochi.getBore() - 3);
            tamagochi.setHunger(tamagochi.getHunger() - 3);
        } else if (secondsAlive <= 120) {
            tamagochi.setHappy(tamagochi.getHappy() - 6);
            tamagochi.setTired(tamagochi.getTired() - 6);
            tamagochi.setHp(tamagochi.getHp() - 6);
            tamagochi.setBore(tamagochi.getBore() - 6);
            tamagochi.setHunger(tamagochi.getHunger() - 6);
        } else if (secondsAlive <= 180) {
            tamagochi.setHappy(tamagochi.getHappy() - 10);
            tamagochi.setTired(tamagochi.getTired() - 10);
            tamagochi.setHp(tamagochi.getHp() - 10);
            tamagochi.setBore(tamagochi.getBore() - 10);
            tamagochi.setHunger(tamagochi.getHunger() - 10);
        } else if (secondsAlive <= 240) {
            tamagochi.setHappy(tamagochi.getHappy() - 15);
            tamagochi.setTired(tamagochi.getTired() - 15);
            tamagochi.setHp(tamagochi.getHp() - 15);
            tamagochi.setBore(tamagochi.getBore() - 15);
            tamagochi.setHunger(tamagochi.getHunger() - 15);
        }
        else {
            tamagochi.setHappy(tamagochi.getHappy() - 20);
            tamagochi.setTired(tamagochi.getTired() - 20);
            tamagochi.setHp(tamagochi.getHp() - 20);
            tamagochi.setBore(tamagochi.getBore() - 20);
            tamagochi.setHunger(tamagochi.getHunger() - 20);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDb();
    }
}