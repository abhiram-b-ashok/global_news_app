package com.example.newsapplication.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentSavedNewsBinding



class SavedNewsFragment : Fragment() {
   private lateinit var binding: FragmentSavedNewsBinding

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

    }


}