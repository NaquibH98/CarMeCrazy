package com.example.carmecrazy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.example.carmecrazy.model.Car;
import com.example.carmecrazy.model.Booking;
import com.example.carmecrazy.model.User;
import com.example.carmecrazy.remote.ApiUtils;
import com.example.carmecrazy.remote.CarService;
import com.example.carmecrazy.remote. BookingService;
import com.example.carmecrazy.sharedpref.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewBookingActivity extends AppCompatActivity {
    private static TextView tvPickupDate;
    private static Date pickup_date;
    private static TextView tvReturnDate;
    private static Date return_date;
    private Booking booking;
    private BookingService bookingService;
    private Car car;
    private CarService carService;

    /**
     * Date picker fragment class
     * Reference: https://developer.android.com/guide/topics/ui/controls/pickers
     */
    public static class PickupDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            // create a date object from selected year, month and day
            pickup_date = new GregorianCalendar(year, month, day).getTime();

            // display in the label beside the button with specific date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            tvPickupDate.setText( sdf.format(pickup_date) );
        }
    }

    /**
     * Date picker fragment class
     * Reference: https://developer.android.com/guide/topics/ui/controls/pickers
     */
    public static class ReturnDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            // create a date object from selected year, month and day
            return_date = new GregorianCalendar(year, month, day).getTime();

            // display in the label beside the button with specific date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            tvReturnDate.setText( sdf.format(return_date) );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // retrieve car details based on selected id

        // get car id sent by CarListActivity, -1 if not found
        Intent intent = getIntent();
        int carId = intent.getIntExtra("CarID", -1);

        // get user info from SharedPreferences
        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        User user = spm.getUser();
        String token = user.getToken();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // get car service instance
        carService = ApiUtils.getCarService();

        // execute the API query. send the token and car id
        carService.getCar(token, carId).enqueue(new Callback<Car>() {

            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                if (response.code() == 200) {
                    // server return success

                    // get car object from response
                    car = response.body();

                    // get references to the view elements
                    TextView tvBrand = findViewById(R.id.tvBrand);
                    TextView tvName = findViewById(R.id.tvName);
                    TextView tvPrice = findViewById(R.id.tvPrice);
                    TextView tvPlateNo = findViewById(R.id.tvPlateNo);
                    //TextView tvState = findViewById(R.id.tvState);
                    //TextView tvTotal_Price = findViewById(R.id.tvTotal_Price);
                    tvPickupDate = findViewById(R.id.tvPickupDate);
                    tvReturnDate = findViewById(R.id.tvReturnDate);

                    // set values
                    tvBrand.setText(car.getCar_Brand());
                    tvName.setText(car.getCar_Name());
                    tvPrice.setText(car.getCar_Price());
                    tvPlateNo.setText(car.getCar_PlateNo());
                    //tvState.setText(booking.getState());
                    //tvTotal_Price.setText(Double.toString(booking.getTotal_price()));

                    // set default pickup date value to current date
                    pickup_date = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(pickup_date);
                    c.add(Calendar.DATE, 2);  // number of days to add
                    return_date = c.getTime();  // dt is now the new date

                    // display in the label beside the button with specific date format
                    tvPickupDate.setText( sdf.format(pickup_date) );
                    tvReturnDate.setText( sdf.format(return_date) );

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
            public void onFailure(Call<Car> call, Throwable t) {
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

     public void showPickupDatePickerDialog(View v) {
        DialogFragment newFragment = new NewBookingActivity.PickupDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showReturnDatePickerDialog(View v) {
        DialogFragment newFragment = new NewBookingActivity.ReturnDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Called when Add Booking button is clicked
     * @param v
     */
    public void addBooking(View v) {
        // get values in form
        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        User user = spm.getUser();

        int carId = car.getCarID();
        int userId = user.getId();
        String state =  booking.getState();
        double total_price = booking.getTotal_price();

        // convert createdAt date to format in DB
        // reference: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String pDate = booking.getPickup_date();
        String rDate = booking.getReturn_date();

        // send request to add new booking to the REST API
        BookingService bookingService = ApiUtils.getBookingService();
        Call<Booking> call = bookingService.addBooking(user.getToken(), pDate, rDate, state, total_price, userId, carId);

        // execute
        call.enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {

                // for debug purpose
                Log.d("MyApp:", "Response: " + response.raw().toString());

                if (response.code() == 201) {
                    // booking added successfully
                    Booking addedBooking = response.body();
                    // display message
                    Toast.makeText(getApplicationContext(),
                            addedBooking.getCar().getCar_Name() + " added successfully.",
                            Toast.LENGTH_LONG).show();

                    // end this activity and forward user to BookingListActivity
                    Intent intent = new Intent(getApplicationContext(), BookingListActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (response.code() == 401) {
                    // invalid token, ask user to relogin
                    Toast.makeText(getApplicationContext(), "Invalid session. Please login again", Toast.LENGTH_LONG).show();
                    clearSessionAndRedirect();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_LONG).show();
                    // server return other error
                    Log.e("MyApp: ", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error connecting", Toast.LENGTH_LONG).show();
                // for debug purpose

            }
        });
    }
}
