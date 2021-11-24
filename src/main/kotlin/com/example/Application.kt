package com.example

import com.example.Services.*
import com.example.Session.Basket
import com.example.Session.GsonSessionSerializer
import com.example.Session.MySession
import com.example.routing.Routes.Companion.serverRoutes
import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.ServerAddress
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import io.ktor.application.install
import io.ktor.freemarker.*
import io.ktor.http.content.*
import mongo.MongoDataService
import org.koin.core.context.startKoin
import org.koin.dsl.module

interface Engine

val Application.mongohost get() = environment.config.property("ktor.mongohost").getString()
val Application.mongoport get() = environment.config.property("ktor.mongoport").getString().toInt()
val Application.database get() = environment.config.property("ktor.database").getString()
fun main(args: Array<String>){

    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}


fun Application.module() {
    fun data() = module {
        val mongo=MongoDataService(MongoClient(ServerAddress(mongohost, mongoport), MongoClientOptions.builder().build()), database)

        val orderService= OrderService(mongo)
        val userService=UserService(mongo)
        val categoriesService=CategoriesService(mongo)
        val componentsService=ComponentsService(mongo,categoriesService)

        factory<MongoDataService> { mongo }
        factory<OrderService> { orderService }
        factory<UserService> { userService }
        factory<ComponentsService> { componentsService }
        factory<CategoriesService> { categoriesService }
    }

    val koin = startKoin {
        modules(data())
    }.koin



    install(ContentNegotiation){
        gson {
            setPrettyPrinting()
        }
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Sessions) {
        cookie<MySession>("SESSION"){
            serializer = GsonSessionSerializer(MySession::class.java)
        }
        cookie<Basket>("BASKET"){
            serializer = GsonSessionSerializer(Basket::class.java)
        }
    }



    routing {

        static("/static") {
            resources("static")
        }
        install(Authentication) {
            form("login") {
                userParamName = "username"
                passwordParamName = "password"
                val userService = koin.get<UserService>()
                validate { credentials ->
                    val map= userService.getUserMapObjbyName((credentials.name))
                    val user= map?.values?.first()
                    if (credentials.password == user?.password) UserIdPrincipal(credentials.name) else null
                }
            }
        }

        serverRoutes(koin)
    }
}


