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
import com.example.newsapplication.data.models.newstype.newsTypeList
import com.example.newsapplication.data.models.topnews.NewsContract
import com.example.newsapplication.data.models.topnews.NewsViewAll
import com.example.newsapplication.data.network.NetworkResult
import com.example.newsapplication.data.network.apis.top_head_lines.setCategory
import com.example.newsapplication.repository.home.HomeRepository
import com.example.newsapplication.ui.home.adapters.topnews.TopNewsAdapter
import com.example.newsapplication.ui.home.adapters.newstype.NewsTypeAdapter
import com.example.newsapplication.ui.home.adapters.topheadlines.TopHeadlinesAdapter
import com.example.newsapplication.ui.info_dialog.showInfoDialog
import com.example.newsapplication.utils.toast
import com.example.newsapplication.viewmodels.home.HomeViewModel
import com.example.newsapplication.viewmodels.home.HomeViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var topNewsAdapter: TopNewsAdapter
    private lateinit var topHeadlinesAdapter: TopHeadlinesAdapter
    private lateinit var newsTypeAdapter: NewsTypeAdapter
    private var selectedCategory: String? = null
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
            newsTypeLoad()

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private suspend fun newsTypeLoad()
    {
        showNewsTypeProgressBar()
        binding.recyclerViewNewsType.visibility = View.GONE
        delay(2000)
        newsTypeAdapter = NewsTypeAdapter(newsTypeList)
        binding.recyclerViewNewsType.adapter = newsTypeAdapter.apply {
            onTypeSelected = { position, item ->
                item.isSelected = true

                newsTypeList.forEach {
                    if (it != item) {
                        it.isSelected = false
                    }
                    selectedCategory = setCategory(position)
                }
                newsTypeAdapter.notifyDataSetChanged()
                viewModel.getTopHeadlines(selectedCategory)
            }

        }
        hideNewsTypeProgressBar()
        binding.recyclerViewNewsType.visibility = View.VISIBLE

    }


    private fun initViews() = binding.apply {

        imgSearch.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToTestFragment())
        }
        textLayoutToolbarTitle.setOnClickListener {
            toast(viewModel.getUsername())
        }
        observeTopHeadLines()
        observeTopNews()
        viewModel.saveUsername("GEORGE SIR")
        viewModel.getTopHeadlines(selectedCategory)
        viewModel.getTopNews()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun observeTopHeadLines() {
        viewModel.topHeadLinesLiveData.observe(viewLifecycleOwner) {
            when (it) {

                is NetworkResult.Success -> {
                    hideProgressBar()
                    val articles = it.data?.articles ?: emptyList()
                    topHeadlinesAdapter = TopHeadlinesAdapter(articles)
                    binding.recyclerViewTopHeadlines.adapter = topHeadlinesAdapter
                    binding.recyclerViewTopHeadlines.visibility = View.VISIBLE
                    topHeadlinesAdapter.apply {
                        itemOnclickListener = {
                            Log.e("yugygb","ihik")
                        }
                        onSavedClickListener = { position, item ->
                            articles.forEachIndexed{
                                    index, element ->
                                if (index == position){
                                    element.isSaved = !element.isSaved
                                }
                            }
                            notifyDataSetChanged()
                        }

                    }

                }

                is NetworkResult.Error -> {
                    showProgressBar()
                    showInfoDialog(it.message ?: "Something went wrong")
                }

                is NetworkResult.Loading -> {
                    showProgressBar()
                    binding.recyclerViewTopHeadlines.visibility = View.GONE
                }
            }
        }
    }

    private fun observeTopNews() {
        viewModel.topNewsLiveData.observe(viewLifecycleOwner) {

            when (it) {

                is NetworkResult.Success -> {

                    hideNewsTypeProgressBar()
                    val articles = it.data?.articles ?: emptyList()
                    val newsList: MutableList<NewsContract> = articles.toMutableList()
                    newsList.add(NewsViewAll(navigationIdentifier = "View All"))
                    Log.e("list", "<<<<< ${it.data?.articles}")
                    topNewsAdapter = TopNewsAdapter(newsList)
                    binding.recyclerViewNews.adapter = topNewsAdapter
                    binding.recyclerViewNews.visibility = View.VISIBLE
                    topNewsAdapter.apply {
                        onItemClickListener = {
                            findNavController().navigate(HomeFragmentDirections.actionHomeToEverythingFragment())
                        }
                    }


                }

                is NetworkResult.Error -> {
                    showNewsTypeProgressBar()
                    showInfoDialog(it.message ?: "Something went wrong")
                }

                is NetworkResult.Loading -> {
                    showNewsTypeProgressBar()
                    binding.recyclerViewNews.visibility = View.GONE
                }
            }
        }
    }


    private fun showProgressBar() = binding.apply {
        shimmerTopNewsLayout.visibility = View.VISIBLE
    }

    private fun hideProgressBar() = binding.apply {
        shimmerTopNewsLayout.visibility = View.GONE
    }

  private fun showNewsTypeProgressBar() = binding.apply {
        shimmerNewsTypeLayout.visibility = View.VISIBLE
    }

    private fun hideNewsTypeProgressBar() = binding.apply {
        shimmerNewsTypeLayout.visibility = View.GONE
    }

    private fun showNewsProgressBar() = binding.apply {
        shimmerNewsLayout.visibility = View.VISIBLE

    }
    private fun hideNewsProgressBar() = binding.apply {
        shimmerNewsLayout.visibility = View.GONE
    }


}