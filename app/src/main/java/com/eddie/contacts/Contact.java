package com.eddie.contacts;

public class Contact {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;

    public Contact() {

    }

    public Contact(String name, String email, String phone, String address, String description) {

        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {

        return name + "," + email + "," + phone + "," + address + "," + description;
    }

    public static Contact newInstance(String str) {

        String[] arr = str.split(",");

        return new Contact(arr[0], arr[1], arr[2], arr[3], arr[4]);
    }
}

