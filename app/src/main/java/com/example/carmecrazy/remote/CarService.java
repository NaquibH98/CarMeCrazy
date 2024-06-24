package com.example.carmecrazy.remote;

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
    Call<List<CAR>> getAllBooks(@Header("api-key") String api_key);

    @GET("CAR/{id}")
    Call<CAR> getBook(@Header("api-key") String api_key, @Path("id") int id);
    @FormUrlEncoded
    @POST("CAR")
    Call<CAR> addBook(@Header ("api-key") String apiKey, @Field("isbn") String isbn,
                       @Field("name") String name, @Field("year") String year,
                       @Field("author") String author, @Field("description") String description,
                       @Field("image") String image, @Field("createdAt") String createdAt,
                       @Field("updatedAt") String updatedAt);


    @DELETE("CAR/{id}")
    Call<DeleteResponse> deleteBook(@Header ("api-key") String apiKey, @Path("id") int id);
}
