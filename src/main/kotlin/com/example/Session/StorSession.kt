package com.example.Session

import com.example.Logic.Order
import com.example.Logic.User
import com.google.gson.Gson
import io.ktor.sessions.*

data class MySession(var user_id:String,var user: User)
class Basket(){
    var order=Order()
}
class GsonSessionSerializer<Any> (
    val type: java.lang.reflect.Type, val gson: Gson = Gson(), configure: Gson.() -> Unit = {}
) : SessionSerializer<Any> {
    init {
        configure(gson)
    }

    override fun serialize(session: Any): String = gson.toJson(session)
    override fun deserialize(text: String): Any = gson.fromJson(text, type)
}
