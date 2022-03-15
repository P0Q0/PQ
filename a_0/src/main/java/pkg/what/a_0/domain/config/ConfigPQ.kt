package pkg.what.a_0.domain.config

import pkg.what.pq.BuildConfig.GOOGLE_SERVICES_API_CLIENT_ID as SECRET_API_CLIENT_ID

object ConfigPQ {
    private const val API_CLIENT_ID: String = SECRET_API_CLIENT_ID
    fun getClientId() : String { return this.API_CLIENT_ID }
}