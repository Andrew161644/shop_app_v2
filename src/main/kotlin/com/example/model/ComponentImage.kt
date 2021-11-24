package com.example.Logic

import com.example.model.Component
import com.example.model.MImage
import com.google.gson.Gson


enum class Status {
    OnStoreHouse,OnShop,NotAvailable
}
open class ComponentImage(name: String, price: Double, status: Status):Component(name, price, status) {
    var im: MImage?=null
    companion object{
        fun fromJson(json:String)= Gson().fromJson(json, ComponentImage::class.java)
        fun toJson(componentImage: ComponentImage) = Gson().toJson(componentImage)
    }
}
