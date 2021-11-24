package com.example.handlers




import com.example.Services.UserService
import com.example.Session.Basket
import com.example.Session.MySession
import com.example.handlers.ComponentsHandler.Companion.mainPage


import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import org.koin.core.Koin


class LoginHandler {
    companion object{

        suspend fun PipelineContext<Unit, ApplicationCall>.getLogin() {
            call.respond(FreeMarkerContent("join.ftl", null))
        }

        suspend fun PipelineContext<Unit, ApplicationCall>.postLogin(koin: Koin) {
            val userService=koin.get<UserService>()

            val principal = call.principal<UserIdPrincipal>() ?: error("No principal")
            val map= userService.getUserMapObjbyName(principal.name)
            if (map==null){
                getLogin()
                return
            }
            call.sessions.clear<MySession>()
            call.sessions.clear<Basket>()
            call.sessions.set("SESSION", MySession(map.keys.first(),map.values.first()))
            mainPage(koin)
        }
    }
}
