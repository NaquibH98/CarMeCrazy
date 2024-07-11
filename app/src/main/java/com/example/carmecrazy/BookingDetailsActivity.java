package com.example.carmecrazy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carmecrazy.model.Booking;
import com.example.carmecrazy.model.User;
import com.example.carmecrazy.remote.ApiUtils;
import com.example.carmecrazy.remote.BookingService;
import com.example.carmecrazy.sharedpref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsActivity extends AppCompatActivity {
    private BookingService bookingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // retrieve booking details based on selected booking id

        // get booking id sent by BookingListActivity, -1 if not found
        Intent intent = getIntent();
        int BookingID = intent.getIntExtra("BookingID", -1);

        // get user info from SharedPreferences
        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        User user = spm.getUser();
        String token = user.getToken();

        // get booking service instance
        bookingService = ApiUtils.getBookingService();

        // execute the API query. send the token and booking id
        bookingService.getBooking(token, BookingID).enqueue(new Callback<Booking>() {

            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                if (response.code() == 200) {
                    // server return success

                    // get booking object from response
                    Booking booking = response.body();

                    // get references to the view elements
                    TextView tvBookingID = findViewById(R.id.tvBookingID);
                    TextView tvPickup_Date = findViewById(R.id.tvPickup_Date);
                    TextView tvReturn_Date = findViewById(R.id.tvReturn_Date);
                    TextView tvState = findViewById(R.id.tvState);
                    TextView tvTotal_Price = findViewById(R.id.tvTotal_Price);

                    // set values
                    tvBookingID.setText(booking.getBooking_id());
                    tvPickup_Date.setText(booking.getPickup_date());
                    tvReturn_Date.setText(booking.getReturn_date());
                    tvState.setText(booking.getState());
                    String totalprice = Double.toString(booking.getTotal_price());
                    tvTotal_Price.setText(totalprice);
                }
                else if (response.code() == 401) {
                    // unauthorized error. invalid token, ask user to relogin
                    Toast.makeText(getApplicationContext(), "Invalid session. Please login again", Toast.LENGTH_LONG).show();
                    clearSessionAndRedirect();
                }
                else {
                    // server return other error
                    Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_LONG).show();
                    Log.e("MyApp: ", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                Toast.makeText(null, "Error connecting", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void clearSessionAndRedirect() {
        // clear the shared preferences
        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        spm.logout();

        // terminate this activity
        finish();

        // forward to Login Page
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}
