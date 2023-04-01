package com.example.yemekleruygulamasi.entity

data class Yemekler(
    var yemek_id: Int,
    var yemek_adi: String,
    var yemek_resim_adi: String,
    var yemek_fiyati: Int
) {
}