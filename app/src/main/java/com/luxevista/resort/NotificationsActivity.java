// File: NotificationsActivity.java
package com.luxevista.resort;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luxevista.resort.adapter.NotificationAdapter;
import com.luxevista.resort.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notificationsRecyclerView = findViewById(R.id.notificationsList);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
        populateNotifications();

        notificationAdapter = new NotificationAdapter(notificationList);
        notificationsRecyclerView.setAdapter(notificationAdapter);
    }

    private void populateNotifications() {
        notificationList.add(new Notification("Holiday Discount", "Enjoy 20% off all bookings this holiday season!"));
        notificationList.add(new Notification("Spa Offer", "Get a free massage with every spa booking."));
    }
}
