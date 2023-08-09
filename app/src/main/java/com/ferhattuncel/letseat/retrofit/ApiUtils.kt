package com.ferhattuncel.letseat.retrofit

class ApiUtils {
    companion object{
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getProductDao() : ProductDao {
            return RetrofitClient.getClient(BASE_URL).create(ProductDao::class.java)
        }
    }
}