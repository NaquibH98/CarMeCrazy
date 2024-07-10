package com.example.carmecrazy.model;

public class Booking {
    int BookingID;
    String Pickup_Date;
    String Return_Date;
    String State;
    Double Price;
    int id; // user id
    int CarID;

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        BookingID = bookingID;
    }

    public String getPickup_Date() {
        return Pickup_Date;
    }

    public void setPickup_Date(String pickup_Date) {
        Pickup_Date = pickup_Date;
    }

    public String getReturn_Date() {
        return Return_Date;
    }

    public void setReturn_Date(String return_Date) {
        Return_Date = return_Date;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarID() {
        return CarID;
    }

    public void setCarID(int carID) {
        CarID = carID;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "BookingID=" + BookingID +
                ", Pickup_Date='" + Pickup_Date + '\'' +
                ", Return_Date='" + Return_Date + '\'' +
                ", State='" + State + '\'' +
                ", Price=" + Price +
                ", id=" + id +
                ", CarID=" + CarID +
                '}';
    }
}