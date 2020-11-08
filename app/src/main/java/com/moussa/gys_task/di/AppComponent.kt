package com.moussa.gys_task.di

import android.app.Application
import com.moussa.gys_task.AppController
import com.moussa.gys_task.network.TrendingNetwork
import com.moussa.gys_task.view.activities.MainActivity
import com.moussa.gys_task.viewmodel.TrendingListViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class]
)
interface AppComponent : AndroidInjector<AppController> {
    fun inject(mainActivity: MainActivity)
    fun inject(trendingListViewModel: TrendingListViewModel)

    @Component.Builder
     interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }

}