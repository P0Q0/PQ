package pkg.what.a_0.domain.pref

import android.content.Context
import android.content.SharedPreferences
import pkg.what.a_0.domain.core.constant.SharedPrefTags.SHARED_PREFERENCES_DISK_KEY

class PrefPQ(ctx: Context) {

    private val sharedPref: SharedPreferences =
        ctx.getSharedPreferences(SHARED_PREFERENCES_DISK_KEY, Context.MODE_PRIVATE)

    fun getPref(): SharedPreferences { return this.sharedPref }

    fun write(key: String, value: String?) {
        sharedPref.edit().putString(key,value).apply()
    }

    fun read(key: String): String? {
        return sharedPref.getString(key,null)
    }
}