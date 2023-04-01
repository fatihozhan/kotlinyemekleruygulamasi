package com.example.yemekleruygulamasi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yemekleruygulamasi.entity.Yemekler

@Database(entities = [Yemekler::class], version = 1)
abstract class Veritabani : RoomDatabase() {
    abstract fun yemeklerDao(): YemeklerDao

    companion object {
        var instance: Veritabani? = null

        fun veritabaniErisim(context: Context): Veritabani? {
            if (instance == null) {
                synchronized(Veritabani::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Veritabani::class.java,
                        "yemekler.sqlite"
                    ).createFromAsset("yemekler.sqlite").build()
                }
            }
            return instance
        }
    }
}