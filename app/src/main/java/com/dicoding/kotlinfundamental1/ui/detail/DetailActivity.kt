package com.dicoding.kotlinfundamental1.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.kotlinfundamental1.R
import com.dicoding.kotlinfundamental1.data.local.FavoriteEntity
import com.dicoding.kotlinfundamental1.databinding.ActivityDetailBinding
import com.dicoding.kotlinfundamental1.ui.main.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private var user: FavoriteEntity = FavoriteEntity(0, null, null)

    companion object {
        const val EXTRA_LOGIN = "LOGIN"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_LOGIN)

        detailViewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        detailViewModel.getDetailUser(username.toString())
        detailViewModel.favoriteEntity.observe(this) {
            user = it
        }

        detailViewModel.detailUser.observe(this) {
            with(binding) {
                progressBar.isGone = true
                if (it != null) {
                    tvUsername.text = it.login
                    tvName.text = it.name
                    followersCount.text = it.followers.toString()
                    followingCount.text = it.following.toString()
                    Glide.with(binding.root)
                        .load(it.avatarUrl)
                        .into(binding.profile)
                        .clearOnDetach()
                }
            }
        }

        detailViewModel.isFavorite(username.toString()).observe(this) {
            if (it) {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_favorite_24
                    )
                )
                binding.fabFavorite.setOnClickListener {
                    detailViewModel.deleteFavorite(username.toString())
                }
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_favorite_border_24
                    )
                )
                binding.fabFavorite.setOnClickListener {
                    detailViewModel.addFavorite(user)
                }
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }
}