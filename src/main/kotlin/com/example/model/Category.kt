package com.example.model


import com.example.Logic.ComponentImage
import com.example.Logic.PersistInstance
import com.google.gson.Gson
import java.util.ArrayList

open class Category(var name:String):PersistInstance() {
    val componetns=ArrayList<ComponentImage>()
    companion object{
        fun categoryToJson(category:Category)=Gson().toJson(category)
        fun fromJson(json: String):Category=Gson().fromJson(json,Category::class.java)
    }
}
