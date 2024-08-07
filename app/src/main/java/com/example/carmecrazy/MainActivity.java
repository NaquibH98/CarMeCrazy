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

public class MainActivity extends AppCompatActivity {

   private TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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

    public void carListClicked(View view) {
        // forward user to carListActivity
        Intent intent = new Intent(getApplicationContext(), CarListActivity.class);
        startActivity(intent);
    }

    public void bookingListAdminClicked(View view) {
        // forward user to BookingListAdminActivity
        Intent intent = new Intent(getApplicationContext(), BookingListAdminActivity.class);
        startActivity(intent);
    }

}