package com.example.handlers


import com.example.Logic.ComponentImage
import com.example.Logic.Role
import com.example.Services.CategoriesService
import com.example.Services.ComponentsService
import com.example.Services.UserService
import com.example.Session.SessionFunctions.Companion.saveComponentBasket
import com.example.model.Component
import com.google.gson.stream.JsonReader
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import org.koin.core.Koin
import java.io.StringReader





class ComponentsHandler {
    companion object{

        suspend fun PipelineContext<Unit, ApplicationCall>.mainPage(koin: Koin) {
            var res: List<ComponentImage>? = null
            val cat = call.parameters["cat"].orEmpty()
            val compService = koin.get<ComponentsService>()
            val catservice = koin.get<CategoriesService>()
            when (cat) {
                "all" -> {
                    res = compService.getAllComponents()
                }
                "" -> {
                    res = compService.getAllComponents()
                }
                else -> {
                    println(cat)
                    res = catservice.getCategoryById(cat)?.first()?.componetns
                }
            }

            val categories = catservice.getAll()
            call.respond(FreeMarkerContent("index.ftl", mapOf("components" to res, "cats" to categories)))

        }

        suspend fun PipelineContext<Unit, ApplicationCall>.addComponentToDataBase(koin: Koin) {
            val compService = koin.get<ComponentsService>()
            val userservice = koin.get<UserService>()

            val componentImage = call.receive<ComponentImage>()


            val username= call.request.header("Username").orEmpty()
            val password = call.request.header("Password").orEmpty()

            val user = userservice.getUserMapObjbyName(username)?.values?.first()
            if (user==null || user.password!=password || user.role!=Role.Admin){
                call.respond("Something wrong")
                return
            }
            compService.addComponentToCategory(componentImage)
            call.respond(
                compService.saveNewComponents(componentImage).orEmpty()
            )
        }

        suspend fun PipelineContext<Unit, ApplicationCall>.handleAddComponentsInBasket(koin: Koin) {
            val id=call.receiveParameters()["_id"].orEmpty()

            val compService = koin.get<ComponentsService>()
            val componentImage=compService.getComponentById(id)


            if (componentImage!=null){
                val res  = Component(componentImage.name,componentImage.price,componentImage.status)
                res._id=componentImage._id
                saveComponentBasket(res)
            }
            mainPage(koin)
        }
    }
}
