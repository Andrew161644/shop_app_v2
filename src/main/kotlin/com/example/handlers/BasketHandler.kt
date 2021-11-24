package com.example.handlers

import com.example.Services.ComponentsService
import com.example.Session.Basket
import com.example.Session.SessionFunctions.Companion.deleteComponentFromSession
import com.example.Session.SessionFunctions.Companion.getComponentBasket
import com.example.Session.SessionFunctions.Companion.authUserAvailible
import com.example.handlers.ComponentsHandler.Companion.mainPage
import com.example.model.Component
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.Koin


class BasketHandler {
    companion object{
        suspend fun PipelineContext<Unit, ApplicationCall>.handlerGetBasketComponents(koin: Koin) {

            coroutineScope { // Creates a coroutine scope
                authUserAvailible {
                    launch {
                        val compService=koin.get<ComponentsService>()
                        val componentsInSession=getComponentBasket()?.map { compService.getComponentById(it._id.value.toHexString())};
                        call.respond(FreeMarkerContent("cart.ftl", mapOf("session_comp" to componentsInSession)))
                    }
                }
            }
        }
        suspend fun PipelineContext<Unit, ApplicationCall>.deleteFromBasket(koin: Koin) {
            val id=call.receiveParameters()["_id"].orEmpty()
            val compService = koin.get<ComponentsService>()
            val comp=compService.getComponentById(id) as Component
            deleteComponentFromSession(comp)
            handlerGetBasketComponents(koin)
        }
        suspend fun PipelineContext<Unit, ApplicationCall>.deleteAllFromBasket(koin: Koin) {
            call.sessions.clear<Basket>()
            mainPage(koin)
        }
    }
}
