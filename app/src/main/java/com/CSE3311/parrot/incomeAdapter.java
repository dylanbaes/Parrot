package com.CSE3311.parrot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.Income;

import java.util.ArrayList;

public class incomeAdapter extends RecyclerView.Adapter <incomeAdapter.MyViewHolder>{

    private ArrayList<Income> income;

    incomeAdapter(ArrayList<Income> income) {
        this.income = income;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView entryText;

        public MyViewHolder(final View view) {
            super(view);
            entryText = view.findViewById(R.id.nestedItemTv);
        }
    }

    @NonNull
    @Override
    public incomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull incomeAdapter.MyViewHolder holder, int position) {
        String entry = income.get(position).getIncomeName();
        holder.entryText.setText(entry);
    }

    @Override
    public int getItemCount() {
        return income.size();
    }
}
