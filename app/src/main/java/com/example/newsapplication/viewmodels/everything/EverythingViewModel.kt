package com.example.newsapplication.viewmodels.everything

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.models.everything.EverythingModel
import com.example.newsapplication.data.models.everything.EverythingRootModel
import com.example.newsapplication.data.models.everything.toEverythingRootModel
import com.example.newsapplication.data.network.NetworkResult
import com.example.newsapplication.repository.everything.EverythingRepository
import com.example.newsapplication.ui.everything.adapter.EverythingAdapter
import kotlinx.coroutines.launch

class EverythingViewModel(private val repository: EverythingRepository) : ViewModel() {

    private val everythingMutableLiveData =
        MutableLiveData<NetworkResult<EverythingRootModel>>(NetworkResult.Loading())

    val everythingLiveData: LiveData<NetworkResult<EverythingRootModel>> = everythingMutableLiveData



    private var everythingRootModel = EverythingRootModel(
        status = "",
        totalResults = 0,
        articles = emptyList()
    )



     fun getEverything() = viewModelScope.launch {
        everythingMutableLiveData.value = NetworkResult.Loading()
        val response = repository.getEverything()

         if (response.exception != null) {
             everythingMutableLiveData.value = NetworkResult.Error(response.message)
         } else {
             everythingRootModel =response.data.toEverythingRootModel()
             everythingMutableLiveData.value = NetworkResult.Success(everythingRootModel)


         }
    }


}