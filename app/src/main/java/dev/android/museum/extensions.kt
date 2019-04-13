package dev.android.museum

import android.content.Context
import dev.android.museum.db.UserDbHelper

val Context.database: UserDbHelper
    get() = UserDbHelper.getInstance(applicationContext)