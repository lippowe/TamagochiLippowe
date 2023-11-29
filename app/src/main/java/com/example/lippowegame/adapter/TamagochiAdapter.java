package com.example.lippowegame.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lippowegame.R;
import com.example.lippowegame.databinding.ActivityDeadTamogochiBinding;
import com.example.lippowegame.databinding.ActivityMainBinding;
import com.example.lippowegame.databinding.ActivityStartGameBinding;

import com.example.lippowegame.gameclass.Tamagochi;

import java.util.List;

public class TamagochiAdapter extends RecyclerView.Adapter<TamagochiAdapter.ViewHolder> {
    List<Tamagochi> tamagochiList;
    LayoutInflater layoutInflater;

    public TamagochiAdapter(Context context, List<Tamagochi> tamagochiList){
        this.layoutInflater = LayoutInflater.from(context);
        this.tamagochiList = tamagochiList;
    }
    @NonNull
    @Override
    public TamagochiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_tamagochi, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TamagochiAdapter.ViewHolder holder, int position) {
        Tamagochi tamagochi = tamagochiList.get(position);
        holder.tvId.setText("ID: " + tamagochi.getId() + "");
        holder.tvHealth.setText("Здоровье: " + tamagochi.getHp() + "");
        holder.tvHappy.setText("Счастье: " + tamagochi.getHappy() + "");
        holder.tvHungry.setText("Сытость: " + tamagochi.getHunger() + "");
        holder.tvBore.setText("Веселье: " + tamagochi.getBore() + "");
        holder.tvTired.setText("Бодрость: " + tamagochi.getTired() + "");
        holder.tvTime.setText("Время жизни: " + timeToString(tamagochi.getDays()));
    }

    @Override
    public int getItemCount() {
        return tamagochiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvTime, tvHealth, tvHappy, tvHungry, tvBore, tvTired;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textViewID);
            tvBore = itemView.findViewById(R.id.textViewBore);
            tvHappy = itemView.findViewById(R.id.textViewHappy);
            tvHealth = itemView.findViewById(R.id.textViewHealth);
            tvTime = itemView.findViewById(R.id.textViewTime);
            tvTired = itemView.findViewById(R.id.textViewTired);
            tvHungry = itemView.findViewById(R.id.textViewHungry);
        }
    }

    @SuppressLint("DefaultLocale")
    public String timeToString(int secondsAlive){
        long hour = secondsAlive / 3600,
                min = secondsAlive / 60 % 60,
                sec = secondsAlive / 1 % 60;
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }
}
