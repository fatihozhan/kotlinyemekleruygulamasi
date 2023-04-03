package com.example.yemekleruygulamasi.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.yemekleruygulamasi.entity.Yemekler
import com.example.yemekleruygulamasi.entity.YemeklerCevap
import com.example.yemekleruygulamasi.retrofit.ApiUtlils
import com.example.yemekleruygulamasi.retrofit.YemeklerDaoInterface
import com.example.yemekleruygulamasi.room.Veritabani
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YemeklerDaoRepository(var application: Application) {
    var yemeklerListesi = MutableLiveData<List<Yemekler>>()
    var yemeklerDaoInterface : YemeklerDaoInterface

    init {
        yemeklerListesi = MutableLiveData()
        yemeklerDaoInterface = ApiUtlils.getYemeklerDaoInterface()
    }

    fun yemekleriGetir(): MutableLiveData<List<Yemekler>> {
        return yemeklerListesi
    }

    fun tumYemekleriAl() {
      yemeklerDaoInterface.tumYemekler().enqueue(object : Callback<YemeklerCevap>{
          override fun onResponse(call: Call<YemeklerCevap>, response: Response<YemeklerCevap>) {
              val liste = response.body()!!.yemekler
              yemeklerListesi.value = liste
          }

          override fun onFailure(call: Call<YemeklerCevap>, t: Throwable) {}

      })
    }
}