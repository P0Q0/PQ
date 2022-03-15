package pkg.what.a_0.domain.core.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Downloader
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Protocol
import pkg.what.a_0.data.io.network.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class DomainDi(applicationContext: Context) {

    //TODO: DomainDi, add interceptor to
    // handle ERROR_500 for bad hashed source path

    private val client: OkHttpClient = OkHttpClient()
        .newBuilder()
        .protocols(Collections.singletonList(Protocol.HTTP_1_1))
        .build()
    private val downloader: Downloader =
        OkHttp3Downloader(client)
    private val picasso = Picasso.Builder(applicationContext)
        .downloader(downloader)
        .loggingEnabled(true)
        .build()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val apiTypiCode = Retrofit.Builder()
        .client(client)
        .baseUrl("http://jsonplaceholder.typicode.com")
        .addConverterFactory(MoshiConverterFactory
            .create(moshi).asLenient())
        .build()
        .create(TypiCodeApi::class.java)
    private val apiRoboHash = Retrofit.Builder()
        .client(client)
        .baseUrl("https://robohash.org")
        .addConverterFactory(MoshiConverterFactory
            .create(moshi).asLenient())
        .build()
        .create(RoboHashApi::class.java)
    private val remoteDataSourceImages =
        DataSourceImages(apiRoboHash, Dispatchers.IO)
    private val imagesRepository =
        DataRepoImages(picasso,remoteDataSourceImages)
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