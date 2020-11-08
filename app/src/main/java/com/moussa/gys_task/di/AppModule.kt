package com.moussa.gys_task.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moussa.gys_task.utils.Constants
import com.moussa.gys_task.viewmodel.TrendingListViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {

    @Provides
    fun getTrendingViewModel(): TrendingListViewModel {
        return TrendingListViewModel()
    }

    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

}