package com.example.submissionandroiddeveloperexpert.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.source.remote.response.Users
import com.example.core.ui.ListUserAdapter
import com.example.core.util.Constants.EXTRA_USER
import com.example.submissionandroiddeveloperexpert.R
import com.example.submissionandroiddeveloperexpert.databinding.ActivityMainBinding
import com.example.submissionandroiddeveloperexpert.detail.DetailUserActivity
import com.example.submissionandroiddeveloperexpert.settings.SettingActivity
import com.example.core.domain.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setRecyclerList()

        // setting up view model
        val mainViewModel: MainViewModel by viewModels()

        // observing search users
        searchUsers(viewModel = mainViewModel, username = "a")

        // setup search bar and search view
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    searchUsers(viewModel =  mainViewModel, username = searchBar.text.toString())
                    false
                }
        }

        // menu in search bar
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val uri = Uri.parse("githubapp://favorite")
                    val moveToFavoriteUsers = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(Intent(moveToFavoriteUsers))
                    true
                }
                R.id.menu2 -> {
                    val moveToSetting = Intent(this@MainActivity, SettingActivity::class.java)
                    startActivity(moveToSetting)
                    true
                }
                else -> false
            }
        }
    }

    // get data list from adapter
    private fun getUsersData(usersData: List<Users>) {
        val adapter = ListUserAdapter {
            val moveToDetailUser = Intent(this@MainActivity, DetailUserActivity::class.java)
            moveToDetailUser.putExtra(EXTRA_USER, it.username)
            startActivity(moveToDetailUser)
        }
        adapter.submitList(usersData)
        binding.rvSearchUsers.adapter = adapter
    }

    // observing search users from view model
    private fun searchUsers(viewModel: MainViewModel, username: String) {
        lifecycleScope.launch {
            viewModel.findSearchUsers(username).observe(this@MainActivity) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val usersData = result.data
                            getUsersData(usersData)
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@MainActivity,
                                "Error: ${result.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setRecyclerList() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvSearchUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvSearchUsers.addItemDecoration(itemDecoration)
    }
}