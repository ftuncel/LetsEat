package com.ferhattuncel.letseat.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ferhattuncel.letseat.R
import com.ferhattuncel.letseat.databinding.FragmentCartBinding
import com.ferhattuncel.letseat.ui.adapter.CartAdapter
import com.ferhattuncel.letseat.ui.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var viewModel : CartViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
        binding.cartToolbarTitle = "Siparişi Onayla"
        binding.cartFragmentDataBindingVariable = this

        binding.tbBtnCartBack.setOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.cartItemsList.observe(viewLifecycleOwner) { cartItems ->
            val adapter = if (cartItems?.isNotEmpty() == true) {
                CartAdapter(requireContext(), cartItems, viewModel)
            } else {
                rearrangeForEmptyCart()
                null
            }
            binding.cartAdapterDataBindingVariable = adapter
        }

        viewModel.totalOrderPrice.observe(viewLifecycleOwner){
                totalPrice -> binding.tvCartTotal.text = "Sipariş Tutarı: ${totalPrice} ₺"
        }

        return binding.root
    }

    private fun rearrangeForEmptyCart() {
        binding.btnCartOrder.visibility = View.GONE
        binding.tvCartTotal.visibility = View.GONE
        binding.fabCartCleanAll.visibility = View.GONE
        binding.tvEmptyList.visibility = View.VISIBLE
    }

    fun confirmCart(view:View){
        val snackbar = Snackbar.make(
            view,
            "Sepeti onaylamak istediğinizden emin misiniz?",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Evet") {
            viewModel.confirmCart()
            Snackbar.make(view, "Sipariş onaylandı.", Snackbar.LENGTH_LONG).show()
            binding.tvEmptyList.setText("Siparişiniz yola çıktı. Afiyet Olsun :)")
            binding.tvEmptyList.setTextColor(Color.BLACK)
            rearrangeForEmptyCart()
        }
        /*
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (event == DISMISS_EVENT_SWIPE || event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_CONSECUTIVE) {
                    // can be use this part in case there is no approve
                }
            }
        })
        */
        snackbar.show()
    }

    /*
    fun cleanCart(view:View){
        viewModel.cleanCart()
        Snackbar.make(view, "Tüm yemekler silindi!", Snackbar.LENGTH_SHORT).show()
    }
    */
    fun cleanCart(view: View) {
        val snackbar = Snackbar.make(
            view,
            "Sepeti temizlemek istediğinizden emin misiniz?",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Evet") {
            viewModel.cleanCart()
            binding.tvEmptyList.setText("Sepetiniz temizlendi.")
            binding.tvEmptyList.setTextColor(Color.BLACK)
        }
        /*
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (event == DISMISS_EVENT_SWIPE || event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_CONSECUTIVE) {
                    // can be use this part in case there is no approve
                }
            }
        })
        */
        snackbar.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CartViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCartItemsList()
    }
}