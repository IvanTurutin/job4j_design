package ru.job4j.io;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.Arrays;

public class JsonTest implements Serializable {
    private boolean bl;
    private int i;
    private String string;
    private Contact contact;
    private int[] arr;

    public JsonTest(boolean bl, int i, String string, Contact contact, int[] arr) {
        this.bl = bl;
        this.i = i;
        this.string = string;
        this.contact = contact;
        this.arr = arr;
    }

    public boolean isBl() {
        return bl;
    }

    public int getI() {
        return i;
    }

    public String getString() {
        return string;
    }

    public Contact getContact() {
        return contact;
    }

    public int[] getArr() {
        return arr;
    }

    @Override
    public String toString() {
        return "JsonTest{"
                + "bl=" + bl
                + ", i=" + i
                + ", string='" + string + '\''
                + ", contact=" + contact
                + ", arr=" + Arrays.toString(arr)
                + '}';
    }

    public static void main(String[] args) {
        JsonTest jsonTest = new JsonTest(
                true, 5, "five", new Contact(123456, "11-22-33"), new int[]{1, 2}
        );
        final Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(jsonTest));

        final String jsonTestString =
                "{"
                        + "\"bl\":true,"
                        + "\"i\":5,"
                        + "\"string\":\"five\","
                        + "\"contact\":"
                        + "{"
                        + "\"zipCode\":123456,\"phone\":\"11-22-33\""
                        + "},"
                        + "\"arr\":[1,2]"
                        + "}";
        final JsonTest jsonTestRestore = gson.fromJson(jsonTestString, JsonTest.class);
        System.out.println(jsonTestRestore);
    }
}
