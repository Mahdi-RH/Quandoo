package com.quandoo.androidtask.data

import com.quandoo.androidtask.data.network.model.CustomerNetEntity
import com.quandoo.androidtask.data.network.model.ReservationNetEntity
import com.quandoo.androidtask.data.network.model.TableNetEntity
import com.quandoo.androidtask.domain.repository.RestaurantRepository
import io.reactivex.Single

class FakeRestaurantRepository : RestaurantRepository {

    private var customerList = ArrayList<CustomerNetEntity>()
    private var tableList = ArrayList<TableNetEntity>()
    private var reservationList = ArrayList<ReservationNetEntity>()

    override fun getTables(): Single<List<TableNetEntity>> {
        return Single.just(makeTableList())
    }

    override fun getReservations(): Single<List<ReservationNetEntity>> {
        return Single.just(makeReservationList())
    }
    override fun getCustomers(): Single<List<CustomerNetEntity>> {
        return Single.just(makeCustomersList())
    }

    private fun makeTableList(): List<TableNetEntity> {
        tableList.clear()
        tableList.add(TableNetEntity("circle", 101))
        tableList.add(TableNetEntity("square", 102))
        val element = TableNetEntity("rectangle", 103)
        element.reservedBy = "Mother Teresa"
        tableList.add(element)


        return tableList
    }

    private fun makeReservationList(): List<ReservationNetEntity> {

       reservationList.clear()
        reservationList.add(ReservationNetEntity(1001, 103, 2))
        reservationList.add(ReservationNetEntity(1002, 104, 2))
        reservationList.add(ReservationNetEntity(1003, 105, 17))

        return reservationList
    }
    private fun makeCustomersList(): List<CustomerNetEntity> {

        customerList.clear()
        customerList.add(
            CustomerNetEntity(
                "Marilyn",
                "Monroe",
                "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/images/profile1.png",
                0
            )
        )
        customerList.add(
            CustomerNetEntity(
                "Abraham",
                "Lincoln",
                "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/images/profile2.png",
                1
            )
        )
        customerList.add(
            CustomerNetEntity(
                "Mother",
                "Teresa",
                "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/images/profile3.png",
                2
            )
        )
        return customerList
    }

    fun getCustomerList() = customerList
    fun getTableList() = tableList
    fun getReservationList() = reservationList


}
