package com.luxevista.resort.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luxevista.resort.R;
import com.luxevista.resort.model.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private OnRoomClickListener listener;

    public interface OnRoomClickListener {
        void onRoomClick(Room room);
    }

    public RoomAdapter(List<Room> roomList, OnRoomClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomTypeTextView.setText(room.getRoomType());
        holder.priceTextView.setText(room.getPrice());
        holder.amenitiesTextView.setText(room.getAmenities());
        holder.roomImageView.setImageResource(room.getImageResourceId());

        holder.bookButton.setOnClickListener(v -> listener.onRoomClick(room));
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView roomTypeTextView, priceTextView, amenitiesTextView;
        public ImageView roomImageView;
        public Button bookButton;

        public RoomViewHolder(View itemView) {
            super(itemView);
            roomTypeTextView = itemView.findViewById(R.id.tv_room_type);
            priceTextView = itemView.findViewById(R.id.tv_price);
            amenitiesTextView = itemView.findViewById(R.id.tv_amenities);
            roomImageView = itemView.findViewById(R.id.iv_room_image);
            bookButton = itemView.findViewById(R.id.btn_book);
        }
    }
}
