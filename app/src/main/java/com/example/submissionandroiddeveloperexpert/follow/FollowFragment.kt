package com.example.submissionandroiddeveloperexpert.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.model.Result
import com.example.core.data.source.remote.response.Users
import com.example.core.ui.ListUserAdapter
import com.example.core.util.Constants.ARG_SECTION_NUMBER
import com.example.core.util.Constants.ARG_USERNAME
import com.example.submissionandroiddeveloperexpert.databinding.FragmentFollowBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowFragment : Fragment() {

    private lateinit var adapter: ListUserAdapter
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get position and username
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val name = arguments?.getString(ARG_USERNAME)

        // setting up view model
        val followViewModel: FollowViewModel by requireActivity().viewModels()

        // setting up recycler view
        setRecyclerView()

        // show following and followers data
        if (index == 0) {
            lifecycleScope.launch {
                followViewModel.getUserFollowing(username = name!!)
                    .observe(requireActivity()) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    binding?.progressBar?.visibility = View.VISIBLE
                                }

                                is Result.Success -> {
                                    binding?.progressBar?.visibility = View.GONE
                                    val usersData = result.data
                                    getUserFollowing(usersData)
                                }

                                is Result.Error -> {
                                    binding?.progressBar?.visibility = View.GONE
                                    Toast.makeText(
                                        requireContext(),
                                        "Error: ${result.error}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            }
        } else {
            lifecycleScope.launch {
                followViewModel.getUserFollowers(username = name!!)
                    .observe(requireActivity()) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    binding?.progressBar?.visibility = View.VISIBLE
                                }

                                is Result.Success -> {
                                    binding?.progressBar?.visibility = View.GONE
                                    val usersData = result.data
                                    getUserFollowers(usersData)
                                }

                                is Result.Error -> {
                                    binding?.progressBar?.visibility = View.GONE
                                    Toast.makeText(
                                        requireContext(),
                                        "Error: ${result.error}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollow?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvFollow?.addItemDecoration(itemDecoration)
        adapter = ListUserAdapter {}
    }

    private fun getUserFollowing(usersData: List<Users>) {
        adapter.submitList(usersData)
        binding?.rvFollow?.adapter = adapter
    }

    private fun getUserFollowers(usersData: List<Users>) {
        adapter.submitList(usersData)
        binding?.rvFollow?.adapter = adapter
    }

}