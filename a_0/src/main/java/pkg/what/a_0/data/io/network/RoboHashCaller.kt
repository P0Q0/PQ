package pkg.what.a_0.data.io.network

import com.google.gson.JsonObject
import pkg.what.a_0.domain.core.constants.NetEptTags
import retrofit2.Call
import retrofit2.http.GET

interface RoboHashCaller {

    @GET(NetEptTags.BASE_ROBOHASH_ENDPOINT)
    fun getRoboHash(

    ) : Call<JsonObject>
}