package pkg.what.a_4

import android.graphics.Bitmap
import android.widget.ImageView
import com.google.gson.annotations.SerializedName

/**@desc an extension utility to debug null data class values */
fun Any?.toString(): String {
    return if (this == null) "IS NULL" else "NOT NULL"
}

class ModelA4{

    /**@desc map to stash all of the data of users */
    val dataOfUsers: MutableMap<Int,UserModel> = mutableMapOf()

    /**@desc list to stash all of the data of images as bitmap */
    val dataOfImages: MutableList<Bitmap> = mutableListOf()

    /**@desc definition for an image */
    data class ImageModel(
        @SerializedName("img") var img : ImageView? = null
    )

    /**@desc definition for an address,
     * [address] is an address object defined as [AddressModel]
     * [company] is a company object defined as [CompanyModel] */
    data class UserModel(
        @SerializedName("id") var id : Int?     = null,
        @SerializedName("name") var name : String?  = null,
        @SerializedName("username") var username : String?  = null,
        @SerializedName("email") var email : String?  = null,
        @SerializedName("address") var address : AddressModel? = AddressModel(),
        @SerializedName("phone") var phone : String?  = null,
        @SerializedName("website") var website : String?  = null,
        @SerializedName("company") var company : CompanyModel? = CompanyModel()
    )
    /**@desc definition for an address,
     * [geo] is a geographical object defined as [GeoModel] */
    data class AddressModel(
        @SerializedName("street") var street : String? = null,
        @SerializedName("suite") var suite : String? = null,
        @SerializedName("city") var city : String? = null,
        @SerializedName("zipcode") var zipcode : String? = null,
        @SerializedName("geo") var geo : GeoModel? = GeoModel()
    )

    /**@desc definition for a company */
    data class CompanyModel(
        @SerializedName("name") var name : String? = null,
        @SerializedName("catchPhrase") var catchPhrase : String? = null,
        @SerializedName("bs") var bs : String? = null
    )

    /**@desc definition for a geo */
    data class GeoModel(
        @SerializedName("lat") var lat : String? = null,
        @SerializedName("lng") var lng : String? = null
    )
}