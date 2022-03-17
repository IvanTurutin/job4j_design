package ru.job4j.io;

import org.json.JSONObject;
import org.json.JSONPropertyIgnore;

import java.io.*;
import java.nio.file.Files;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class Contact2 {

    private int zipCode;

    private String phone;

    private JsonTest jsonTest;

    public Contact2() {
    }

    public Contact2(int zipCode, String phone) {
        this.zipCode = zipCode;
        this.phone = phone;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getPhone() {
        return phone;
    }

    @JSONPropertyIgnore
    public JsonTest getJsonTest() {
        return jsonTest;
    }

    public void setJsonTest(JsonTest jsonTest) {
        this.jsonTest = jsonTest;
    }

    @Override
    public String toString() {
        return "Contact{"
                + "zipCode=" + zipCode
                + ", phone='" + phone + '\''
                + '}';
    }

    public static void main(String[] args) {
        Contact2 contact2 = new Contact2(123456, "11-22-33");
        JsonTest jsonTest = new JsonTest(false, 30, "five", contact2, new int[]{1, 2});
        contact2.setJsonTest(jsonTest);

        System.out.println(new JSONObject(jsonTest).toString());
    }
}