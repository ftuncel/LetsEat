package com.ferhattuncel.letseat.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ferhattuncel.letseat.R
import com.ferhattuncel.letseat.databinding.FragmentProductBinding
import com.ferhattuncel.letseat.ui.viewmodel.ProductViewModel
import com.ferhattuncel.letseat.utils.doPageTransfer
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {
    private lateinit var binding : FragmentProductBinding
    private lateinit var viewModel: ProductViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_product,container,false)
        binding.productFragmentDataBindingVariable = this
        binding.productToolbarTitle = "Ürün Detayı"

        viewModel.itemQuantity.observe(viewLifecycleOwner){
            binding.quantityDataBindingVariable = it.toString()
        }

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val bundle: ProductFragmentArgs by navArgs()
        val incomingItem = bundle.product
        binding.productEntityDataBindingVariable = incomingItem

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${incomingItem.pic}"
        Glide.with(this).load(url).override(100,100).into(binding.iwProductPicture)


        binding.btnProductAdd.setOnClickListener {
            addToCart(incomingItem.id,incomingItem.name,incomingItem.pic,incomingItem.price,binding.quantityDataBindingVariable.toString())
            Snackbar.make(it, "${binding.quantityDataBindingVariable.toString()} adet ${incomingItem.name} sepete eklendi.", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val tempViewModel: ProductViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun addToCart(id:Int, name:String, pic:String, price:Int, quantity:String){
        viewModel.addToCart(id, name, pic,price, quantity.toInt())
    }

    fun buttonIncrementClick(quantity:String){
        viewModel.incQuantity(quantity.toInt())
    }

    fun buttonDecrementClick(quantity:String){
        viewModel.decQuantity(quantity.toInt())
    }

    fun fabActionGoToCart(it:View){
        Navigation.doPageTransfer(it, R.id.goCartFromProduct)
    }
}
