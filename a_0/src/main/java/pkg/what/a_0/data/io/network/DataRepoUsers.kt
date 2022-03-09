package pkg.what.a_0.data.io.network

import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataRepoUsers (private val remoteUsers: DataSourceUsers) {
    val data: Flow<List<JsonObject>>
            = flow { remoteUsers.fetchUsers() }
}