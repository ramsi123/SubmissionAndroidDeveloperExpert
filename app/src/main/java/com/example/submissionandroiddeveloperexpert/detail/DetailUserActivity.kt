package com.example.submissionandroiddeveloperexpert.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.core.domain.model.Result
import com.example.core.data.source.local.entity.FavoriteUserEntity
import com.example.core.data.source.remote.response.DetailUser
import com.example.submissionandroiddeveloperexpert.follow.SectionsPagerAdapter
import com.example.core.util.Constants.EXTRA_USER
import com.example.core.util.Constants.TAB_TITLES
import com.example.submissionandroiddeveloperexpert.R
import com.example.submissionandroiddeveloperexpert.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailUserActivity : AppCompatActivity() {

    private var user: String? = null
    private var username: String = ""
    private var avatarUrl: String = ""
    private var isFavorite = 0
    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // declare SectionsPagerAdapter
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        // connects ViewPager2 and SectionsPagerAdapter
        binding.viewPager.adapter = sectionsPagerAdapter

        // connects ViewPager2 and Tablayout
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        // Get intent from MainActivity
        user = intent.getStringExtra(EXTRA_USER)

        // send data from DetailUserActivity to SectionsPagerAdapter, then FollowFragment
        sectionsPagerAdapter.username = user.toString()

        // observe detail user
        lifecycleScope.launch {
            detailUserViewModel.getDetailUser(username = user!!).observe(this@DetailUserActivity) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val userData = result.data
                            setDetailUser(userData)
                            Log.i("MyInfo", "$username $avatarUrl")
                        }

                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@DetailUserActivity,
                                "Error: ${result.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        // observe favorite user
        lifecycleScope.launch {
            detailUserViewModel.isUserFavorite(username = user!!).observe(this@DetailUserActivity) { result ->
                if (Result != null) {
                    when (result) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            isFavorite = result.data
                            setFavoriteIcon(isFavorite)
                        }
                        is Result.Error -> {
                            Toast.makeText(
                                this@DetailUserActivity,
                                "Error: ${result.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        // add or delete favorite user
        binding.fabAddFavorite.setOnClickListener {
            if (isFavorite == 1) {
                isFavorite = 0
                setFavoriteIcon(isFavorite)
                detailUserViewModel.deleteFavoriteUser(username)
            } else {
                isFavorite = 1
                setFavoriteIcon(isFavorite)
                detailUserViewModel.insertFavoriteUser(
                    FavoriteUserEntity(
                        username = username,
                        avatarUrl = avatarUrl
                    )
                )
            }
        }
    }

    private fun setDetailUser(user: DetailUser) {
        username = user.username
        avatarUrl = user.avatarUrl

        binding.tvUsername.text = user.username
        binding.tvName.text = user.name
        binding.tvBio.text = user.bio
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.imgProfile)
        binding.tvFollowing.text = resources.getString(R.string.data_following, user.following)
        binding.tvFollowers.text = resources.getString(R.string.data_followers, user.followers)
    }

    private fun setFavoriteIcon(isFavorite: Int) {
        if (isFavorite == 1) {
            binding.fabAddFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.fabAddFavorite.context,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.fabAddFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.fabAddFavorite.context,
                    R.drawable.ic_unfavorite
                )
            )
        }
    }

}
