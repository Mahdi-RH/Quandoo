package com.quandoo.androidtask.data.network;

import com.quandoo.androidtask.data.network.model.CustomerNetEntity;
import com.quandoo.androidtask.data.network.model.ReservationNetEntity;
import com.quandoo.androidtask.data.network.model.TableNetEntity;

import java.util.List;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface RestaurantService {

    @GET("/quandoo-assessment/customers.json")
    Single<List<CustomerNetEntity>> getCustomers();

    @GET("/quandoo-assessment/reservations.json")
    Single<List<ReservationNetEntity>> getReservations();

    @GET("/quandoo-assessment/tables.json")
    Single<List<TableNetEntity>> getTables();

}