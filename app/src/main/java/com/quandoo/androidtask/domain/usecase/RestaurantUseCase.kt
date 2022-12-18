package com.quandoo.androidtask.domain.usecase

import com.quandoo.androidtask.domain.model.RestaurantData
import io.reactivex.Single

interface RestaurantUseCase {

    fun fetchData(): Single<RestaurantData>
}