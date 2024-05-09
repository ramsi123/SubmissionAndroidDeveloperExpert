package com.example.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.source.remote.response.Users
import com.example.core.domain.model.Result
import com.example.core.ui.ListUserAdapter
import com.example.core.util.Constants
import com.example.favorite.databinding.ActivityFavoriteUserBinding
import com.example.favorite.di.favoriteModule
import com.example.submissionandroiddeveloperexpert.detail.DetailUserActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var binding: ActivityFavoriteUserBinding
    private val favoriteUserViewModel: FavoriteUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // load koin modules
        loadKoinModules(favoriteModule)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // setting up recycler view
        setRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        // observing list of favorite users
        lifecycleScope.launch {
            favoriteUserViewModel.getFavoriteUsers().observe(this@FavoriteUserActivity) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val usersData = result.data

                            val items = mutableListOf<Users>()
                            usersData.map {
                                val item = Users(username = it.username, avatarUrl = it.avatarUrl)
                                items.add(item)
                            }
                            getFavoriteUsers(items)
                        }

                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@FavoriteUserActivity,
                                "Error: ${result.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavoriteUsers.addItemDecoration(itemDecoration)
    }

    private fun getFavoriteUsers(usersData: List<Users>) {
        adapter = ListUserAdapter {
            val moveToDetailUser = Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
            moveToDetailUser.putExtra(Constants.EXTRA_USER, it.username)
            startActivity(moveToDetailUser)
        }
        adapter.submitList(usersData)
        binding.rvFavoriteUsers.adapter = adapter
    }
}