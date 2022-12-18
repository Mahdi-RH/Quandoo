package com.quandoo.androidtask.data.usecase

import com.quandoo.androidtask.domain.model.RestaurantData
import com.quandoo.androidtask.domain.model.Customer
import com.quandoo.androidtask.domain.model.Reservation
import com.quandoo.androidtask.domain.model.Table
import com.quandoo.androidtask.domain.repository.RestaurantRepository
import com.quandoo.androidtask.domain.usecase.RestaurantUseCase
import com.quandoo.androidtask.utils.Constants.CUSTOMERS_FILE_NAME
import com.quandoo.androidtask.utils.Constants.RESERVATIONS_FILE_NAME
import com.quandoo.androidtask.utils.Constants.TABLES_FILE_NAME
import com.quandoo.androidtask.utils.PersistanceUtil
import io.reactivex.Single
import javax.inject.Inject



class RestaurantUseCaseImpl @Inject constructor(private val repository: RestaurantRepository):RestaurantUseCase{


    override fun fetchData(): Single<RestaurantData> {

        val restaurantData = RestaurantData(
            PersistanceUtil.readSerializable(TABLES_FILE_NAME),
            PersistanceUtil.readSerializable(CUSTOMERS_FILE_NAME),
            PersistanceUtil.readSerializable(RESERVATIONS_FILE_NAME))

        if (restaurantData.customers.isNullOrEmpty().not() && restaurantData.tables.isNullOrEmpty()
                .not() && restaurantData.reservations.isNullOrEmpty().not()
        ) {
            restaurantData.isStoredData = true
            return Single.just(restaurantData)
        }

       return Single.zip(
            repository.getTables(),
            repository.getCustomers()
        ) { tables, customers ->
            restaurantData.tables = tables.map {
                Table(shape = it.shape, id = it.id)
            }
            restaurantData.customers = customers.map {
                Customer(firstName = it.firstName, lastName = it.lastName, imageUrl = it.imageUrl, id = it.id)
            }
           restaurantData.customers?.let {
               PersistanceUtil.writeCustomersToFile(it)
           }
           restaurantData.isStoredData = false
           tables
        }.zipWith(repository.getReservations()) { o, reservations ->
        restaurantData.reservations = reservations.map {
            Reservation(userId = it.userId, tableId = it.tableId, id = it.id)
        }
           restaurantData
              }
    }


}