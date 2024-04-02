package com.dicoding.kotlinfundamental1.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kotlinfundamental1.databinding.ActivityFavoriteBinding
import com.dicoding.kotlinfundamental1.ui.main.ViewModelFactory
import com.dicoding.kotlinfundamental1.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.userGithub.layoutManager = layoutManager

        adapter = FavoriteAdapter({
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, it.username)
            startActivity(intent)
        }, {
            favoriteViewModel.deleteAllFavorite(it.username.toString())
            favoriteViewModel.getAllFavorite().observe(this) { result ->
                adapter.submitList(result)
            }
        })

        binding.userGithub.adapter = adapter
        favoriteViewModel.getAllFavorite().observe(this){result ->
            binding.progressBar.isGone = true
            adapter.submitList(result)
            if(result.isEmpty()){
                binding.tvEmpty.isVisible = true
            }
        }
    }
}