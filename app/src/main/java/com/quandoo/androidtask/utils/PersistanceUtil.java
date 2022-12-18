package com.quandoo.androidtask.utils;

import static com.quandoo.androidtask.utils.Constants.CUSTOMERS_FILE_NAME;
import static com.quandoo.androidtask.utils.Constants.RESERVATIONS_FILE_NAME;
import static com.quandoo.androidtask.utils.Constants.TABLES_FILE_NAME;

import android.content.Context;

import com.quandoo.androidtask.App;
import com.quandoo.androidtask.domain.model.Customer;
import com.quandoo.androidtask.domain.model.Reservation;
import com.quandoo.androidtask.domain.model.Table;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersistanceUtil {


    /**
     * Saves a serializable object.
     *
     * @param objectToSave The object to save.
     * @param fileName     The name of the file.
     * @param <T>          The type of the object.
     */

    private static <T extends Serializable> void saveSerializable(T objectToSave, String fileName) {
        try {
            FileOutputStream fileOutputStream = App.instance.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(objectToSave);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a serializable object.
     *
     * @param fileName The filename.
     * @param <T>      The object type.
     *
     * @return the serializable object.
     */

    public static <T extends Serializable> T readSerializable(String fileName) {
        T objectToReturn = null;

        try {
            FileInputStream fileInputStream = App.instance.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectToReturn = (T) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objectToReturn;
    }

    /**
     * Removes a specified file.
     *
     * @param filename The name of the file.
     */

    public static void removeSerializable(String filename) {
        App.instance.deleteFile(filename);
    }

    public static void writeCustomersToFile(List<Customer> customerList) {
        saveSerializable(new ArrayList(customerList), CUSTOMERS_FILE_NAME);
    }


    public static void writeTablesToFile(List<Table> tableList) {
        saveSerializable(new ArrayList(tableList), TABLES_FILE_NAME);
    }


    public static void writeReservationsToFile(List<Reservation> reservationList) {
        saveSerializable(new ArrayList(reservationList), RESERVATIONS_FILE_NAME);
    }
}
