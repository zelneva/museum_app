package dev.android.museum.db

import android.content.Context
import dev.android.museum.database
import dev.android.museum.model.util.SessionObject
import org.jetbrains.anko.db.*

class UserRepository(val context: Context) {

    companion object {
        val USER_TABLE_NAME = "user"
    }

    fun findAll(): ArrayList<SessionObject?> = context.database.use {
        val objects = ArrayList<SessionObject?>()

        select(USER_TABLE_NAME, "id", "user_id", "session_id")
                .parseList(object : MapRowParser<List<SessionObject?>> {
                    override fun parseRow(columns: Map<String, Any?>): List<SessionObject?> {
                        val userId: String? = columns.getValue("user_id").toString()
                        val sessionId: String? = columns.getValue("session_id").toString()
                        if (userId != null && sessionId != null) {
                            val sessionObject = SessionObject(userId, sessionId)
                            objects.add(sessionObject)
                        }
                        return objects
                    }
                })
        objects
    }


    fun create(sessionObject: SessionObject) = context.database.use {
        insert(USER_TABLE_NAME,
                "user_id" to sessionObject.userId,
                "session_id" to sessionObject.sessionId)
    }


    fun update(sessionObject: SessionObject) = context.database.use {
        update(USER_TABLE_NAME,
                "user_id" to sessionObject.userId,
                "session_id" to sessionObject.sessionId)
                .exec()
    }


    fun delete(sessionObject: SessionObject) = context.database.use {
        delete(USER_TABLE_NAME, "session_id = {session_id}", "session_id" to sessionObject.sessionId)
    }

    fun deleteAll() = context.database.use {
        delete(USER_TABLE_NAME, null, null)
    }

}
