package pkg.what.a_0.data.model

import android.graphics.Bitmap
import android.widget.ImageView
import com.google.gson.JsonObject
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_ADDRESS_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_BS_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_CATCHPHRASE_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_CITY_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_COMPANY_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_EMAIL_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_GEO_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_ID_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_IMG_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_LAT_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_LNG_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_NAME_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_PHONE_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_STREET_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_SUITE_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_USERNAME_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_WEBSITE_TAG
import pkg.what.a_0.domain.core.constants.NetDataTags.NET_DATA_ZIPCODE_TAG

/**@desc an extension utility to debug null data class values */
fun Any?.toString(): String {
    return if (this == null) "IS NULL" else "NOT NULL"
}

class DataModel {
    /**@desc map to stash all of the data of users */
    val dataOfUsers: MutableMap<Int,UserModel> = mutableMapOf()

    /**@desc list to stash all of the data of images as bitmap */
    val dataOfImages: MutableList<Bitmap> = mutableListOf()

    /**@desc definition for an image */
    @JsonClass(generateAdapter = true)
    data class ImageModel(
        @field: Json(name = NET_DATA_IMG_TAG) var img : ImageView? = null
    )

    /**@desc definition for an address,
     * [address] is an address object defined as [AddressModel]
     * [company] is a company object defined as [CompanyModel] */
    @JsonClass(generateAdapter = true)
    data class UserModel(
        @Json(name = NET_DATA_ID_TAG) var id : Int?     = null,
        @Json(name = NET_DATA_NAME_TAG) var name : String?  = null,
        @Json(name = NET_DATA_USERNAME_TAG) var username : String?  = null,
        @Json(name = NET_DATA_EMAIL_TAG) var email : String?  = null,
        @Json(name = NET_DATA_ADDRESS_TAG) var address : AddressModel? = AddressModel(),
        @Json(name = NET_DATA_PHONE_TAG) var phone : String?  = null,
        @Json(name = NET_DATA_WEBSITE_TAG) var website : String?  = null,
        @Json(name = NET_DATA_COMPANY_TAG) var company : CompanyModel? = CompanyModel()
    )
    /**@desc definition for an address,
     * [geo] is a geographical object defined as [GeoModel] */
    @JsonClass(generateAdapter = true)
    data class AddressModel(
        @field: Json(name = NET_DATA_STREET_TAG) var street : String? = null,
        @field: Json(name = NET_DATA_SUITE_TAG) var suite : String? = null,
        @field: Json(name = NET_DATA_CITY_TAG) var city : String? = null,
        @field: Json(name = NET_DATA_ZIPCODE_TAG) var zipcode : String? = null,
        @field: Json(name = NET_DATA_GEO_TAG) var geo : GeoModel? = GeoModel()
    )

    /**@desc definition for a company */
    @JsonClass(generateAdapter = true)
    data class CompanyModel(
        @field: Json(name = "NET_DATA_COMPANY_MODEL_TAG") var name : String? = null,
        @field: Json(name = NET_DATA_CATCHPHRASE_TAG) var catchPhrase : String? = null,
        @field: Json(name = NET_DATA_BS_TAG) var bs : String? = null
    )

    /**@desc definition for a geo */
    @JsonClass(generateAdapter = true)
    data class GeoModel(
        @field: Json(name = NET_DATA_LAT_TAG) var lat : String? = null,
        @field: Json(name = NET_DATA_LNG_TAG) var lng : String? = null
    )
}