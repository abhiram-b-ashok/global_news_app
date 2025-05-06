package com.example.newsapplication.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.data.models.newstype.newsTypeList
import com.example.newsapplication.data.models.topnews.NewsContract
import com.example.newsapplication.data.models.topnews.NewsViewAll
import com.example.newsapplication.data.network.NetworkResult
import com.example.newsapplication.data.network.apis.top_head_lines.setCategory
import com.example.newsapplication.data.room_database.SavedNewsDatabase
import com.example.newsapplication.data.room_database.SavedNewsModel
import com.example.newsapplication.databinding.FragmentHomeBinding
import com.example.newsapplication.repository.home.HomeRepository
import com.example.newsapplication.ui.home.adapters.newstype.NewsTypeAdapter
import com.example.newsapplication.ui.home.adapters.topheadlines.TopHeadlinesAdapter
import com.example.newsapplication.ui.home.adapters.topnews.TopNewsAdapter
import com.example.newsapplication.ui.info_dialog.showInfoDialog
import com.example.newsapplication.utils.errorLog
import com.example.newsapplication.utils.hide
import com.example.newsapplication.utils.show
import com.example.newsapplication.utils.toast
import com.example.newsapplication.viewmodels.home.HomeViewModel
import com.example.newsapplication.viewmodels.home.HomeViewModelFactory
import com.example.newsapplication.viewmodels.savedNews.SavedNewsViewModel
import com.example.newsapplication.viewmodels.savedNews.SavedNewsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var topNewsAdapter: TopNewsAdapter
    private lateinit var topHeadlinesAdapter: TopHeadlinesAdapter
    private lateinit var newsTypeAdapter: NewsTypeAdapter
    private var selectedCategory: String? = null
    private lateinit var savedNewsViewModel: SavedNewsViewModel
    private var savedNews: List<SavedNewsModel> = listOf()
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


        val dao = SavedNewsDatabase.getDatabase(requireContext()).savedNewsDao()
        val factory = SavedNewsViewModelFactory(dao)
        savedNewsViewModel =
            ViewModelProvider(this@HomeFragment, factory)[SavedNewsViewModel::class.java]

        lifecycleScope.launch {
            savedNews = withContext(Dispatchers.IO) {
                savedNewsViewModel.getAllSavedNews()
            }
            initViews()
            newsTypeLoad()

        }


    }


    @SuppressLint("NotifyDataSetChanged")
    private suspend fun newsTypeLoad() {
        showNewsTypeProgressBar()
        binding.recyclerViewNewsType.visibility = View.GONE
        delay(1000)
        newsTypeList.forEachIndexed { index, item ->
            item.isSelected = index == 0
        }
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
            findNavController().navigate(HomeFragmentDirections.actionHomeToDiscover())
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
                    hideNewsProgressBar()
                    val articles = it.data?.articles ?: emptyList()
                    val savedUrls = savedNews.map { it.url }.toSet()
                    articles.forEach { article ->
                        article.isSaved = savedUrls.contains(article.url)
                    }
                    topHeadlinesAdapter = TopHeadlinesAdapter(articles)
                    binding.recyclerViewTopHeadlines.adapter = topHeadlinesAdapter
                    binding.recyclerViewTopHeadlines.visibility = View.VISIBLE
                    topHeadlinesAdapter.apply {

                        onSavedClickListener = { position, item ->
                            lifecycleScope.launch {

                                item.isSaved = !item.isSaved
                                topHeadlinesAdapter.notifyItemChanged(position)
                                notifyItemChanged(position)

                                notifyItemChanged(position)
                                val savedModel = SavedNewsModel(
                                    title = item.title,
                                    urlToImage = item.urlToImage,
                                    publishedAt = item.publishedAt,
                                    url = item.url,
                                    isSaved = item.isSaved
                                )

                                if (item.isSaved) {
                                    savedNewsViewModel.saveNews(savedModel)
                                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                                } else {

                                    savedNewsViewModel.unSaveNews(savedModel)
                                    val newsToUnSave =
                                        savedNewsViewModel.getNewsByUrl(item.url ?: "")
                                    if (newsToUnSave != null) {
                                        savedNewsViewModel.unSaveNews(newsToUnSave)
                                        Log.e("unsaved", "$newsToUnSave")
                                    } else {
                                        Log.e(
                                            "HomeFragment",
                                            "Item not found in database for deletion"
                                        )
                                    }
                                }
                            }

                        }
                        itemOnclickListener = {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeToWebViewFragment(
                                    it.url ?: ""
                                )
                            )
                        }


                    }

                }

                is NetworkResult.Error -> {
                    showNewsProgressBar()
                    showInfoDialog(it.message ?: "Something went wrong")
                }

                is NetworkResult.Loading -> {
                    showNewsProgressBar()
                    binding.recyclerViewTopHeadlines.visibility = View.GONE
                }
            }
        }
    }

    private fun observeTopNews() {
        viewModel.topNewsLiveData.observe(viewLifecycleOwner) {

            when (it) {

                is NetworkResult.Success -> {

                    hideProgressBar()
                    val articles = it.data?.articles ?: emptyList()
                    val newsList: MutableList<NewsContract> = articles.toMutableList()
                    newsList.add(NewsViewAll(navigationIdentifier = "View All"))

                    //  Log.e("list", "<<<<< ${it.data?.articles}")
                    errorLog("<<<< ${it.data?.articles}")

                    topNewsAdapter = TopNewsAdapter(newsList)
                    binding.apply {
                        recyclerViewNews.adapter = topNewsAdapter
                        recyclerViewNews.show()
                    }
                    topNewsAdapter.apply {
                        onItemClickListener = {
                            findNavController().navigate(HomeFragmentDirections.actionHomeToEverythingFragment())
                        }
                    }
                }

                is NetworkResult.Error -> {
                    showProgressBar()
                    showInfoDialog(it.message ?: "Something went wrong")
                }

                is NetworkResult.Loading -> {
                    showProgressBar()
                    binding.recyclerViewNews.hide()
                }
            }
        }
    }


    private fun showProgressBar() = binding.apply {
        shimmerTopNewsLayout.show()
    }

    private fun hideProgressBar() = binding.apply {
        shimmerTopNewsLayout.hide()
    }

    private fun showNewsTypeProgressBar() = binding.apply {
        shimmerNewsTypeLayout.show()
    }

    private fun hideNewsTypeProgressBar() = binding.apply {
        shimmerNewsTypeLayout.hide()
    }

    private fun showNewsProgressBar() = binding.apply {
        shimmerNewsLayout.show()

    }

    private fun hideNewsProgressBar() = binding.apply {
        shimmerNewsLayout.hide()
    }


}