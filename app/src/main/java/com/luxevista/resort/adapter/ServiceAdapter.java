package com.luxevista.resort.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.luxevista.resort.R;
import com.luxevista.resort.model.Service;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<Service> serviceList;
    private OnServiceClickListener onServiceClickListener;

    public interface OnServiceClickListener {
        void onServiceClick(Service service);
    }

    public ServiceAdapter(List<Service> serviceList, OnServiceClickListener listener) {
        this.serviceList = serviceList;
        this.onServiceClickListener = listener;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.serviceName.setText(service.getServiceName());
        holder.price.setText(service.getPrice());
        holder.description.setText(service.getDescription());

        holder.itemView.setOnClickListener(v -> onServiceClickListener.onServiceClick(service));
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceName, price, description;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
        }
    }
}
