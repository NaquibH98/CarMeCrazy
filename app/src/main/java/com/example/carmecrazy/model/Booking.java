package com.example.carmecrazy.model;

public class Booking {
    int booking_id;
    String pickup_date;
    String return_date;
    String state;
    double total_price;
    int user_id; // user id
    int car_id;
    User user;
    Car car;

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "booking_id=" + booking_id +
                ", pickup_date='" + pickup_date + '\'' +
                ", return_date='" + return_date + '\'' +
                ", state='" + state + '\'' +
                ", total_price=" + total_price +
                ", user_id=" + user_id +
                ", car_id=" + car_id +
                ", user=" + user +
                ", car=" + car +
                '}';
    }

    public Booking(int booking_id, String pickup_date, String return_date, String state, double total_price, int user_id, int car_id, User user, Car car) {
        this.booking_id = booking_id;
        this.pickup_date = pickup_date;
        this.return_date = return_date;
        this.state = state;
        this.total_price = total_price;
        this.user_id = user_id;
        this.car_id = car_id;
        this.user = user;
        this.car = car;
    }

    public Booking() {
    }
}
