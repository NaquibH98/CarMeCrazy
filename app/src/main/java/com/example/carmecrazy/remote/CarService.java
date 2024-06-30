package com.example.carmecrazy.remote;

import com.example.carmecrazy.model.Car;
import com.example.carmecrazy.model.DeleteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CarService {

    @GET("CAR")
    Call<List<Car>> getAllCars(@Header("api-key") String api_key);

    @GET("CAR/{CarID}")
    Call<Car> getCar(@Header("api-key") String api_key, @Path("CarID") int CarID);
    @FormUrlEncoded
    @POST("CAR")
    Call<Car> addCar(@Header ("api-key") String apiKey, @Field("Car_Brand") String Car_Brand,
                       @Field("Car_Name") String Car_Name, @Field("Car_Price") String Car_Price,
                       @Field("Car_PlateNo") String Car_PlateNo);

    @DELETE("CAR/{CarID}")
    Call<DeleteResponse> deleteCar(@Header ("api-key") String apiKey, @Path("CarID") int CarID);

    @FormUrlEncoded
    @POST("CAR/{CarID}")
    Call<Car> updateCar(@Header ("api-key") String apiKey, @Path("CarID") int CarID,
                       @Field("Car_Brand") String Car_Brand,
                        @Field("Car_Name") String Car_Name, @Field("Car_Price") String Car_Price,
                       @Field("Car_PlateNo") String Car_PlateNo);
}
