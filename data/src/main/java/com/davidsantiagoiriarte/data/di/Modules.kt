package com.davidsantiagoiriarte.data.di

import androidx.room.Room
import com.davidsantiagoiriarte.data.db.GiphyDatabase
import com.davidsantiagoiriarte.data.db.daos.FavoriteGifsDao
import com.davidsantiagoiriarte.data.db.daos.RecentSearchDao
import com.davidsantiagoiriarte.data.network.GiphyService
import com.davidsantiagoiriarte.data.repositories.ApiGifsRepository
import com.davidsantiagoiriarte.data.repositories.FavoriteGifsRepository
import com.davidsantiagoiriarte.data.repositories.RecentSearchRepositoryImpl
import com.davidsantiagoiriarte.data.util.BASE_URL
import com.davidsantiagoiriarte.domain.di.NAMED_INJECTOR_API_GIF_REPOSITORY
import com.davidsantiagoiriarte.domain.di.NAMED_INJECTOR_FAVORITE_GIF_REPOSITORY
import com.davidsantiagoiriarte.domain.repositories.GifsRepository
import com.davidsantiagoiriarte.domain.repositories.RecentSearchRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<GiphyService> {
        (get() as Retrofit).create(GiphyService::class.java)
    }
}

val dbModule = module {
    single<GiphyDatabase> {
        Room.databaseBuilder(
            get(),
            GiphyDatabase::class.java, "giphy-database"
        ).build()
    }
    single<RecentSearchDao> { get<GiphyDatabase>().recentSearchDao() }
    single<FavoriteGifsDao> { get<GiphyDatabase>().favoriteGifsDao() }
}

val repositoriesModule = module {
    single<GifsRepository>(named(NAMED_INJECTOR_API_GIF_REPOSITORY)) {
        ApiGifsRepository(get(), get())
    }
    single<GifsRepository>(named(NAMED_INJECTOR_FAVORITE_GIF_REPOSITORY)) {
        FavoriteGifsRepository(get())
    }
    single<RecentSearchRepository> {
        RecentSearchRepositoryImpl(get())
    }
}
