package com.example.routing



import com.example.Services.ComponentsService
import com.example.Session.Basket
import com.example.Session.MySession
import com.example.handlers.AccauntHandler.Companion.accauntPage
import com.example.handlers.AccauntHandler.Companion.accauntPost

import com.example.handlers.BasketHandler.Companion.deleteAllFromBasket
import com.example.handlers.BasketHandler.Companion.deleteFromBasket
import com.example.handlers.BasketHandler.Companion.handlerGetBasketComponents
import com.example.handlers.ComponentsHandler.Companion.addComponentToDataBase

import com.example.handlers.ComponentsHandler.Companion.handleAddComponentsInBasket

import com.example.handlers.ComponentsHandler.Companion.mainPage
import com.example.handlers.FindHandler.Companion.findInBase
import com.example.handlers.LoginHandler.Companion.getLogin
import com.example.handlers.LoginHandler.Companion.postLogin

import com.example.handlers.OrderHandler.Companion.handleAddOrder
import com.example.handlers.OrderHandler.Companion.handleGetOrders
import com.example.handlers.PersonalPageHandler.Companion.viewPage
import com.example.handlers.RegistrationHandler.Companion.getReg
import com.example.handlers.RegistrationHandler.Companion.postReg
import com.example.handlers.UsersHandler.Companion.addAdmin
import com.example.handlers.UsersHandler.Companion.handleAddUser

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*

import io.ktor.routing.*
import io.ktor.sessions.*
import org.koin.core.Koin


open class Routes() {

    companion object {

        fun Routing.serverRoutes(koin: Koin) {

            post("/addAdmin"){
                addAdmin(koin)
            }

            post("/componentsName"){
                val name=call.receiveParameters()["name"].orEmpty()
                val res= koin.get<ComponentsService>().getComponentsContainsName(name)
                if (res != null) {
                    call.respond(res)
                }
            }

            get("/testClear"){
                call.sessions.clear<Basket>()
                mainPage(koin)
            }

            post ("/addComponent"){
                addComponentToDataBase(koin)
            }

            post("/logout"){
                call.sessions.clear<MySession>()
                call.sessions.clear<Basket>()
                getLogin()
            }

            post("/clearAll"){
                call.sessions.clear<Basket>()
                mainPage(koin)
            }

            get("/createOrder") {
                handleGetOrders(koin)
            }

            post("/newOrder") {
                handleAddOrder(koin)
            }
            post("/addUser") {
                handleAddUser(koin)
            }

            route("/login") {
                get {
                    getLogin()
                }
                authenticate("login") {
                    post {
                        postLogin(koin)
                    }
                }
            }
            route("/"){
                get {
                    mainPage(koin)
                }
                post {

                    handleAddComponentsInBasket(koin)
                }
            }
            route("/registration"){
                get {
                    getReg()
                }
                post {
                    postReg(koin)
                }
            }
            route("/find"){
                get { findInBase(koin) }
            }
            route("/basket"){
                get {
                    handlerGetBasketComponents(koin)
                }
                post {
                    deleteFromBasket(koin)
                }
                post("/clear") {
                    deleteAllFromBasket(koin)
                }
            }
            route("/info"){
                get{
                    viewPage(koin)
                }
            }
            route("/accaunt"){
                get{
                    accauntPage(koin)
                }
                post {
                    accauntPost(koin)
                }
            }

        }


    }
}

