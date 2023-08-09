package com.ferhattuncel.letseat.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ferhattuncel.letseat.R
import com.ferhattuncel.letseat.databinding.FragmentMainpageBinding
import com.ferhattuncel.letseat.ui.adapter.ProductAdapter
import com.ferhattuncel.letseat.ui.viewmodel.MainViewModel
import com.ferhattuncel.letseat.utils.doPageTransfer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainpageFragment : Fragment() {
    private lateinit var binding: FragmentMainpageBinding
    private lateinit var viewModel : MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("FTLOG","MainpageFragment")
        binding = DataBindingUtil
            .inflate(inflater,R.layout.fragment_mainpage,container,false)
        binding.mainpageFragmentDataBindingVariable = this
        binding.mainpageToolbarTitle = "LetsEat"

        viewModel.itemsList.observe(viewLifecycleOwner){
            if (binding.rvProduct.adapter == null){
                val adapter = ProductAdapter(requireContext(),it,viewModel)
                binding.productAdapterDataBindingVariable = adapter
            } else {
                (binding.rvProduct.adapter as ProductAdapter).updateItems(it)
            }
        }

        binding.searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.filterList(newText)
                return true
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: MainViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun fabActionGoToCart(it:View){
        Navigation.doPageTransfer(it, R.id.goCart)
    }
}