package com.luxevista.resort;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luxevista.resort.adapter.LocalAttractionsAdapter;
import com.luxevista.resort.model.Attraction;

import java.util.ArrayList;
import java.util.List;

public class LocalAttractionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocalAttractionsAdapter adapter;
    private List<Attraction> attractionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_attractions);

        recyclerView = findViewById(R.id.recyclerView_attractions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize data and adapter
        attractionList = getAttractionData();
        adapter = new LocalAttractionsAdapter(attractionList);
        recyclerView.setAdapter(adapter);
    }

    // Mock data for attractions and offers
    private List<Attraction> getAttractionData() {
        List<Attraction> attractions = new ArrayList<>();
        attractions.add(new Attraction("Beach Tour", "Explore pristine beaches with guided tours.", R.drawable.beach_tour));
        attractions.add(new Attraction("Water Sports", "Experience thrilling water sports like jet skiing.", R.drawable.water_sports));
        attractions.add(new Attraction("Fine Dining", "Savor gourmet meals at seaside restaurants.", R.drawable.fine_dining));
        attractions.add(new Attraction("Sunset Cruise", "Enjoy a romantic sunset cruise along the coast.", R.drawable.sunset_cruise));
        attractions.add(new Attraction("Spa & Wellness", "Relax with luxury spa treatments.", R.drawable.spa_wellness));
        return attractions;
    }
}
