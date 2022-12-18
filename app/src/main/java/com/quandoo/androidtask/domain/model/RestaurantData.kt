package com.quandoo.androidtask.domain.model

data class RestaurantData(
    var tables: List<Table>? = null,
    var customers: List<Customer>? = null,
    var reservations: List<Reservation>? = null,
    var isStoredData: Boolean = false
)