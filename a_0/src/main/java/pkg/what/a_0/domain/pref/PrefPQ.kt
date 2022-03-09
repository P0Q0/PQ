package pkg.what.a_0.domain.pref

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import pkg.what.a_0.domain.core.constants.SharedPrefTags.SHARED_PREFERENCES_DISK_KEY

class PrefPQ(ctx: Context) {

    private val sharedPref: SharedPreferences =
        ctx.getSharedPreferences(SHARED_PREFERENCES_DISK_KEY, Context.MODE_PRIVATE)

    fun write(key: String, value: String?) {
        sharedPref.edit().putString(key,value).apply()
    }

    fun read(state: Bundle? , key: String): String? {
        return state?.getString(key)
    }
}