package com.moussa.gys_task.di

import com.moussa.gys_task.view.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeAuthActivity(): MainActivity?
}