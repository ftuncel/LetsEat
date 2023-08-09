package com.ferhattuncel.letseat.data.repo

import com.ferhattuncel.letseat.data.datasource.ProductDataSource
import com.ferhattuncel.letseat.data.entity.Cart
import com.ferhattuncel.letseat.data.entity.Product

class ProductRepository (var productDataSource: ProductDataSource){

    suspend fun incQuantity(quantity:Int) : Int {
        return productDataSource.incQuantity(quantity)
    }

    suspend fun decQuantity(quantity:Int) : Int {
        return productDataSource.decQuantity(quantity)
    }

    suspend fun addToCart(id:Int, name:String, pic:String, price:Int, quantity:Int) {
        productDataSource.addToCart(id,name,pic,price,quantity)
    }

    suspend fun removeFromCart(id:Int, username:String) {
        productDataSource.removeFromCart(id,username)
    }

    suspend fun loadCart() : List<Cart>{
        return productDataSource.loadCart()
    }

    suspend fun loadMenu() : List<Product>{
        return productDataSource.loadMenu()
    }

    suspend fun cleanCart() {
        productDataSource.cleanCart()
    }

    suspend fun confirmCart() {
        productDataSource.confirmCart()
    }
}