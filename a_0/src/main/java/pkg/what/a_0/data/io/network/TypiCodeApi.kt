package pkg.what.a_0.data.io.network

import pkg.what.a_0.data.model.DataModel
import pkg.what.a_0.domain.core.constants.NetEptTags
import retrofit2.http.GET
import retrofit2.http.Path

interface TypiCodeApi {

    @GET("${NetEptTags.BASE_TYPICODE_ENDPOINT}/{Users}")
    suspend fun getTypiCode(
        @Path("Users") Users: String
    ) : List<DataModel.UserModel>
}