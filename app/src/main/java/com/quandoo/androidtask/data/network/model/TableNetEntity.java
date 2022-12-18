package com.quandoo.androidtask.data.network.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class TableNetEntity implements Serializable {

    @SerializedName("shape")
    String shape;

    @SerializedName("id")
    long id;

    public @Nullable String reservedBy;

    public TableNetEntity(String shape, long id) {
        this.shape = shape;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Table{" +
                "shape='" + shape + '\'' +
                ", id=" + id +
                '}';
    }

    public String getShape() {
        return shape;
    }

    public long getId() {
        return id;
    }
}
