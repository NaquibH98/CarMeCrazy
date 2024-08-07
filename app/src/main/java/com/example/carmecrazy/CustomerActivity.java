package com.example.carmecrazy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carmecrazy.model.User;
import com.example.carmecrazy.sharedpref.SharedPrefManager;

public class CustomerActivity extends AppCompatActivity {

    private TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cust_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // get references
        tvHello = findViewById(R.id.tvHello);

        // greet the user
        // if the user is not logged in we will directly them to LoginActivity
        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        if (!spm.isLoggedIn()) {
            finish();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            User user = spm.getUser();
            tvHello.setText("Hello " + user.getUsername());
        }

    }

    public void logoutClicked(View view) {

        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        spm.logout();

        Toast.makeText(getApplicationContext(), "You have successfully logged out.",
                Toast.LENGTH_LONG).show();

        finish();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
/**
    public void rentClicked(View view) {


        Intent intent = new Intent(this, BookingCarActivity.class);
        startActivity(intent);

    } */

    public void newBookingClicked(View view) {
        // forward user to NewBookingActivity
        Intent intent = new Intent(getApplicationContext(), CustomerCarListActivity.class);
        startActivity(intent);
    }

    public void carListClicked(View view) {
        // forward user to CustomerCarListActivity
        Intent intent = new Intent(getApplicationContext(), CustomerCarListActivity.class);
        startActivity(intent);
    }

    public void bookingListClicked(View view) {
        // forward user to BookingListActivity
        Intent intent = new Intent(getApplicationContext(), BookingListActivity.class);
        startActivity(intent);
    }
}