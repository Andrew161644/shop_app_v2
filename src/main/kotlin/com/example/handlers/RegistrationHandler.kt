package com.example.handlers

import com.example.Logic.User
import com.example.Services.ComponentsService
import com.example.Services.UserService
import com.example.Session.MySession
import com.example.handlers.ComponentsHandler.Companion.mainPage
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import org.koin.core.Koin

class RegistrationHandler {
    companion object{

        suspend fun PipelineContext<Unit, ApplicationCall>.getReg() {
            call.respond(FreeMarkerContent("registration.ftl", null))
        }
        suspend fun PipelineContext<Unit, ApplicationCall>.postReg(koin: Koin) {
            val userService=koin.get<UserService>()
            val responseBody=call.receiveParameters()
            val username=responseBody["username"]
            val password=responseBody["password"]

            if (username!=null && password!=null){
                val user=User(username,password)
                val user2=userService.getUserByName(username)
                if (user2!=null){
                    getReg()
                }else{
                    val res=userService.saveNewUser(user).orEmpty()
                    call.sessions.set("SESSION", MySession(res,user))
                    mainPage(koin)
                }
            }

        }
    }
}
