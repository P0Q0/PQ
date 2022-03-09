package pkg.what.a_0.data.io.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pkg.what.a_0.data.model.DataModel

class DataRepoUsers (private val remoteUsers: DataSourceUsers) {
    val data: Flow<List<DataModel.UserModel>>
            = flow { emit(remoteUsers.fetchUsers()) }
}