package com.example.yemekleruygulamasi

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yemekleruygulamasi.entity.Yemekler
import com.example.yemekleruygulamasi.ui.theme.YemeklerUygulamasiTheme
import com.example.yemekleruygulamasi.viewmodel.AnasayfaViewModel
import com.example.yemekleruygulamasi.viewmodel.AnasayfaViewModelFactory
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YemeklerUygulamasiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SayfaGecisleri()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    YemeklerUygulamasiTheme {

    }
}


@Composable
fun Anasayfa(navController: NavController) {
    val context = LocalContext.current
    var viewModel: AnasayfaViewModel = viewModel(
        factory = AnasayfaViewModelFactory(context.applicationContext as Application)
    )

    val yemekListesi = viewModel.yemeklerListesi.observeAsState(listOf())

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Yemekler") },
            backgroundColor = colorResource(id = R.color.anaRenk),
            contentColor = colorResource(id = R.color.white)
        )
    }, content = { it ->
        it
        LazyColumn {
            items(
                count = yemekListesi.value!!.count(),
                itemContent = {
                    val yemek = yemekListesi.value!![it]
                    Card(
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.clickable {
                            val yemekJson = Gson().toJson(yemek)
                            navController.navigate("detay_sayfa/${yemekJson}")
                        }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(all = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                val activity = (LocalContext.current as Activity)
                                GlideImage(
                                    imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}",
                                    modifier = Modifier.size(100.dp)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier.fillMaxHeight()
                                    ) {
                                        Text(text = yemek.yemek_adi, fontSize = 20.sp)
                                        Spacer(modifier = Modifier.size(30.dp))
                                        Text(
                                            text = "${yemek.yemek_fiyati} ₺",
                                            color = Color.Blue,
                                            fontSize = 20.sp
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_resim),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                }

            )
        }
    })
}


@Composable
fun SayfaGecisleri() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa") {
        composable("anasayfa") {
            Anasayfa(navController = navController)
        }
        composable("detay_sayfa/{yemek}", arguments = listOf(
            navArgument("yemek") { type = NavType.StringType }
        )) {
            val json = it.arguments?.getString("yemek")!!
            val yemek = Gson().fromJson(json, Yemekler::class.java)
            DetaySayfa(yemek)
        }
    }
}