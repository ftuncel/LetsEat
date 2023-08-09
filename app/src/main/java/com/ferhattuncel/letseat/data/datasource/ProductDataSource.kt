package com.ferhattuncel.letseat.data.datasource

import android.util.Log
import com.ferhattuncel.letseat.data.entity.Cart
import com.ferhattuncel.letseat.data.entity.Product
import com.ferhattuncel.letseat.retrofit.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ProductDataSource(var productDao: ProductDao) {
    val USER_NAME = "ftuncel"
    suspend fun incQuantity(quantity:Int) : Int =
        withContext(Dispatchers.IO){
            return@withContext (quantity + 1)
        }

    suspend fun decQuantity(quantity:Int) : Int =
        withContext(Dispatchers.IO){
            var newQuantity = quantity - 1
            if (newQuantity < 1) {
                newQuantity = 1
            }
            return@withContext newQuantity
        }

    suspend fun addToCart(id:Int,name:String,pic:String,price:Int,quantity:Int) {
        Log.d("FTLOG", "ProductDataSource addToCart - BEGIN")

        val oldCartItemList = loadCart() ?: emptyList()
        Log.d("FTLOG", "ProductDataSource.addToCart - Old list= " + oldCartItemList.toString())
        var isAlreadyExist = false
        var oldCartItemQuantity = 0
        var oldCartItemId = 0

        if (oldCartItemList.isNotEmpty()){
            for(c:Cart in oldCartItemList){
                if (c.name == name && c.pic == pic && c.price == price){
                    Log.i("FTLOG", "ProductDataSource.addToCart - Same product already exist on cart: [" + c.name + " , " + c.quantity + "]")
                    oldCartItemQuantity = c.quantity
                    oldCartItemId = c.id
                    isAlreadyExist = true
                    break
                }
            }
        }
        if(isAlreadyExist){
            removeFromCart(oldCartItemId, USER_NAME)
            val newQuantity = quantity + oldCartItemQuantity
            productDao.addToCart(name, pic, price, newQuantity, USER_NAME)
            Log.i("FTLOG", "ProductDataSource.addToCart - Product combined: [" + name + " , " + newQuantity + "]")
        }else{
            productDao.addToCart(name, pic, price, quantity, USER_NAME)
            Log.i("FTLOG", "ProductDataSource.addToCart - New product added: [" + name + " , " + quantity + "]")
        }
        Log.d("FTLOG", "ProductDataSource addToCart - END")
    }

    suspend fun removeFromCart(id:Int, username:String){
        Log.d("FTLOG", "ProductDataSource removeFromCart id= " + id)
        productDao.removeCartItem(id, username)
    }

    suspend fun loadCart() : List<Cart> =
        withContext(Dispatchers.IO){
            Log.d("FTLOG", "ProductDataSource loadCart")
            try {
                val loadCartItemsListResponse = productDao.loadCartItemsList(USER_NAME)
                Log.d("FTLOG", "loadCart - loadCartItemsListResponse : " + loadCartItemsListResponse.toString())
                return@withContext loadCartItemsListResponse?.sepet_yemekler ?: emptyList()
            } catch (e: IOException) {
                Log.e("FTLOG","loadCart IOException ", e)
                return@withContext emptyList<Cart>()
            } catch (e: HttpException) {
                Log.e("FTLOG","loadCart HttpException ", e)
                return@withContext emptyList<Cart>()
            }
    }
    suspend fun loadMenu() : List<Product> =
        withContext(Dispatchers.IO){
            Log.d("FTLOG","ProductDataSource loadMenuItems")
            return@withContext productDao.loadMenuItems().yemekler
        }

    suspend fun cleanCart() {
        Log.d("FTLOG","ProductDataSource cleanCart")
        val cartItemList = loadCart() ?: emptyList()

        if (cartItemList.isNotEmpty()){
            for(c:Cart in cartItemList){
                removeFromCart(c.id, USER_NAME)
            }
        }
    }
    suspend fun confirmCart() {
        Log.d("FTLOG","ProductDataSource confirmCart")
        // confirm order code can be implmented here. There is no need now for this project.
        // just clean cart for now
        cleanCart()
    }

}