package com.example.newsapplication.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.data.room_database.SavedNewsDatabase
import com.example.newsapplication.databinding.FragmentSavedNewsBinding
import com.example.newsapplication.ui.saved.adapter.SavedNewsAdapter
import com.example.newsapplication.viewmodels.savedNews.SavedNewsViewModel
import com.example.newsapplication.viewmodels.savedNews.SavedNewsViewModelFactory
import kotlinx.coroutines.launch


class SavedNewsFragment : Fragment() {
   private lateinit var binding: FragmentSavedNewsBinding
   private lateinit var savedViewModel: SavedNewsViewModel
   private lateinit var savedAdapter: SavedNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgSearch.setOnClickListener{
            findNavController().navigate(SavedNewsFragmentDirections.actionSavedToDiscover())
        }
        lifecycleScope.launch {

        initViews()

        }

    }
    fun initViews(){
        val dao = SavedNewsDatabase.getDatabase(requireContext()).savedNewsDao()
        savedViewModel = ViewModelProvider(this, SavedNewsViewModelFactory(dao))[SavedNewsViewModel::class.java]
        savedViewModel.getSavedNews { savedNewsList ->
            savedAdapter = SavedNewsAdapter(savedNewsList)
            binding.savedNewsRecycler.adapter = savedAdapter
            savedAdapter.onUnSaveClickListener = { item, position ->
                savedViewModel.unSaveNews(item)
                savedAdapter.notifyItemRemoved(position)
            }

        }
    }


}