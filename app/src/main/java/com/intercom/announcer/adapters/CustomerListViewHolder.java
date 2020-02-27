package com.intercom.announcer.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intercom.announcer.R;

public class CustomerListViewHolder extends RecyclerView.ViewHolder {
    private TextView tvUserId;
    private TextView tvName;
    private TextView tvDistance;

    public CustomerListViewHolder(@NonNull View itemView) {
        super(itemView);

        tvUserId    = itemView.findViewById(R.id.tv_user_id);
        tvName      = itemView.findViewById(R.id.tv_name);
        tvDistance  = itemView.findViewById(R.id.tv_distance);
    }

    public TextView getTvUserId() {
        return tvUserId;
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvDistance() {
        return tvDistance;
    }

    public View getView() {
        return itemView;
    }
}
