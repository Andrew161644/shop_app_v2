package com.example.model

import com.example.Logic.PersistInstance
import com.example.Logic.Status
import com.google.gson.Gson
import java.util.ArrayList

open class Component(var name:String, var price:Double, var status: Status,): PersistInstance() {
    var specifications:Map<String,String>?=null
    var categories = ArrayList<String>()
    companion object{
        fun fromJson(json:String)= Gson().fromJson(json, Component::class.java)
        fun toJson(component: Component) = Gson().toJson(component)
    }
}

