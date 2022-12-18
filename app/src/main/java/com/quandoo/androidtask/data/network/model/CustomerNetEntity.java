package com.quandoo.androidtask.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerNetEntity implements Serializable {

    @SerializedName("first_name")
    String firstName;

    @SerializedName("last_name")
    String lastName;

    @SerializedName("image_url")
    String imageUrl;

    @SerializedName("id")
    long id;


    public CustomerNetEntity(String firstName, String lastName, String imageUrl, long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", id=" + id +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getId() {
        return id;
    }
}
