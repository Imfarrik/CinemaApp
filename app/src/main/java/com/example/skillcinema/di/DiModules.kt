package com.example.skillcinema.di

import android.content.Context
import com.example.skillcinema.model.network.HeaderInterceptor
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.network.Api
import com.example.skillcinema.model.network.ApiImpl
import com.example.skillcinema.model.network.ServiceApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module(includes = [DomainModule::class, NetworkModule::class])
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }
}

@Module
class DomainModule {

    @Provides
    fun providerSharedPreferencesManager(context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }
//
//    @Provides
//    fun providerAppDatabase(context: Context): AppDatabase {
//        return Room.databaseBuilder(context, AppDatabase::class.java, "AsakaAC_Database")
//            .addMigrations(MIGRATION_1_2)
//            .build()
//    }

}

@Module
class NetworkModule {

    @Provides
    fun provideApiImpl(api: Api): ServiceApi {
        return ApiImpl(api)
    }

    @Provides
    fun providerApi(okHttpClient: OkHttpClient): Api {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.MOVIES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(Api::class.java)
    }

    @Provides
    fun provideOkHttpClient(sharedPreferencesManager: SharedPreferencesManager): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(sharedPreferencesManager))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60000, TimeUnit.MILLISECONDS)
            .readTimeout(60000, TimeUnit.MILLISECONDS)
            .callTimeout(60000, TimeUnit.MILLISECONDS)
            .build()
    }


}