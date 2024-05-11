package com.example.descuentosapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.descuentosapp.components.Alert
import com.example.descuentosapp.components.MainButton
import com.example.descuentosapp.components.MainTextField
import com.example.descuentosapp.components.SpaceH
import com.example.descuentosapp.components.TwoCards

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(){
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "App descuentos", color = Color.White)},
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
            )
    }) {
        ContentHomeView(it)
    }

}
@Composable
fun ContentHomeView(paddingValues: PaddingValues) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .padding(10.dp)
        .fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var precio by remember { mutableStateOf("") }
        var descuento by remember { mutableStateOf("") }
        var precioDescuento by remember { mutableStateOf(0.0) }
        var totalDescuento by remember { mutableStateOf(0.0) }
        var showAlert  by remember { mutableStateOf(false) }

        TwoCards(
            title1 = "Total",
            number1 = totalDescuento ,
            title2 = "Descuento",
            number2 = precioDescuento
        )
        MainTextField(value = precio, onValueChange = {precio = it}, label = "Precio")
        SpaceH()
        MainTextField(value = descuento, onValueChange = {descuento = it}, label = "Descuento")
        SpaceH(10.dp)
        MainButton(text = "Generar Descuento") {
            if (precio != ""&& descuento!=""){
                precioDescuento = calcularPrecio(precio.toDouble(),descuento.toDouble())
                totalDescuento = calcularDescuento(precio.toDouble(),descuento.toDouble())
            }else{
                showAlert = true
            }
        }
    SpaceH()
        MainButton(text = "Limpiar", color = Color.Red) {
            precio = ""
            descuento = ""
            precioDescuento = 0.0
            totalDescuento = 0.0
        }
        if(showAlert){
            Alert(title = "Alerta",
                message = "Escribe el precio y el descuento",
                confirmText = "Aceptar",
                onConfirmClick = {showAlert = false}) {}

        }
    }
}
fun calcularPrecio(precio:Double, descuento:Double):Double{
    val res = precio - calcularDescuento(precio, descuento)
    return kotlin.math.round(res*100)/100.0

}
fun calcularDescuento(precio:Double, descuento:Double):Double{
    val res = precio * (1- descuento /100)
    return kotlin.math.round(res*100)/100.0
}
