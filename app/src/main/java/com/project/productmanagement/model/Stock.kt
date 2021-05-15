package com.project.productmanagement.model


data class Stock(
    override val codprod:Int,
    override val name:String?,
    val qtde:Double,
    val date: String,
    val punit:Double
): Product(codprod,name){
    constructor(codprod:Int, qtde: Double, date: String, punit: Double) :
            this(codprod,null,qtde,date,punit)
}