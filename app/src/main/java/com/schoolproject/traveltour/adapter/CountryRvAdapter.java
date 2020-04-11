package com.schoolproject.traveltour.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Country;
import com.schoolproject.traveltour.utils.BitmapUtil;

import java.util.List;

public class CountryRvAdapter extends RecyclerView.Adapter<CountryRvAdapter.ViewHolder> {
    private List<Country> dataSet;
    private onListItemClickListener onListItemClickListener;

    public CountryRvAdapter(onListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = dataSet.get(holder.getAdapterPosition());

        holder.imageView.setImageBitmap(BitmapUtil.base64StringToBitmap(country.getImageBase64()));
        holder.title.setText(country.getName());
    }

    public void setDataSet(List<Country> dataSet) {
        this.dataSet = dataSet;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_background);
            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListItemClickListener.onClick(dataSet.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onListItemClickListener.onLongClick(dataSet.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }

    public interface onListItemClickListener {
        void onClick(Country country);

        void onLongClick(Country country);
    }
}
