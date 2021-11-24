package com.example.handlers

import com.example.Services.UserService
import com.example.Session.SessionFunctions.Companion.authUserAvailible
import com.example.Session.SessionFunctions.Companion.getCurrentUser
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.Koin

class AccauntHandler {
    companion object{
        suspend fun PipelineContext<Unit, ApplicationCall>.accauntPage(koin: Koin){
            coroutineScope {
                authUserAvailible {
                    launch {
                        val user=getCurrentUser()
                        call.respond(FreeMarkerContent("account.ftl", mapOf("user" to user)))
                    }
                }
            }
        }
        suspend fun PipelineContext<Unit, ApplicationCall>.accauntPost(koin: Koin){
            val userservice = koin.get<UserService>()
            val par=call.receiveParameters()
            val newname = par["username"].orEmpty()
            val password = par["password"]
            val street = par["street"]
            val house = par["house"]
            val room=par["room"]
            val cur=getCurrentUser()
            if (cur?.username!=newname){
                val us=userservice.getUserByName(newname)
                if (us==null){
                    userservice.updateUser(cur!!)
                }
            }
            password?.let {
                cur?.password=password
            }
            cur?.let {
                user->
                house?.let {
                    user.adress.house=it
                }
                street?.let {
                    user.adress.street=it
                }
                room?.let {
                    user.adress.room=it
                }
            }
            cur?.let { userservice.updateUser(it) }
            accauntPage(koin)
        }
    }
}
