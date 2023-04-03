package com.example.yemekleruygulamasi.retrofit

class ApiUtlils {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getYemeklerDaoInterface(): YemeklerDaoInterface {
            return RetrofitClient.getClient(BASE_URL).create(YemeklerDaoInterface::class.java)
        }
    }
}