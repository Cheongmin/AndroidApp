package com.cheongmin.voicereader.user

import android.content.Context
import android.preference.PreferenceManager
import com.cheongmin.voicereader.SingletonHolder
import com.cheongmin.voicereader.model.User

const val USER_ID = "user.id"

class UserManager private constructor(context: Context) {
    private val preference = PreferenceManager.getDefaultSharedPreferences(context)

    var user: User? = null

    var id: String
        get() = preference.getString(USER_ID, "")
        set(value) {
            preference.edit().putString(USER_ID, value).commit()
        }

    fun hasId(): Boolean {
        return !id.isNullOrBlank()
    }

    companion object: SingletonHolder<UserManager, Context>(::UserManager)
}