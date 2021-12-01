package com.example.handlers

import com.example.Services.OrderService
import com.example.Services.UserService
import com.example.Session.Basket
import com.example.Session.SessionFunctions.Companion.getCurrentUser
import com.example.Session.SessionFunctions.Companion.getOrderBasket
import com.example.Session.SessionFunctions.Companion.authUserAvailible
import com.example.handlers.ComponentsHandler.Companion.mainPage

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.bson.BsonObjectId
import org.bson.types.ObjectId
import org.koin.core.Koin


open class OrderHandler() {

    companion object{

        suspend fun PipelineContext<Unit, ApplicationCall>.handleAddOrder(koin: Koin) {
            val userService = koin.get<UserService>()
            val orderService = koin.get<OrderService>()
            val user=getCurrentUser()
            val order= getOrderBasket()

            if (  order==null || order.components.isEmpty() || user==null){
                mainPage(koin)
                return
            }
            order._id = BsonObjectId(ObjectId())
            //save order
            orderService.saveNewOrder(order)
            //update user
            user.orders.add(order)
            userService.updateUser(user)

            getOrderBasket()?.components?.clear()
            handleGetOrders(koin)

        }


        suspend fun PipelineContext<Unit, ApplicationCall>.handleGetOrders(koin: Koin) {
            coroutineScope { // Creates a coroutine scope
                authUserAvailible {
                    launch {

                        call.respond(FreeMarkerContent("order.ftl", mapOf("user" to getCurrentUser())))
                    }
                }
            }
        }


    }
}
