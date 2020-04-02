package com.schoolproject.traveltour.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Booking;
import com.schoolproject.traveltour.utils.DateUtil;

import java.util.Date;
import java.util.List;

public class InvoicedAdapter extends RecyclerView.Adapter<InvoicedAdapter.ViewHolder> {
    private final List<Booking> dataSet;
    private final OnClickListener onClickListener;

    public InvoicedAdapter(List<Booking> dataSet, OnClickListener onClickListener) {
        this.dataSet = dataSet;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoiced_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Booking booking = dataSet.get(holder.getAdapterPosition());
        holder.tvDate.setText(DateUtil.stdDateFormat(new Date(Long.parseLong(booking.getBookingDate()))));
        holder.tvUsername.setText(booking.getUsername());
        holder.tvPackageName.setText(booking.getPackageName());
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvUsername, tvPackageName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            tvUsername = itemView.findViewById(R.id.tv_user_name);
            tvPackageName = itemView.findViewById(R.id.tv_package_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(dataSet.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickListener.onLongClick(dataSet.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }

    public interface OnClickListener {
        void onClick(Booking booking);

        void onLongClick(Booking booking);
    }
}
