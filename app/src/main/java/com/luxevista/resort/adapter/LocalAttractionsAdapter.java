package com.luxevista.resort.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxevista.resort.R;
import com.luxevista.resort.model.Attraction;

import java.util.List;

public class LocalAttractionsAdapter extends RecyclerView.Adapter<LocalAttractionsAdapter.ViewHolder> {

    private List<Attraction> attractions;

    public LocalAttractionsAdapter(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attraction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attraction attraction = attractions.get(position);
        holder.titleTextView.setText(attraction.getTitle());
        holder.descriptionTextView.setText(attraction.getDescription());
        holder.imageView.setImageResource(attraction.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_title);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
            imageView = itemView.findViewById(R.id.iv_image);
        }
    }
}
