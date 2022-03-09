package pkg.what.a_0.data.io.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pkg.what.a_0.domain.core.constants.NetEptTags

class DataSourceImages(private val caller: RoboHashApi
    , private val ioDispatcher: CoroutineDispatcher) : DataSource() {

    //TODO: DaraSourceImages, fix fetchImage use library Picasso-n/Glide-s
        suspend fun fetchImages(): Any
            = withContext(ioDispatcher){ caller.getRoboHash("qwerty") }
}