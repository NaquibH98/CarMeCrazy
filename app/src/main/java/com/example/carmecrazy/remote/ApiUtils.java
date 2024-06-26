package com.example.carmecrazy.remote;


public class ApiUtils {

    // REST API server URL
    public static final String BASE_URL = "http://178.128.220.20/2022911085/api/";

    // return UserService instance
    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    // return BookService instance
    public static CarService getCarService() {
        return RetrofitClient.getClient(BASE_URL).create(CarService.class);
    }

}
