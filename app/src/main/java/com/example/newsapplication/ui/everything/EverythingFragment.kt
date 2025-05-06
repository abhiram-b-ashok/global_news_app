package com.example.newsapplication.ui.everything


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.data.network.NetworkResult
import com.example.newsapplication.data.room_database.SavedNewsModel
import com.example.newsapplication.databinding.FragmentEverythingBinding
import com.example.newsapplication.repository.everything.EverythingRepository
import com.example.newsapplication.ui.everything.adapter.EverythingAdapter
import com.example.newsapplication.viewmodels.everything.EverythingViewModel
import com.example.newsapplication.viewmodels.everything.EverythingViewModelFactory
import com.example.newsapplication.viewmodels.savedNews.SavedNewsViewModel
import com.example.newsapplication.viewmodels.savedNews.SavedNewsViewModelFactory
import kotlinx.coroutines.launch
import com.example.newsapplication.data.room_database.SavedNewsDatabase
import com.example.newsapplication.utils.hide
import com.example.newsapplication.utils.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class EverythingFragment : Fragment() {
    private lateinit var binding: FragmentEverythingBinding
    private lateinit var savedNewsViewModel: SavedNewsViewModel
    private var savedNews: List<SavedNewsModel> = listOf()


    private val viewModel by viewModels<EverythingViewModel>(factoryProducer = {
        EverythingViewModelFactory(
            EverythingRepository()
        )
    })
    private lateinit var everythingAdapter: EverythingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEverythingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = SavedNewsDatabase.getDatabase(requireContext()).savedNewsDao()
        val factory = SavedNewsViewModelFactory(dao)
        savedNewsViewModel =
            ViewModelProvider(this@EverythingFragment, factory)[SavedNewsViewModel::class.java]

        lifecycleScope.launch {
            savedNews = withContext(Dispatchers.IO) {
                savedNewsViewModel.getAllSavedNews()
            }
            viewModel.getEverything()
            observeEverything()

        }
    }

    private fun observeEverything() {
        viewModel.everythingLiveData.observe(viewLifecycleOwner) { it ->
            when (it) {
                is NetworkResult.Success -> {
                    hideProgressBar()
                    val articles = it.data?.articles ?: emptyList()

                    val savedUrls = savedNews.map { it.url }.toSet()
                    articles.forEach { article ->
                        article.isSaved = savedUrls.contains(article.url)
                    }

                    Log.e("listFromApi", "<<<<< $articles")
                    everythingAdapter = EverythingAdapter(articles)
                    binding.everythingRecyclerView.adapter = everythingAdapter
                    binding.everythingRecyclerView.visibility = View.VISIBLE

                    everythingAdapter.apply {

                        onSaveClickListener = { item, position ->

                            lifecycleScope.launch {
                                item.isSaved = !item.isSaved
                                everythingAdapter.notifyItemChanged(position)
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
                                    Log.e("saved", "$savedModel")
                                } else {
                                    savedNewsViewModel.unSaveNews(savedModel)
                                    val newsToUnSave =
                                        savedNewsViewModel.getNewsByUrl(item.url ?: "")
                                    if (newsToUnSave != null) {
                                        savedNewsViewModel.unSaveNews(newsToUnSave)
                                        Log.e("unsaved", "$newsToUnSave")
                                    }
                                }

                            }
                        }
                        onItemClickListener = {
                            findNavController().navigate(EverythingFragmentDirections.actionEverythingFragmentToWebViewFragment(it.url ?: ""))
                        }


                    }
                }

                is NetworkResult.Error -> {
                    showProgressBar()
                }

                is NetworkResult.Loading -> {
                    showProgressBar()
                    binding.everythingRecyclerView.visibility = View.GONE
                }
            }
        }
    }

    private fun showProgressBar() = binding.apply {
        shimmerNewsLayout.show()

    }
    private fun hideProgressBar() = binding.apply {
        shimmerNewsLayout.hide()
    }


}

