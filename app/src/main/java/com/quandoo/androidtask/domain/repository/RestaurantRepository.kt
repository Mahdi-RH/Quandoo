package com.quandoo.androidtask.domain.repository

import com.quandoo.androidtask.data.network.model.CustomerNetEntity
import com.quandoo.androidtask.data.network.model.ReservationNetEntity
import com.quandoo.androidtask.data.network.model.TableNetEntity
import io.reactivex.Single

interface RestaurantRepository {

    fun getTables(): Single<List<TableNetEntity>>

    fun getReservations(): Single<List<ReservationNetEntity>>

    fun getCustomers(): Single<List<CustomerNetEntity>>

}