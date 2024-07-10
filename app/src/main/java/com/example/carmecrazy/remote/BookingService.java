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

    @GET("BOOKING/{BookingID}")
    Call<Booking> getBooking(@Header("api-key") String api_key, @Path("BookingID") int BookingID);

    @FormUrlEncoded
    @POST("BOOKING")
    Call<Booking> addBooking(@Header ("api-key") String apiKey, @Field("Pickup_Date") String Pickup_Date,
                         @Field("Return_Date") String Return_Date, @Field("State") String State,
                         @Field("Price") Double Price);

    @DELETE("BOOKING/{BookingID}")
    Call<DeleteResponse> deleteBooking(@Header ("api-key") String apiKey, @Path("BookingID") int BookingID);

    @FormUrlEncoded
    @POST("BOOKING/{BookingID}")
    Call<Booking> updateBooking(@Header ("api-key") String apiKey, @Path("BookingID") int BookingID,
                                @Field("Pickup_Date") String Pickup_Date, @Field("Return_Date") String Return_Date,
                                @Field("State") String State,
                                @Field("Price") Double Price);
}
