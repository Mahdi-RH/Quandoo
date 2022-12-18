package com.quandoo.androidtask.data.repository

import com.quandoo.androidtask.data.network.model.CustomerNetEntity
import com.quandoo.androidtask.data.network.model.ReservationNetEntity
import com.quandoo.androidtask.data.network.model.TableNetEntity
import com.quandoo.androidtask.data.network.RestaurantService
import com.quandoo.androidtask.domain.repository.RestaurantRepository
import io.reactivex.Single
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(private val service: RestaurantService): RestaurantRepository {

    override fun getTables(): Single<List<TableNetEntity>> = service.tables

    override fun getReservations(): Single<List<ReservationNetEntity>> = service.reservations

    override fun getCustomers(): Single<List<CustomerNetEntity>> = service.customers

}