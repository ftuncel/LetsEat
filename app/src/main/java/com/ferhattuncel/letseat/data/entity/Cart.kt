package com.ferhattuncel.letseat.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cart (@SerializedName("sepet_yemek_id") var id:Int,
                 @SerializedName("yemek_adi")var name:String,
                 @SerializedName("yemek_resim_adi")var pic:String,
                 @SerializedName("yemek_fiyat")var price:Int,
                 @SerializedName("yemek_siparis_adet")var quantity:Int,
                 @SerializedName("kullanici_adi")var username:String) {
    val total: Int
        get() = price * quantity
}