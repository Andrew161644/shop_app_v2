package com.example.Logic

import com.google.gson.Gson
import org.bson.BsonObjectId
import org.bson.types.ObjectId


open class PersistInstance {
    public var _id= BsonObjectId(ObjectId())
    open fun toJson()= Gson().toJson(this,this::class.java)

}
