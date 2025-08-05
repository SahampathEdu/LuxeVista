package com.luxevista.resort.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luxevista.resort.R;
import com.luxevista.resort.model.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private OnBookingClickListener listener;

    public interface OnBookingClickListener {
        void onBookingClick(Booking booking);
    }

    public BookingAdapter(List<Booking> bookingList, OnBookingClickListener listener) {
        this.bookingList = bookingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.bookingTypeTextView.setText(booking.getBookingType());
        holder.bookingDetailsTextView.setText(booking.getBookingDetails());

        holder.removeButton.setOnClickListener(v -> listener.onBookingClick(booking));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        public TextView bookingTypeTextView, bookingDetailsTextView;
        public Button removeButton;

        public BookingViewHolder(View itemView) {
            super(itemView);
            bookingTypeTextView = itemView.findViewById(R.id.tv_booking_type);
            bookingDetailsTextView = itemView.findViewById(R.id.tv_booking_details);
            removeButton = itemView.findViewById(R.id.btn_remove_booking);
        }
    }
}
