package pkg.what.a_0.data.io.network

import com.google.gson.JsonObject
import pkg.what.a_0.domain.core.constants.NetEptTags
import retrofit2.Call
import retrofit2.http.GET

interface TypiCodeCaller {

    @GET(NetEptTags.BASE_TYPICODE_ENDPOINT)
    fun getRoboHash(

    ) : Call<JsonObject>
}