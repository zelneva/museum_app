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
                for( i in list){
                    Log.d("SESSION",i?.userId + " " + i?.sessionId)
                }
                return list.last()
            }
            else return null
        }

        //удаление данных из sqlite
        fun deleteSessionObject(sessionObject: SessionObject, context: Context) {
            UserRepository(context).delete(sessionObject)
        }

        fun deleteAllSession(context: Context){
            UserRepository(context).deleteAll()
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