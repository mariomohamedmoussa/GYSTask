package com.moussa.gys_task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.annotations.NonNull
import javax.inject.Inject
import javax.inject.Provider


class ViewModelFactory @Inject constructor(myViewModelProvider: Provider<TrendingListViewModel>) :
    ViewModelProvider.Factory {
    private val mMyViewModelProvider: Provider<TrendingListViewModel> = myViewModelProvider

    @NonNull
    override fun <T : ViewModel?> create(@NonNull modelClass: Class<T>): T {
        val viewModel: ViewModel = if (modelClass == TrendingListViewModel::class.java) {
            mMyViewModelProvider.get()
        } else {
            throw RuntimeException("unsupported view model class: $modelClass")
        }
        return viewModel as T
    }

}