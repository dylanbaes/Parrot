package com.CSE3311.parrot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.CSE3311.parrot.databinding.ActivityCreateAccountBinding;

public class create_account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCreateAccountBinding binding;
        super.onCreate(savedInstanceState);

        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}