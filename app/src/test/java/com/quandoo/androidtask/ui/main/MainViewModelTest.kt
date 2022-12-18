package com.quandoo.androidtask.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.quandoo.androidtask.data.FakeRestaurantRepository
import com.quandoo.androidtask.data.FakeRestaurantUseCase
import com.quandoo.androidtask.domain.model.RestaurantData
import com.quandoo.androidtask.utils.Resource
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainViewModelTest{

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeRestaurantRepository
    private lateinit var viewModel: MainViewModel
    private lateinit var useCase: FakeRestaurantUseCase

    @Before
    fun setUp() {
        val testScheduler = Schedulers.trampoline()
        repository= FakeRestaurantRepository()
        useCase = FakeRestaurantUseCase(repository)
        viewModel= MainViewModel(useCase, testScheduler)
    }

    @Test
    fun `fetch data success state`() {
        viewModel.fetchData()
        Truth.assertThat(viewModel.getRestaurantData().value is Resource<RestaurantData>).isTrue()
        Truth.assertThat(viewModel.getRestaurantData().value?.message).isNull()
        Thread.sleep(2_000)
        Truth.assertThat(viewModel.getRestaurantData().value is Resource.Success).isTrue()
        Truth.assertThat(repository.getCustomerList().size).isEqualTo(viewModel.getRestaurantData().value?.data?.customers?.size)
        Truth.assertThat(repository.getReservationList().size).isEqualTo(viewModel.getRestaurantData().value?.data?.reservations?.size)
        Truth.assertThat(repository.getTableList().size).isEqualTo(viewModel.getRestaurantData().value?.data?.tables?.size)
    }



}