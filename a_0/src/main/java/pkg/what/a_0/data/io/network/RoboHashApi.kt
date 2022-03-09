package pkg.what.a_0.data.io.network

import pkg.what.a_0.domain.core.constants.NetEptTags
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RoboHashApi {

    /** @Note: retrofit will not load a blob to transcode as an image
     * , but for demonstration purpose, this is how it is done
     * , add cache control in the headers, and change return type to ImageView */
    @Headers("Content-Type: image/png")
    @GET("${NetEptTags.BASE_ROBOHASH_ENDPOINT}/{path}")
    suspend fun getRoboHash(
        @Path("qwerty") path: String
    ) : Any
}