package com.example.Services

import com.example.Logic.ComponentImage
import com.example.model.Category
import com.google.gson.Gson
import com.mongodb.client.model.Filters.regex
import mongo.MongoDataService
import org.bson.BsonObjectId
import org.bson.Document
import org.bson.types.ObjectId
import java.util.ArrayList

class ComponentsService(val mongoDataService: MongoDataService,val categoriesService: CategoriesService) {
    val componentCollection="components"

    fun saveNewComponents(comp:ComponentImage) = mongoDataService.saveNewDocument(componentCollection, compToJson(comp),comp._id)
    fun updateComponent(comp: ComponentImage) = mongoDataService.updateExistingDocument(componentCollection, comp._id.value.toHexString(),compToJson(comp))
    fun compToJson(comp: ComponentImage): String = Gson().toJson(comp)
    fun compFromJson(json: String): ComponentImage=Gson().fromJson(json,ComponentImage::class.java)
    /*fun getCompMapObjbyName(name:String): List<Map<String, ComponentImage>>? {
        val documents = mongoDataService.database.getCollection(componentCollection).find(
            Document("name", name)
        )
        if (documents?.first() != null) {
           val a=documents.map{mongoDataService.mongoDocumentTransform(it)}
               .map{mapOf(it.keys.first() to compFromJson(it.values.first()))}.toList()
            return a
        }
        return null

    }*/
    fun getComponentById(_id:String): ComponentImage? {
        return mongoDataService.getDocumentById(componentCollection,_id)?.map {
            val res=compFromJson(it.value)
            res._id= BsonObjectId(ObjectId(it.key))
            res
        }?.first()
    }
    fun getAllComponents()=mongoDataService.allFromCollection(componentCollection).map {
        val comp=Gson().fromJson(it.values.first(),ComponentImage::class.java)
        comp._id=BsonObjectId(ObjectId(it.keys.first()))
        comp }
    fun addComponentToCategory(comp:ComponentImage){
        comp.categories.forEach { name->
            var cat= categoriesService.getCategoryByName(name)
            if (cat==null){
                cat=Category(name)
                cat.componetns.add(comp)
                categoriesService.createNew(cat)
            }
            else{
                cat.componetns.add(comp)
                categoriesService.updateCategory(cat)
            }
        }
    }
    fun getComponentsContainsName(name:String): List<ComponentImage>? {

        var document = mongoDataService.database.getCollection(componentCollection).find(
            Document("name",name)
        )

        if (document.first() == null) {

            document = mongoDataService.database.getCollection(componentCollection).find(
                regex("name", ".*${name}.*")
            )
            println(document.first())
        }

        if (document?.first() != null) {
            val res=ArrayList<ComponentImage>()
            val a = document.map{mongoDataService.mongoDocumentTransform(it)}
            a.map { val a=ComponentImage.fromJson(it.values.first())
                a._id=BsonObjectId(ObjectId(it.keys.first()))
            a
            }.forEach(res::add)
            return res
        }
        return null
    }
}
