package mongo

import com.mongodb.MongoClient
import org.bson.BsonDocument
import org.bson.BsonObjectId
import org.bson.Document
import org.bson.json.JsonParseException
import org.bson.types.ObjectId


open class MongoDataService(mongoClient: MongoClient, database: String) {
    val database = mongoClient.getDatabase(database)
     fun allFromCollection(collection: String):
            ArrayList<Map<String, String>> {
        val mongoResult =
            database.getCollection(collection, Document::class.java)
        val result = ArrayList<Map<String, String>>()
        mongoResult.find()
            .forEach {
                val asMap: Map<String,String> = mongoDocumentTransform(it)
                result.add(asMap)
            }
        return result
    }
    open fun saveNewDocument(collection: String, document: String): String {
        try {
            val bsonDocument = BsonDocument.parse(document)
            // we create the id ourselves
            bsonDocument.remove("_id")
            val oid = ObjectId()
            bsonDocument.put("_id", BsonObjectId(oid))
            database.getCollection(collection, BsonDocument::class.java)
                .insertOne(bsonDocument)
            return oid.toHexString()
        } catch (ex: JsonParseException) {
            return "Invalid JSON: ${ex.localizedMessage}"
        }
    }
    open fun saveNewDocument(collection: String, document: String,id:BsonObjectId): String? {
        try {
            val bsonDocument = BsonDocument.parse(document)
            // we create the id ourselves
            bsonDocument.remove("_id")
            bsonDocument.put("_id", id)
            database.getCollection(collection, BsonDocument::class.java)
                .insertOne(bsonDocument)
            return id.value.toHexString()
        } catch (ex: JsonParseException) {
            return null
        }
    }
    open fun mongoDocumentTransform(document: Document): Map<String, String> {
        val asMap: MutableMap<String, Any> = document.toMutableMap()
        val asJson: String= document.toJson()
        if (asMap.containsKey("_id")) {
            val id = asMap.getValue("_id")
            if (id is ObjectId) {
               return mapOf(id.toHexString() to asJson )
            }
        }
        return mapOf()
    }
    open fun deleteDocument(collection: String, id: String?): Pair<Int, String> {
        if (!ObjectId.isValid(id)) {
            return Pair(0, "ID not found")
        }
        val filter = BsonDocument("_id", BsonObjectId(ObjectId(id)))
        val updatedValues = database.getCollection(collection)
            .deleteOne(filter).deletedCount
        if (updatedValues < 1) {
            return Pair(0, "ID not found")
        } else {
            return Pair(1, "success")
        }
    }
    open fun updateExistingDocument(collection: String, id: String?, document: String): Pair<Int, String> {
        try {
            if (!ObjectId.isValid(id)) {
                return Pair(0, "ID not found")
            }
            val bsonDocument = BsonDocument.parse(document)
            bsonDocument.remove("_id")
            val filter = BsonDocument("_id", BsonObjectId(ObjectId(id)))
            val updatedValues =
                database.getCollection(collection, BsonDocument::class.java)
                    .replaceOne(filter, bsonDocument).modifiedCount
            if (updatedValues < 1) {
                return Pair(0, "ID not found")
            } else {
                return Pair(1, "success")
            }
        } catch (ex: JsonParseException) {
            return Pair(-1, "Invalid JSON: ${ex.localizedMessage}")
        }
    }
    open fun getDocumentById(collection: String, id: String?): Map<String, String>? {
        if (!ObjectId.isValid(id)) {
            return null
        }
        val document = database.getCollection(collection)
            .find(Document("_id", ObjectId(id)))
        if (document != null && document.first() != null) {
            return mongoDocumentTransform(document.first())
        }
        return null
    }

}
