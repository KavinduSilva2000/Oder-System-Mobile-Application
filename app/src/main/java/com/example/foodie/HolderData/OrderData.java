package com.example.foodie.HolderData;

public class OrderData {

    String Customer,Date,Items,Status,Time,UserID;

    public OrderData() {
    }

    public OrderData(String customer, String date, String items, String status, String time, String userID) {
        Customer = customer;
        Date = date;
        Items = items;
        Status = status;
        Time = time;
        UserID = userID;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
