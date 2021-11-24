package com.example.Logic

import com.example.model.Component
import java.util.*

class Order():PersistInstance() {

    var date:Date?=null
    var extraPrice:Double?=null
    var components = ArrayList<Component>()
    var user_id:String?=null
    fun addComponent(component: Component){
        components.add(component)
    }
}
