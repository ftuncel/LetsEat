package com.ferhattuncel.letseat.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferhattuncel.letseat.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor (var productRepository: ProductRepository) : ViewModel() {
    var itemQuantity = MutableLiveData( 1)

    fun incQuantity(quantity:Int){
        CoroutineScope(Dispatchers.Main).launch{
            itemQuantity.value = productRepository.incQuantity(quantity)
        }
    }
    fun decQuantity(quantity:Int){
        CoroutineScope(Dispatchers.Main).launch{
            itemQuantity.value = productRepository.decQuantity(quantity)
        }
    }

    fun addToCart(id:Int, name:String, pic:String, price:Int, quantity:Int){
        CoroutineScope(Dispatchers.Main).launch {
            productRepository.addToCart(id, name, pic, price, quantity)
        }
    }

    fun cleanCart(){
        CoroutineScope(Dispatchers.Main).launch {
            productRepository.cleanCart()
        }
    }
}