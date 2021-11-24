package com.example.Session

import com.example.Logic.Order
import com.example.Logic.Role
import com.example.Logic.User
import com.example.model.Component

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import java.util.ArrayList

class SessionFunctions {
    companion object{

        suspend fun PipelineContext<Unit, ApplicationCall>.saveComponentBasket(component: Component) {
            if (call.sessions.get<Basket>()==null){
                call.sessions.set("BASKET", Basket())
                call.sessions.get<Basket>()?.order?.user_id=getCurrentUserId()
            }
            call.sessions.get<Basket>()?.order?.addComponent(component)

        }
        suspend fun PipelineContext<Unit, ApplicationCall>.deleteComponentFromSession(component: Component){


            call.sessions.get<Basket>()?.order?.components?.map {
                if (it._id==component._id){
                    call.sessions.get<Basket>()?.order?.components?.remove(it)
                    return
                }
            }
        }
        suspend fun PipelineContext<Unit, ApplicationCall>.getComponentBasket(): ArrayList<Component>? {
            if (call.sessions.get<Basket>()==null){
                call.sessions.set("BASKET", Basket())
                call.sessions.get<Basket>()?.order?.user_id=getCurrentUserId()
            }
            println(call.sessions.get<Basket>()?.order?.components)
            return call.sessions.get<Basket>()?.order?.components
        }
        suspend fun PipelineContext<Unit, ApplicationCall>.getOrderBasket(): Order? {
            if (call.sessions.get<Basket>()==null){
                call.sessions.set("BASKET", Basket())
                call.sessions.get<Basket>()?.order?.user_id=getCurrentUserId()
            }
            return call.sessions.get<Basket>()?.order
        }
        suspend fun PipelineContext<Unit, ApplicationCall>.getCurrentUser(): User? {
            val session = call.sessions.get<MySession>()
            if (session != null) {
                return session.user
            }
            return null;
        }

        suspend fun PipelineContext<Unit, ApplicationCall>.getCurrentUserId(): String? {
            val session = call.sessions.get<MySession>()
            if (session != null) {
                return session.user_id
            }
            return null;
        }

        suspend fun PipelineContext<Unit, ApplicationCall>.authUserAvailible(function: () -> Unit) {
            val session = call.sessions.get<MySession>()
            if (session != null) {
                return function()
            }
            return call.respondRedirect("/login")
        }

        suspend fun PipelineContext<Unit, ApplicationCall>.roleAdminAvailible(function: () -> Unit) {
            val session = call.sessions.get<MySession>()
            val user = session?.user
            if (session != null && user?.role== Role.Admin) {
                return function()
            }
            return call.respondRedirect("/login")
        }

    }
}
