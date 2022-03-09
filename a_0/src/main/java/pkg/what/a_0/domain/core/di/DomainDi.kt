package pkg.what.a_0.domain.core.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import pkg.what.a_0.data.io.network.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DomainDi {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val apiTypiCode = Retrofit.Builder()
        .baseUrl("http://jsonplaceholder.typicode.com")
        .addConverterFactory(MoshiConverterFactory
            .create(moshi).asLenient())
        .build()
        .create(TypiCodeApi::class.java)
    private val apiRoboHash = Retrofit.Builder()
        .baseUrl("https://robohash.org")
        .addConverterFactory(MoshiConverterFactory
            .create(moshi).asLenient())
        .build()
        .create(RoboHashApi::class.java)
    private val remoteDataSourceImages =
        DataSourceImages(apiRoboHash, Dispatchers.IO)
    private val imagesRepository =
        DataRepoImages(remoteDataSourceImages)
    private val remoteDataSourceUsers =
        DataSourceUsers(apiTypiCode, Dispatchers.IO)
    private val usersRepository =
        DataRepoUsers(remoteDataSourceUsers)

    fun getRemoteDataSourceImagesFromRoboHash(): DataSourceImages {
        return this.remoteDataSourceImages
    }

    fun getRemoteDataSourceUsersFromTypiCode(): DataSourceUsers {
        return this.remoteDataSourceUsers
    }

    fun getRemoteDataRepoImagesFromRoboHash(): DataRepoImages {
        return this.imagesRepository
    }

    fun getRemoteDataRepoUsersFromTypiCode(): DataRepoUsers {
        return this.usersRepository
    }
}