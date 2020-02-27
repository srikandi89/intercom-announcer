package com.intercom.announcer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intercom.announcer.R;
import com.intercom.announcer.entities.Customer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListViewHolder> {

    private Context context;
    private List<Customer> customers;

    public CustomerListAdapter(Context context) {
        this.context = context;
        customers = new ArrayList<>();
    }

    public void updateList(List<Customer> newList) {
        if (customers != null) {
            customers.clear();
            customers.addAll(newList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CustomerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_list_item, parent, false);

        return new CustomerListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.getTvName().setText(customer.getName());
        holder.getTvUserId().setText("#"+customer.getUserId());
        holder.getTvDistance().setText(new DecimalFormat("##.##").format(customer.getDistance())+" km");
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}
