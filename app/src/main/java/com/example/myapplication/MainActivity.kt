package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateHandle
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val myViewModel = MyViewModel(application, handle = SavedStateHandle());
            val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
            binding.data = myViewModel;
            binding.lifecycleOwner = this;
        }
}
