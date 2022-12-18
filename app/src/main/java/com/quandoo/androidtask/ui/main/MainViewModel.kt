package com.quandoo.androidtask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quandoo.androidtask.domain.model.RestaurantData
import com.quandoo.androidtask.domain.usecase.RestaurantUseCase
import com.quandoo.androidtask.utils.Logger
import com.quandoo.androidtask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject


@HiltViewModel
 class MainViewModel @Inject constructor(private val restaurantUseCase: RestaurantUseCase, private val processScheduler: Scheduler) :
    ViewModel() ,Logger{

    private val disposables = CompositeDisposable()
    private val _restaurantDataLive = MutableLiveData<Resource<RestaurantData>>()



     fun fetchData() {
        _restaurantDataLive.postValue(Resource.Loading())
        restaurantUseCase.fetchData()
            .subscribeOn(processScheduler)
            .subscribe(object : SingleObserver<RestaurantData> {

            override fun onSubscribe(d: Disposable) {
                disposables.add(d)
            }

            override fun onSuccess(restaurantData: RestaurantData) {

                if (restaurantData.isStoredData.not()) {
                    restaurantData.reservations?.forEach { reservation ->
                        restaurantData.tables?.forEach { table ->
                            if (table.id == reservation.tableId) {
                                restaurantData.customers?.forEach { customer ->
                                    if (customer.id == reservation.userId) {
                                        table.reservedBy =
                                            customer.firstName + " " + customer.lastName;
                                    }

                                }
                            }

                        }
                    }
                }
                _restaurantDataLive.postValue(Resource.Success(restaurantData))
            }

            override fun onError(e: Throwable) {
                _restaurantDataLive.postValue(Resource.Error(e.message?:"unExpected error happened"))
                log(e.localizedMessage)
            }
        })
    }

    fun getTables()=_restaurantDataLive.value?.data?.tables
    fun getCustomers()=_restaurantDataLive.value?.data?.customers
    fun getReservations()=_restaurantDataLive.value?.data?.reservations

    fun getRestaurantData(): LiveData<Resource<RestaurantData>> = _restaurantDataLive

    override fun onCleared() {
        disposables.clear()
    }
}