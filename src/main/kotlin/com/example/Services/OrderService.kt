package com.example.Services

import com.example.Logic.Order

import com.example.routing.Routes
import com.google.gson.Gson
import mongo.MongoDataService

class OrderService(val mongoDataService: MongoDataService) {

    val usersCollection = "users"
    val ordersCollection = "orders"

    fun saveNewOrder(order: Order) = mongoDataService.saveNewDocument(ordersCollection, orderToJson(order),order._id)
    fun updateOrder(order: Order) =
        mongoDataService.updateExistingDocument(ordersCollection, order._id.value.toHexString(), orderToJson(order))

    fun orderToJson(order: Order): String = Gson().toJson(order)


}
