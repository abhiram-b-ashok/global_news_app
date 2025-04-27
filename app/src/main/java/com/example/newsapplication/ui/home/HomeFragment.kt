package com.example.newsapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.databinding.FragmentHomeBinding
import com.example.newsapplication.data.models.news.newsList
import com.example.newsapplication.ui.home.adapters.news.NewsAdapter


class HomeFragment : Fragment() {
   private lateinit var binding:FragmentHomeBinding
   private lateinit var adapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = binding.apply {
        adapter = NewsAdapter(list = newsList)
        recyclerViewNews.adapter = adapter
       // toast("home_fragment")

        imgSearch.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeToTestFragment())
        }
    }


}