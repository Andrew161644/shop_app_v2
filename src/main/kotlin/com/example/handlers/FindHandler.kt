package com.example.handlers

import com.example.Services.CategoriesService
import com.example.Services.ComponentsService
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import org.koin.core.Koin

class FindHandler {
    companion object{
        suspend fun PipelineContext<Unit, ApplicationCall>.findInBase(koin: Koin) {
            val compService = koin.get<ComponentsService>()
            val catservice = koin.get<CategoriesService>()
            val name = call.parameters["name"].orEmpty()
            val res= compService.getComponentsContainsName(name)
            val categories = catservice.getAll()
            call.respond(FreeMarkerContent("index.ftl", mapOf("components" to res, "cats" to categories)))
        }
    }
}
