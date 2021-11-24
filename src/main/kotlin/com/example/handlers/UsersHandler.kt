package com.example.handlers

import com.example.Logic.Role
import com.example.Logic.User
import com.example.Services.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import org.bson.types.ObjectId
import org.koin.core.Koin

class UsersHandler {
    companion object{


        suspend fun PipelineContext<Unit, ApplicationCall>.handleAddUser(koin: Koin) {

            val requestBody = call.receiveParameters()

            val username =requestBody["username"].orEmpty()
            val password =requestBody["password"].orEmpty()

            val userService = koin.get<UserService>()
            //getUserByName
            val user = userService.getUserByName(username)

            if (user!=null){
                call.respondText("Already Exist")
                return
            }
            //save User
            val oidOrErrorMessage = userService.saveNewUser(User(username,password))
            if (ObjectId.isValid(oidOrErrorMessage)) {
                call.respond(HttpStatusCode.Created)
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        suspend fun PipelineContext<Unit, ApplicationCall>.getAllUsers(){
            call.respond(getAllUsers())
        }
        suspend fun PipelineContext<Unit, ApplicationCall>.addAdmin(koin: Koin){
            val userService=koin.get<UserService>()
            val username= call.request.header("Username").orEmpty()
            val password = call.request.header("Password").orEmpty()
            val user = userService.getUserByName(username)

            if (user!=null){
                call.respondText("Already Exist")
                return
            }
            else{
                userService.saveNewUser(User(username, password,Role.Admin))?.let { call.respond(it) }
                return
            }
        }
    }
}
