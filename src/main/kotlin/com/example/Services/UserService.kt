package com.example.Services

import com.example.Logic.User
import com.example.routing.Routes
import com.google.gson.Gson
import mongo.MongoDataService
import org.bson.BsonObjectId
import org.bson.Document
import org.bson.types.ObjectId

class UserService(val mongoDataService: MongoDataService) {


    val usersCollection = "users"
    val ordersCollection = "orders"

    fun saveNewUser(user: User) = mongoDataService.saveNewDocument(usersCollection, userToJson(user),user._id)
    fun updateUser(user: User): Pair<Int, String> {
        return mongoDataService.updateExistingDocument(usersCollection,user._id.value.toHexString(), userToJson(user))
    }
    fun userToJson(user: User): String = Gson().toJson(user)
    fun getUserByName(username:String): User? = getUserMapObjbyName(username)?.values?.first()
    fun getUserMapObjbyName(userName: String): Map<String, User>? {
        val document = mongoDataService.database.getCollection(usersCollection).find(
            Document("username", userName)
        )
        if (document?.first() != null) {
            val a = mongoDataService.mongoDocumentTransform(document.first())
            val user=Gson().fromJson(a.values.first(), User::class.java)
            user._id= BsonObjectId(ObjectId(a.keys.first()))
            return mapOf(a.keys.first() to user)
        }
        return null

    }
    fun getAllUsers()=mongoDataService.allFromCollection(usersCollection).map {
        val user=Gson().fromJson(it.values.first(),User::class.java)
        user._id= BsonObjectId(ObjectId(it.values.first()))
        user
    }
}
