package com.luxevista.resort;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luxevista.resort.adapter.RoomAdapter;
import com.luxevista.resort.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomBookingActivity extends AppCompatActivity {

    private RecyclerView roomRecyclerView;
    private RoomAdapter roomAdapter;
    private List<Room> roomList;
    private DatabaseHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booking);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Retrieve userId from the intent (assuming it’s passed from the previous activity)
        userId = getIntent().getIntExtra("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error: User not identified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        roomRecyclerView = findViewById(R.id.roomList);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        roomList = new ArrayList<>();
        populateRooms();

        roomAdapter = new RoomAdapter(roomList, room -> {
            // Save booking to database
            boolean isBooked = databaseHelper.addBooking(userId, room.getRoomType(), "2024-12-15", "2024-12-20");

            if (isBooked) {
                Toast.makeText(this, "Room booked: " + room.getRoomType(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to book room: " + room.getRoomType(), Toast.LENGTH_SHORT).show();
            }
        });

        roomRecyclerView.setAdapter(roomAdapter);
    }

    private void populateRooms() {
        roomList.add(new Room("Ocean View Suite", "Rs16 000", "2 Beds, Balcony", R.drawable.ocean_view_suite));
        roomList.add(new Room("Deluxe Room", "Rs25 000", "1 Bed, Ocean View", R.drawable.deluxe_room));
        roomList.add(new Room("Family Suite", "RS40 000", "3 Beds, Living Area", R.drawable.family_suite));
    }
}
