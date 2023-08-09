package com.ferhattuncel.letseat.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ferhattuncel.letseat.R
import com.ferhattuncel.letseat.data.entity.Cart
import com.ferhattuncel.letseat.databinding.CardCartBinding
import com.ferhattuncel.letseat.ui.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar

class CartAdapter (var mContext:Context, var cartItemsList:List<Cart>, var cartViewModel: CartViewModel)
    : RecyclerView.Adapter<CartAdapter.CardDesignHolder>(){
    inner class CardDesignHolder(var design:CardCartBinding) : RecyclerView.ViewHolder(design.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val binding : CardCartBinding = DataBindingUtil
            .inflate(LayoutInflater.from(mContext), R.layout.card_cart,parent,false)
        return CardDesignHolder(binding)
    }
    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {
        val cartItem = cartItemsList.get(position)
        val d = holder.design
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${cartItem.pic}"
        Glide.with(mContext).load(url).override(100,100).into(d.iwPicture)

        d.cartEntityDataBindingVariable = cartItem

        d.btnCart.setOnClickListener {
            Snackbar.make(it,"${cartItem.name} sepetten çıkarıldı.",Snackbar.LENGTH_SHORT).show()
            removeCartItem(cartItem.id,cartItem.username)
        }
    }
    fun removeCartItem(cartItemId:Int,username:String){
        cartViewModel.removeCartItem(cartItemId,username)
    }
    override fun getItemCount(): Int {
        return cartItemsList.size
    }
}