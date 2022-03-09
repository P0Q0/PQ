package pkg.what.a_0.data.io.network

import pkg.what.a_0.domain.core.constants.NetEptTags
import retrofit2.http.GET
import retrofit2.http.Path

interface RoboHashApi {

    @GET("${NetEptTags.BASE_ROBOHASH_ENDPOINT}/{path}")
    suspend fun getRoboHash(
        @Path("qwerty") path: String
    ) : Any
}