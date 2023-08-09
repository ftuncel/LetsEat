package com.ferhattuncel.letseat.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferhattuncel.letseat.data.entity.Cart
import com.ferhattuncel.letseat.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor (var productRepository: ProductRepository): ViewModel() {
    var cartItemsList = MutableLiveData<List<Cart>?>()
    var totalOrderPrice = MutableLiveData<Int>(0)
    init {
        loadCartItemsList()
    }
    fun loadCartItemsList(){
        CoroutineScope(Dispatchers.Main).launch(){
            val updatedCartItemsList = productRepository.loadCart() ?: emptyList()
            cartItemsList.value = updatedCartItemsList
            var updatedTotalOrderPrice = 0
            if(updatedCartItemsList.isNotEmpty()){
                for(x: Cart in updatedCartItemsList){
                    updatedTotalOrderPrice += x.total
                }
            }
            totalOrderPrice.value = updatedTotalOrderPrice
        }
    }

    fun removeCartItem(cartItemId:Int,username:String){
        CoroutineScope(Dispatchers.Main).launch {
            productRepository.removeFromCart(cartItemId,username)
            loadCartItemsList()
        }
    }

    fun cleanCart(){
        CoroutineScope(Dispatchers.Main).launch {
            productRepository.cleanCart()
            loadCartItemsList()
        }
    }

    fun confirmCart(){
        CoroutineScope(Dispatchers.Main).launch {
            productRepository.confirmCart()
            loadCartItemsList()
        }
    }
}