package pkg.what.a_0.data.io.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pkg.what.a_0.data.model.DataModel

class DataSourceUsers(private val caller: TypiCodeApi
    , private val ioDispatcher: CoroutineDispatcher) : DataSource() {

    suspend fun fetchUsers(): List<DataModel.UserModel>
            = withContext(ioDispatcher){ caller.getTypiCode("Users") }
}