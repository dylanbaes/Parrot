package com.CSE3311.parrot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CSE3311.parrot.Models.Expense;

import java.util.ArrayList;

public class expenseAdapter extends RecyclerView.Adapter <expenseAdapter.MyViewHolder>{

    private ArrayList<Expense> expenses;
    private RecyclerViewClickListener listener;

    expenseAdapter(ArrayList<Expense> expenseList, RecyclerViewClickListener listener) {
        this.expenses = expenseList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView entryText;

        public MyViewHolder(final View view) {
            super(view);
            entryText = view.findViewById(R.id.nestedItemTv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public expenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull expenseAdapter.MyViewHolder holder, int position) {
        String entry = expenses.get(position).getCategoryName();
        holder.entryText.setText(entry);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
