package com.quandoo.androidtask.domain.model

import java.io.Serializable

data class Table(
    val shape: String,
    val id: Long
):Serializable {
    var reservedBy: String? = null


}