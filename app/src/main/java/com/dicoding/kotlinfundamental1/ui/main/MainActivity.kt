package com.dicoding.kotlinfundamental1.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kotlinfundamental1.R
import com.dicoding.kotlinfundamental1.databinding.ActivityMainBinding
import com.dicoding.kotlinfundamental1.ui.detail.DetailActivity
import com.dicoding.kotlinfundamental1.ui.detail.UserAdapter
import com.dicoding.kotlinfundamental1.ui.favorite.FavoriteActivity
import com.dicoding.kotlinfundamental1.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, it.login)
            startActivity(intent)
        }

        binding.userGithub.adapter = adapter

        mainViewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }

        mainViewModel.user.observe(this) {
            adapter.submitList(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    mainViewModel.findUser(searchView.text.toString())
                    searchView.hide()
                    true
                }
            topAppBar.inflateMenu(R.menu.option_menu)
            topAppBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favorite -> {
                        val action = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(action)
                    }
                    R.id.setting -> {
                        val action = Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(action)
                    }
                }
                true
            }
        }

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.userGithub.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.userGithub.addItemDecoration(itemDecoration)
    }
}