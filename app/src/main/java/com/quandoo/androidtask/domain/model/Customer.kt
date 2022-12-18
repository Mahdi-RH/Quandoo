package com.quandoo.androidtask.domain.model

import java.io.Serializable


data class Customer(
    val firstName: String,
    val lastName: String,
    val imageUrl: String,
    val id: Long
):Serializable