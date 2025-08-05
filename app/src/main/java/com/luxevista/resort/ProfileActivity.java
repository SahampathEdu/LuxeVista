package com.luxevista.resort;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luxevista.resort.adapter.BookingAdapter;
import com.luxevista.resort.model.Booking;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameInput, emailInput;
    private Button updateButton;
    private RecyclerView bookingsRecyclerView;
    private BookingAdapter bookingAdapter;
    private DatabaseHelper databaseHelper;
    private int userId;
    private List<Booking> bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // UI references
        nameInput = findViewById(R.id.profileNameInput);
        emailInput = findViewById(R.id.profileEmailInput);
        updateButton = findViewById(R.id.updateProfileButton);
        bookingsRecyclerView = findViewById(R.id.bookingHistoryList);

        // Database helper initialization
        databaseHelper = new DatabaseHelper(this);
        bookingList = new ArrayList<>(); // Initialize bookingList here

        // Retrieve user email and user details
        String email = getIntent().getStringExtra("user_email");
        Cursor cursor = databaseHelper.getUserByEmail(email);
        if (cursor != null && cursor.moveToFirst()) {
            try {
                userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                nameInput.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                emailInput.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            } catch (Exception e) {
                Toast.makeText(this, "Error fetching user details: " + e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                cursor.close(); // Always close cursor
            }
        } else {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
        }

        // Set up RecyclerView for bookings
        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingAdapter = new BookingAdapter(bookingList, booking -> {
            // Handle booking removal
            boolean success = databaseHelper.deleteBooking(booking.getBookingId());
            if (success) {
                Toast.makeText(this, "Booking removed: " + booking.getBookingType(), Toast.LENGTH_SHORT).show();
                populateBookings(); // Refresh bookings after deletion
                populateServices(); // Refresh services after deletion
            } else {
                Toast.makeText(this, "Failed to remove booking", Toast.LENGTH_SHORT).show();
            }
        });
        bookingsRecyclerView.setAdapter(bookingAdapter);

        // Populate both bookings and services
        populateBookings();
        populateServices();

        // Update profile
        updateButton.setOnClickListener(v -> {
            String updatedName = nameInput.getText().toString().trim();
            if (updatedName.isEmpty()) {
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean updated = databaseHelper.updateUser(userId, updatedName);
            if (updated) {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to populate bookings
    private void populateBookings() {
        bookingList.clear(); // Clear the list to avoid duplicates
        Cursor cursor = databaseHelper.getUserBookings(userId);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int bookingId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String roomType = cursor.getString(cursor.getColumnIndexOrThrow("room_type"));
                    String checkInDate = cursor.getString(cursor.getColumnIndexOrThrow("check_in_date"));
                    String checkOutDate = cursor.getString(cursor.getColumnIndexOrThrow("check_out_date"));

                    // Combine booking details for display purposes
                    String bookingDetails = "Room: " + roomType + "\nCheck-in: " + checkInDate + "\nCheck-out: " + checkOutDate;

                    bookingList.add(new Booking(bookingId, "Room Booking", bookingDetails));
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error fetching bookings: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                cursor.close(); // Close the cursor after use
            }
            bookingAdapter.notifyDataSetChanged(); // Refresh the adapter
        } else {
            Toast.makeText(this, "No bookings found.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to populate services
    private void populateServices() {
        Cursor serviceCursor = databaseHelper.getServicesByUserId(userId);
        if (serviceCursor != null) {
            try {
                while (serviceCursor.moveToNext()) {
                    int serviceId = serviceCursor.getInt(serviceCursor.getColumnIndexOrThrow("id"));
                    String serviceName = serviceCursor.getString(serviceCursor.getColumnIndexOrThrow("service_name"));
                    String serviceDate = serviceCursor.getString(serviceCursor.getColumnIndexOrThrow("service_date"));

                    String serviceDetails = "Service: " + serviceName + "\nDate: " + serviceDate;

                    bookingList.add(new Booking(serviceId, "Service Booking", serviceDetails));
                }
                bookingAdapter.notifyDataSetChanged(); // Refresh the adapter after adding services
            } catch (Exception e) {
                Log.e("ProfileActivity", "Error fetching services", e);  // Logs detailed error in logcat
                Toast.makeText(this, "Error fetching services: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                serviceCursor.close(); // Close the cursor after use
            }
        } else {
            Toast.makeText(this, "No services found.", Toast.LENGTH_SHORT).show();
        }
    }
}
