package com.project.productmanagement.model


data class Stock(
    val codprod:Int,
    val desc:String?,
    val qtde:Double,
    val date: String,
    val punit:Double
)