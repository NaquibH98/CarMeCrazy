package com.example.carmecrazy.remote;

import com.example.carmecrazy.model.Booking;
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

public interface BookingService {
    @GET("BOOKING")
    Call<List<Booking>> getAllBooking(@Header("api-key") String api_key);

    @GET("BOOKING/{booking_id}")
    Call<Booking> getBooking(@Header("api-key") String api_key, @Path("booking_id") int booking_id);

    @FormUrlEncoded
    @POST("BOOKING")
    Call<Booking> addBooking(@Header ("api-key") String apiKey, @Field("pickup_date") String pickup_date,
                         @Field("return_date") String return_date, @Field("car_id") int carId, @Field("user_id") int userId);

    @DELETE("BOOKING/{booking_id}")
    Call<DeleteResponse> deleteBooking(@Header ("api-key") String apiKey, @Path("booking_id") int booking_id);

    @FormUrlEncoded
    @POST("BOOKING/{booking_id}")
    Call<Booking> updateBooking(@Header ("api-key") String apiKey, @Path("booking_id") int booking_id,
                                @Field("pickup_date") String pickup_date, @Field("return_date") String return_date,
                                @Field("state") String state,
                                @Field("total_price") double total_price, @Field("car_id") int car_id, @Field("user_id") int user_id);
}
