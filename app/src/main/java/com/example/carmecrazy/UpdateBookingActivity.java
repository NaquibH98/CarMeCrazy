package com.example.carmecrazy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carmecrazy.model.Booking;
import com.example.carmecrazy.model.Car;
import com.example.carmecrazy.model.User;
import com.example.carmecrazy.remote.ApiUtils;
import com.example.carmecrazy.remote.BookingService;
import com.example.carmecrazy.remote.CarService;
import com.example.carmecrazy.sharedpref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBookingActivity extends AppCompatActivity {
    // form fields
    private EditText txtState;
    private Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // retrieve booking id from intent
        // get booking id sent by BookingListActivity, -1 if not found
        Intent intent = getIntent();
        int BookingID = intent.getIntExtra("booking_id", -1);

        // get references to the form fields in layout
        txtState = findViewById(R.id.txtState);

        // retrieve car info from database using the car id
        // get user info from SharedPreferences
        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        User user = spm.getUser();

        // get car service instance
        BookingService bookingService = ApiUtils.getBookingService();

        // execute the API query. send the token and booking id
        bookingService.getBooking(user.getToken(), BookingID).enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                // for debug purpose
                Log.d("MyApp:", "Update Form Populate Response: " + response.raw().toString());

                if (response.code() == 200) {
                    // server return success
                    // get booking object from response
                    booking = response.body();

                    // set values into forms
                    txtState.setText(booking.getState());
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

        // terminate this MainActivity
        finish();

        // forward to Login Page
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

        /**
     * Update booking status in database when the user click Update Booking button
     * @param view
     */
    public void updateBooking(View view) {
        // get values in form
        String state = txtState.getText().toString();

        Log.d("MyApp:", "Old Booking info: " + booking.toString());

        // update the booking object retrieved in when populating the form with the new data.
        // update all fields excluding the id
        booking.setState(state);

        Log.d("MyApp:", "New Booking info: " + booking.toString());

        // get user info from SharedPreferences
        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        User user = spm.getUser();

        // send request to update the booking record to the REST API
        BookingService bookingService = ApiUtils.getBookingService();
        Call<Booking> call = bookingService.updateBooking(user.getToken(), booking.getBooking_id(), booking.getState(),
                booking.getPickup_date(), booking.getReturn_date(), booking.getTotal_price(), booking.getCar_id(), booking.getUser_id());

        // execute
        call.enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {

                // for debug purpose
                Log.d("MyApp:", "Update Request Response: " + response.raw().toString());

                if (response.code() == 200) {
                    // server return success code for update request
                    // get updated booking object from response
                    Booking updatedBooking = response.body();

                    // display message
                    displayUpdateSuccess(updatedBooking.getBooking_id() + " updated successfully.");

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
                displayAlert("Error [" + t.getMessage() + "]");
                // for debug purpose
                Log.d("MyApp:", "Error: " + t.getCause().getMessage());
            }
        });
    }

    /**
     * Displaying an alert dialog with a single button
     * @param message - message to be displayed
     */
    public void displayUpdateSuccess(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // end this activity and forward user to BookingListActivity
                        Intent intent = new Intent(getApplicationContext(), BookingListActivity.class);
                        startActivity(intent);
                        finish();

                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Displaying an alert dialog with a single button
     * @param message - message to be displayed
     */
    public void displayAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
