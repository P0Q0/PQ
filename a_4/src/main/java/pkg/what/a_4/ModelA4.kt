package pkg.what.a_4

/**@desc an extension utility to debug null data class values */
fun Any?.toString(): String {
    return if (this == null) "IS NULL" else "NOT NULL"
}

class ModelA4{

    /**@desc map to stash all of the data, nothing special, simply, stash it all, and that's that
     * @note the <K,V> set allows the V to be null, also [Any] is at it's root, an [Object]  */
    val data: MutableMap<String,Any?> = mutableMapOf()

    companion object {
        const val MAP_KEY_USER_DATA = "USER DATA KEY"
        const val MAP_KEY_IMAGE_DATA = "IMAGE DATA KEY"
    }

    /**@desc definition for an image */
    data class ImageModel(
        var img : String? = null //TODO: for now placeholder is String but change this to the Image Object type
    )

    /**@desc definition for an address,
     * [address] is an address object defined as [AddressModel]
     * [company] is a company object defined as [CompanyModel] */
    data class UserModel(
        var id       : Int?     = null,
        var name     : String?  = null,
        var username : String?  = null,
        var email    : String?  = null,
        var address  : AddressModel? = AddressModel(),
        var phone    : String?  = null,
        var website  : String?  = null,
        var company  : CompanyModel? = CompanyModel()
    )
    /**@desc definition for an address,
     * [geo] is a geographical object defined as [GeoModel] */
    data class AddressModel(
        var street  : String? = null,
        var suite   : String? = null,
        var city    : String? = null,
        var zipcode : String? = null,
        var geo     : GeoModel? = GeoModel()
    )

    /**@desc definition for a company */
    data class CompanyModel(
        var name        : String? = null,
        var catchPhrase : String? = null,
        var bs          : String? = null
    )

    /**@desc definition for a geo */
    data class GeoModel(
        var lat : String? = null,
        var lng : String? = null
    )
}