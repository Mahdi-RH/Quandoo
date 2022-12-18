package com.quandoo.androidtask.data

import com.quandoo.androidtask.domain.model.Customer
import com.quandoo.androidtask.domain.model.Reservation
import com.quandoo.androidtask.domain.model.RestaurantData
import com.quandoo.androidtask.domain.model.Table
import com.quandoo.androidtask.domain.usecase.RestaurantUseCase
import com.quandoo.androidtask.utils.PersistanceUtil
import io.reactivex.Single

class FakeRestaurantUseCase(private val fakeRestaurantRepository: FakeRestaurantRepository):RestaurantUseCase {

    override fun fetchData():Single<RestaurantData> {
        val restaurantData = RestaurantData()
        return Single.zip(
            fakeRestaurantRepository.getTables(),
            fakeRestaurantRepository.getCustomers()
        ) { tables, customers ->
            restaurantData.tables = tables.map {
                Table(shape = it.shape, id = it.id)
            }
            restaurantData.customers = customers.map {
                Customer(firstName = it.firstName, lastName = it.lastName, imageUrl = it.imageUrl, id = it.id)
            }
            restaurantData.isStoredData = false
            tables
        }.zipWith(fakeRestaurantRepository.getReservations()) { o, reservations ->
            restaurantData.reservations = reservations.map {
                Reservation(userId = it.userId, tableId = it.tableId, id = it.id)
            }
            restaurantData
        }


    }
}