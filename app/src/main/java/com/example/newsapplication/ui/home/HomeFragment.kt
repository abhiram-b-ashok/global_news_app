package com.example.newsapplication.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.databinding.FragmentHomeBinding
import com.example.newsapplication.data.models.news.newsList
import com.example.newsapplication.data.models.newstype.newsTypeList
import com.example.newsapplication.data.network.NetworkResult
import com.example.newsapplication.data.network.apis.top_head_lines.getApiRequest
import com.example.newsapplication.data.network.apis.top_head_lines.setCategory
import com.example.newsapplication.repository.home.HomeRepository
import com.example.newsapplication.ui.home.adapters.news.NewsAdapter
import com.example.newsapplication.ui.home.adapters.newstype.NewsTypeAdapter
import com.example.newsapplication.ui.home.adapters.topheadlines.TopHeadlinesAdapter
import com.example.newsapplication.ui.info_dialog.showInfoDialog
import com.example.newsapplication.utils.toast
import com.example.newsapplication.viewmodels.home.HomeViewModel
import com.example.newsapplication.viewmodels.home.HomeViewModelFactory
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var topHeadlinesAdapter: TopHeadlinesAdapter
    private lateinit var newsTypeAdapter: NewsTypeAdapter
    var selectedNewsTypePosition = 0
    private val viewModel by viewModels<HomeViewModel>(
        factoryProducer = {
            HomeViewModelFactory(HomeRepository())
        }
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            initViews()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private suspend fun initViews() = binding.apply {
        newsAdapter = NewsAdapter(list = newsList)
        recyclerViewNews.adapter = newsAdapter
        newsTypeAdapter = NewsTypeAdapter(newsTypeList)
        recyclerViewNewsType.adapter = newsTypeAdapter.apply {
            onTypeSelected = { position, item ->
                item.isSelected = true
                var selected = setCategory(position)


                newsTypeList.forEach {
                    if (it != item) {
                        it.isSelected = false
                    }
                }
                newsTypeAdapter.notifyDataSetChanged()
            }

        }

        imgSearch.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToTestFragment())
        }
        textLayoutToolbarTitle.setOnClickListener {
            toast(viewModel.getUsername())
        }
        observeTopHeadLines()
        viewModel.saveUsername("Abhiram B")
        viewModel.getTopHeadlines()
    }

    private fun observeTopHeadLines() {
        viewModel.topHeadLinesLiveData.observe(viewLifecycleOwner) {
            when (it) {

                is NetworkResult.Success -> {
                    hideProgressBar()
                    Log.e("list", "<<<<< ${it.data?.articles}")
                    topHeadlinesAdapter = TopHeadlinesAdapter(it.data?.articles ?: emptyList())
                    binding.recyclerViewTopHeadlines.adapter = topHeadlinesAdapter

                }

                is NetworkResult.Error -> {
                    hideProgressBar()
                    showInfoDialog(it.message ?: "Error")
                }

                is NetworkResult.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() = binding.apply {
        progressCircular.visibility = View.VISIBLE
    }

    private fun hideProgressBar() = binding.apply {
        progressCircular.visibility = View.GONE
    }


}