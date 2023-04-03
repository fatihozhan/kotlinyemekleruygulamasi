package com.example.yemekleruygulamasi.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.yemekleruygulamasi.entity.Yemekler
import com.example.yemekleruygulamasi.entity.YemeklerCevap
import com.example.yemekleruygulamasi.retrofit.ApiUtlils
import com.example.yemekleruygulamasi.retrofit.YemeklerDaoInterface
import com.example.yemekleruygulamasi.room.Veritabani
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YemeklerDaoRepository(var application: Application) {
    var yemeklerListesi = MutableLiveData<List<Yemekler>>()
    var refYemekler: DatabaseReference

    init {
        yemeklerListesi = MutableLiveData()
        var db = FirebaseDatabase.getInstance()
        refYemekler =
            db.getReference("https://start-e1f22-default-rtdb.europe-west1.firebasedatabase.app")
    }

    fun yemekleriGetir(): MutableLiveData<List<Yemekler>> {
        return yemeklerListesi
    }

    fun tumYemekleriAl() {
        refYemekler.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val liste = ArrayList<Yemekler>()
                for (d in snapshot.children) {
                    val yemek = d.getValue(Yemekler::class.java)
                    if (yemek != null) {
                        yemek.yemek_id = d.key
                        liste.add(yemek)
                    }
                }
                yemeklerListesi.value = liste
            }

            override fun onCancelled(error: DatabaseError) {}

        }
        )
    }
}