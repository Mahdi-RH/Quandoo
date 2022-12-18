package com.quandoo.androidtask.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.quandoo.androidtask.data.network.RestaurantService
import com.quandoo.androidtask.data.network.createNetworkClient
import com.quandoo.androidtask.data.repository.RestaurantRepositoryImpl
import com.quandoo.androidtask.domain.repository.RestaurantRepository
import com.quandoo.androidtask.data.usecase.RestaurantUseCaseImpl
import com.quandoo.androidtask.domain.usecase.RestaurantUseCase
import com.quandoo.androidtask.ui.BaseViewModelFactory
import com.quandoo.androidtask.ui.main.MainViewModel
import com.quandoo.androidtask.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideNetworkClient(): Retrofit = createNetworkClient(BASE_URL)

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): RestaurantService =
        retrofit.create(RestaurantService::class.java)

    @Singleton
    @Provides
    fun provideRestaurantRepository(restaurantService: RestaurantService): RestaurantRepository =
        RestaurantRepositoryImpl(restaurantService)

    @Singleton
    @Provides
    fun provideRestaurantUseCase(restaurantRepository: RestaurantRepository): RestaurantUseCase =
        RestaurantUseCaseImpl(restaurantRepository)


    @Provides
    fun provideSubscribeScheduler() = Schedulers.io()

    @Provides
    fun provideMainViewModel(restaurantUseCase: RestaurantUseCase, scheduler: Scheduler) =
        ViewModelProvider(ViewModelStore(), BaseViewModelFactory {
            MainViewModel(restaurantUseCase,scheduler)
        })[MainViewModel::class.java]


}

