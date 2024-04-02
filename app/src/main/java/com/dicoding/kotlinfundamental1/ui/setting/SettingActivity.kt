package com.dicoding.kotlinfundamental1.ui.setting

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.kotlinfundamental1.databinding.ActivitySettingBinding
import com.dicoding.kotlinfundamental1.ui.main.ViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val settingViewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingViewModel.getThemeSettings().observe(this) { checkTheme: Boolean ->
            binding.switchTheme.isChecked = checkTheme
            if (checkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}