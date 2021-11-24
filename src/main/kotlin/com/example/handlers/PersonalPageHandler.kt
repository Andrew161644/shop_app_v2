package com.example.handlers

import com.example.Logic.ComponentImage
import com.example.Services.CategoriesService
import com.example.Services.ComponentsService
import com.example.Services.UserService


import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import org.koin.core.Koin

class PersonalPageHandler {
    companion object{

        suspend fun PipelineContext<Unit, ApplicationCall>.viewPage(koin: Koin) {
            val compService=koin.get<ComponentsService>()
            val catservice = koin.get<CategoriesService>()
            val id=call.parameters["_id"]
            var res:ComponentImage?=null
            if (id != null) {
                res= compService.getComponentById(id)
            }
            val categories = catservice.getAll()
            call.respond(FreeMarkerContent("description.ftl", mapOf("component" to res, "cats" to categories)))
        }
    }
}
