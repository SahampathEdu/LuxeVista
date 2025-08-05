package com.luxevista.resort;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luxevista.resort.adapter.ServiceAdapter;
import com.luxevista.resort.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceBookingActivity extends AppCompatActivity {

    private RecyclerView serviceRecyclerView;
    private ServiceAdapter serviceAdapter;
    private List<Service> serviceList;
    private DatabaseHelper databaseHelper;
    private int userId;  // To store the user ID passed from the previous activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Retrieve the user ID from the intent
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error: User ID not found", Toast.LENGTH_SHORT).show();
            finish();  // Exit if user ID is not available
            return;
        }

        serviceRecyclerView = findViewById(R.id.serviceList);
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        serviceList = new ArrayList<>();
        populateServices();

        serviceAdapter = new ServiceAdapter(serviceList, service -> {
            // Save service booking in the database
            boolean success = databaseHelper.bookService(userId, service.getServiceName(), "2024-12-05"); // Example date, can be dynamic
            if (success) {
                Toast.makeText(this, "Service booked: " + service.getServiceName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to book service: " + service.getServiceName(), Toast.LENGTH_SHORT).show();
            }
        });
        serviceRecyclerView.setAdapter(serviceAdapter);
    }

    private void populateServices() {
        serviceList.add(new Service("Spa Treatment", "Rs10 000", "Relaxing full-body massage"));
        serviceList.add(new Service("Poolside Cabana", "Rs16 000", "Private cabana for the day"));
        serviceList.add(new Service("Fine Dining", "Rs15 000", "Exclusive 5-course meal"));
        serviceList.add(new Service("Guided Beach Tour", "Rs25 000", "Private beach tour with a guide"));
    }
}
