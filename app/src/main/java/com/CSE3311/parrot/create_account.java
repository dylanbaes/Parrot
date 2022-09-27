package com.CSE3311.parrot;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.CSE3311.parrot.databinding.ActivityCreateAccountBinding;

public class create_account extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}