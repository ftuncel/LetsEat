package com.ferhattuncel.letseat.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferhattuncel.letseat.data.entity.Product
import com.ferhattuncel.letseat.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (var itemRepository: ProductRepository): ViewModel() {
    var itemsList = MutableLiveData<List<Product>>()
    private var allItems : List<Product> = listOf()

    init {
        loadMenuItems()
    }
    fun loadMenuItems(){
        CoroutineScope(Dispatchers.Main).launch {
            allItems = itemRepository.loadMenu()
            itemsList.value = allItems
        }
    }
    fun filterList(query:String){
        CoroutineScope(Dispatchers.Main).launch{
            val filteredItems = if (query.isEmpty()) {
                allItems
            } else {
                allItems.filter { item -> item.name.contains(query, ignoreCase = true) }
            }
            itemsList.value = filteredItems
        }
    }
}