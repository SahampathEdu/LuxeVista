package com.luxevista.resort;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;

public class HomeActivity extends AppCompatActivity {

    private String userEmail;
    private int userId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Retrieve the user email (assuming it's passed via Intent)
        userEmail = getIntent().getStringExtra("user_email");

        if (userEmail == null || userEmail.isEmpty()) {
            Toast.makeText(this, "Error: User email not found", Toast.LENGTH_SHORT).show();
            finish();  // Exit activity if email is not available
            return;
        }

        // Retrieve user ID based on email
        Cursor cursor = databaseHelper.getUserByEmail(userEmail);
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            cursor.close();
        } else {
            Toast.makeText(this, "Error: User not found in database", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Button references
        Button roomBookingButton = findViewById(R.id.btn_room_booking);
        Button serviceReservationButton = findViewById(R.id.btn_service_reservation);
        Button localAttractionsButton = findViewById(R.id.btn_local_attractions);
        Button notificationsButton = findViewById(R.id.btn_notifications);
        Button profileButton = findViewById(R.id.btn_profile);
        Button logoutButton = findViewById(R.id.btn_logout);

        // Navigate to Room Booking
        roomBookingButton.setOnClickListener(v -> {
            Intent roomBookingIntent = new Intent(HomeActivity.this, RoomBookingActivity.class);
            roomBookingIntent.putExtra("user_id", userId);
            startActivity(roomBookingIntent);
        });

        // Navigate to Service Reservation
        serviceReservationButton.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(HomeActivity.this, ServiceBookingActivity.class);
            serviceIntent.putExtra("user_id", userId);
            startActivity(serviceIntent);
        });

        // Navigate to Local Attractions
        localAttractionsButton.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, LocalAttractionsActivity.class)));

        // Navigate to Notifications
        notificationsButton.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NotificationsActivity.class)));

        // Navigate to Profile Management
        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
            profileIntent.putExtra("user_email", userEmail);
            startActivity(profileIntent);
        });

        // Logout
        logoutButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }
}
