package com.example.Logic

import com.example.model.Adress

enum class Role {
    Admin, User
}
class User(var username:String, var password:String, var role: Role = Role.User):PersistInstance(){
    var adress=Adress()
    var orders:ArrayList<Order> = ArrayList()
}
