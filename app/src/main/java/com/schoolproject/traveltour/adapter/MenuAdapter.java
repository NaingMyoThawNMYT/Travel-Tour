package com.schoolproject.traveltour.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Menu;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private Context mContext;
    private List<Menu> dataSet;

    public MenuAdapter(Context mContext, List<Menu> dataSet) {
        this.mContext = mContext;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu menu = dataSet.get(holder.getAdapterPosition());

        holder.view.setBackgroundResource(mContext.getResources().getIdentifier(menu.getImageUrl(), "raw", mContext.getPackageName()));
        holder.title.setText(menu.getTitle());

        if (!TextUtils.isEmpty(menu.getDescription())) {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(menu.getDescription());
        } else {
            holder.description.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView title;
        private TextView description;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.background);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }
}
