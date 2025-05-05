package com.example.newsapplication.ui.everything

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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



class EverythingFragment : Fragment() {
    private lateinit var binding: FragmentEverythingBinding
    private lateinit var savedNewsViewModel: SavedNewsViewModel

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

        lifecycleScope.launch {
            observeEverything()
            viewModel.getEverything()
            val dao = SavedNewsDatabase.getDatabase(requireContext()).savedNewsDao()
            val factory = SavedNewsViewModelFactory(dao)
            savedNewsViewModel = ViewModelProvider(this@EverythingFragment, factory)[SavedNewsViewModel::class.java]

        }
    }

    private fun observeEverything() {
        viewModel.everythingLiveData.observe(viewLifecycleOwner) { it ->
            when (it) {
                is NetworkResult.Success -> {
                    hideProgressBar()
                    val articles = it.data?.articles ?: emptyList()
                    Log.e("listFromApi", "<<<<< $articles")
                    everythingAdapter = EverythingAdapter(articles)
                    binding.everythingRecyclerView.adapter = everythingAdapter
                    binding.everythingRecyclerView.visibility = View.VISIBLE
                    everythingAdapter.apply {
                        onSaveClickListener = { item, position ->

                            articles.forEachIndexed { index, it ->
                                if (index == position) {
                                    it.isSaved = !it.isSaved
                                }
                            }
                            notifyItemChanged(position)
                            Toast.makeText(
                                this@EverythingFragment.context,
                                "Saved",
                                Toast.LENGTH_SHORT
                            ).show()


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
                                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                is NetworkResult.Error -> {
                    hideProgressBar()
                }

                is NetworkResult.Loading -> {
                    showProgressBar()
                    binding.everythingRecyclerView.visibility = View.GONE
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