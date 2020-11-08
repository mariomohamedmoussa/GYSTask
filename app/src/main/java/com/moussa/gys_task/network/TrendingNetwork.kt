package com.moussa.gys_task.network

import com.moussa.gys_task.model.TrendingModel
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Inject

interface TrendingNetwork {


    @GET
    fun getTrending( @Url url: String): Single<MutableList<TrendingModel>>
}