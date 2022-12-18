package com.quandoo.androidtask.domain.model

import java.io.Serializable

data class Reservation(
    val userId: Long,
    val tableId: Long,
    val id: Long
):Serializable