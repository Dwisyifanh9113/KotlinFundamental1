package com.dicoding.kotlinfundamental1.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kotlinfundamental1.databinding.FragmentFollowBinding
import com.dicoding.kotlinfundamental1.ui.main.ViewModelFactory

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserAdapter
    private val followViewModel: FollowViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "position"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_POSITION, 0)
        val username = requireActivity().intent.getStringExtra(DetailActivity.EXTRA_LOGIN)

        val layoutManager = LinearLayoutManager(context)
        binding.follow.layoutManager = layoutManager

        adapter = UserAdapter {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, it.login)
            startActivity(intent)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.follow.adapter = adapter
        followViewModel.isEmpty.observe(viewLifecycleOwner) {
            binding.tvEmpty.visibility = if (it) View.VISIBLE else View.GONE
        }

        followViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        if (index == 1) {
            followViewModel.getFollowers(username.toString())
            followViewModel.listFollowers.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        } else {
            followViewModel.getFollowings(username.toString())
            followViewModel.listFollowing.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}