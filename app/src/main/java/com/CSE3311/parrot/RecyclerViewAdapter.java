package com.CSE3311.parrot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> surface;

    public RecyclerViewAdapter(ArrayList<String> list, Context context) {
        this.surface = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_surface, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.surfaceCategories.setText(surface.get(position));
    }

    @Override
    public int getItemCount() {
        return surface.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView surfaceCategories;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            surfaceCategories = itemView.findViewById(R.id.surfaceCategory);
        }
    }
}
