package dev.android.museum.db

import android.content.Context
import android.util.Log
import dev.android.museum.model.util.SessionObject

class UserDb {

    companion object {

        // загрузка данных из sqlite
        fun loadSessionObject(context: Context): SessionObject? {
            val list: ArrayList<SessionObject?> =  UserRepository(context).findAll()
            if(list.size != 0){
                return list.last()
            }
            else return null
        }

        fun deleteAllSession(context: Context){
            UserRepository(context).deleteAllSessions()
        }

        fun delete(session: SessionObject, context: Context){
            UserRepository(context).deleteSession(session)
        }


        // Положим данные SessionObject о пользователе в sqlite
        fun addSessionObjectToDB(sessionObject: SessionObject, context: Context) {
            createSessionObject(sessionObject, context)
        }


        fun createSessionObject(sessionObject: SessionObject, context: Context) {
            val newSessionObject = SessionObject(sessionObject.userId, sessionObject.sessionId)
            UserRepository(context).create(newSessionObject)
        }


    }
}