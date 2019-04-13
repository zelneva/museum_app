package dev.android.museum.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class UserDbHelper(context: Context) : ManagedSQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        private val DATABASE_NAME = "museums"
        private val USER_TABLE_NAME = "user"
//        private val DATABASE_VERSION = 1

        private var instance: UserDbHelper? = null

        @Synchronized
        fun getInstance(context: Context): UserDbHelper {
            if (instance == null){
                instance = UserDbHelper(context.applicationContext)
            }

            return instance!!
        }
    }


    override fun onCreate(database: SQLiteDatabase) {

        database.createTable(USER_TABLE_NAME, true,
                "id" to INTEGER + PRIMARY_KEY + UNIQUE,
                "user_id" to TEXT + NOT_NULL,
                "session_id" to TEXT)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.dropTable(USER_TABLE_NAME, ifExists = true)
    }

}
