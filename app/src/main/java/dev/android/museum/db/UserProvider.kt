package dev.android.museum.db

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class UserProvider
//    : ContentProvider()
{

//    companion object {
//        val TAG = UserProvider.javaClass.simpleName
//
//        val USERS = 100
//        val USER_ID = 101
//
//        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
//    }
//
//    private var userDB: UserDbHelper? = null
//
//
//    init {
//        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS, USERS)
//        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS + "/#", USER_ID)
//    }
//
//
//    override fun onCreate(): Boolean {
//        userDB = UserDbHelper(context)
//        return true
//    }
//
//
//    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?,
//                       selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
//        val database = userDB?.readableDatabase
//
//        val cursor: Cursor
//
//        val match = sUriMatcher.match(uri)
//        when (match) {
////            USERS -> {
////                cursor = database?.query(UserContract.UserEntry.TABLE_NAME, projection, selection,
////                        selectionArgs, null, null, sortOrder)!!
////            }
//
//            USER_ID -> {
//                val selectionUserId = UserContract.UserEntry._ID + " = " +"?"
//                val selectionArgsUserId = arrayOf(ContentUris.parseId(uri).toString())
//
//                cursor = database?.query(UserContract.UserEntry.TABLE_NAME, projection,
//                        selectionUserId, selectionArgsUserId, null, null, sortOrder)!!
//            }
//
//            else -> throw IllegalArgumentException("Cannot query unknown URI $uri")
//        }
//        cursor.setNotificationUri(context!!.contentResolver, uri)
//
//        return cursor
//    }
//
//
//    override fun getType(uri: Uri?): String {
//        val match = sUriMatcher.match(uri)
//        return when (match) {
//            USERS -> UserContract.UserEntry.CONTENT_LIST_TYPE
//            USER_ID -> UserContract.UserEntry.CONTENT_ITEM_TYPE
//            else -> throw IllegalStateException("Unknown URI $uri with match $match")
//        }
//    }
//
//
//    override fun insert(uri: Uri?, contentValues: ContentValues?): Uri? {
//        val match = sUriMatcher.match(uri)
//        return when (match) {
//            USERS -> insert(UserContract.UserEntry.TABLE_NAME, uri, contentValues)
//            else -> throw IllegalArgumentException("Insertion is not supported for $uri")
//        }
//    }
//
//
//    override fun update(uri: Uri, contentValues: ContentValues, selection: String?,
//                        selectionArgs: Array<out String>?): Int {
//        val match = sUriMatcher.match(uri)
//        when (match) {
//            USERS -> return update(UserContract.UserEntry.TABLE_NAME, uri, contentValues, selection, selectionArgs)
//            USER_ID -> {
//                val selectionUserID = UserContract.UserEntry._ID + " = ?"
//                val selectionArgsUserId = arrayOf(ContentUris.parseId(uri).toString())
//                return update(UserContract.UserEntry.TABLE_NAME, uri, contentValues, selectionUserID, selectionArgsUserId)
//            }
//
//            else -> throw IllegalArgumentException("Update is not supported for $uri")
//        }
//    }
//
//
//    override fun delete(uri: Uri, selection: String, selectionArgs: Array<out String>): Int {
//        val database = userDB?.writableDatabase
//
//        val rowsDeleted: Int
//
//        val match = sUriMatcher.match(uri)
//        when (match) {
//            USERS -> rowsDeleted = database!!.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs)
//            USER_ID -> {
//                val selectionId = UserContract.UserEntry.COLUMN_USER_ID + "=?"
//                val selectionArgsId = arrayOf(ContentUris.parseId(uri).toString())
//                rowsDeleted = database!!.delete(UserContract.UserEntry.TABLE_NAME, selectionId, selectionArgsId)
//            }
//            else -> throw IllegalArgumentException("Deletion is not supported for $uri")
//        }
//
//        if (rowsDeleted != 0) {
//            context!!.contentResolver.notifyChange(uri, null)
//        }
//
//        return rowsDeleted
//    }
//
//
//    private fun insert(tableName: String, uri: Uri?, contentValues: ContentValues?): Uri? {
//        val database = userDB?.writableDatabase
//        val id = database?.insert(tableName,
//                null, contentValues)
//        if (id?.equals(-1)!!) {
//            Log.e(TAG, "Failed to insert row for $uri")
//            return null
//        }
//        context!!.contentResolver.notifyChange(uri, null)
//        return ContentUris.withAppendedId(uri, id)
//    }
//
//
//    private fun update(tableName: String, uri: Uri, values: ContentValues, selection: String?,
//                       selectionArgs: Array<out String>?): Int {
//        val database = userDB?.writableDatabase
//        val rowsUpdated = database?.update(tableName, values, selection, selectionArgs)!!
//        if (rowsUpdated != 0) {
//            context!!.contentResolver.notifyChange(uri, null)
//        }
//        return rowsUpdated
//    }
}
