package com.example.yemekleruygulamasi.retrofit

import com.example.yemekleruygulamasi.entity.YemeklerCevap
import retrofit2.Call
import retrofit2.http.GET

interface YemeklerDaoInterface {
    @GET("yemekler/tum_yemekler_getir.php")
    fun tumYemekler():Call<YemeklerCevap>
}