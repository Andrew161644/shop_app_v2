package com.example.Services

import com.example.model.Category

import mongo.MongoDataService
import org.bson.BsonObjectId
import org.bson.Document
import org.bson.types.ObjectId

class CategoriesService(val mongoDataService: MongoDataService) {
    val categoriesCollection="categories"

    fun getAll()=mongoDataService.allFromCollection(categoriesCollection).map {
        val cat=Category.fromJson(it.values.first())
        cat._id= BsonObjectId(ObjectId(it.keys.first()))
        cat
    }
    fun createNew(cat:Category):String{
        return mongoDataService.saveNewDocument(categoriesCollection,Category.categoryToJson(cat))
    }
    fun updateCategory(cat: Category){
        mongoDataService.updateExistingDocument(categoriesCollection,cat._id.value.toHexString(),Category.categoryToJson(cat))
    }
    fun getCategoryById(id:String) = mongoDataService.getDocumentById(categoriesCollection,id)?.map {

        val cat=Category.fromJson(it.value)
        cat._id= BsonObjectId(ObjectId(it.key))
        cat
    }
    fun getCategoryByName(name:String): Category? {
        val document = mongoDataService.database.getCollection(categoriesCollection).find(
            Document("name", name)
        ).first()
        if (document==null)
            return null
        return mongoDataService.mongoDocumentTransform(document).map {
            val cat=Category.fromJson(it.value)
            cat._id=BsonObjectId(ObjectId(it.key))
            cat
        }.first()
    }
}
