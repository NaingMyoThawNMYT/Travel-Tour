package com.schoolproject.traveltour.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> implements Filterable {
    private List<Menu> dataSet;
    private List<Menu> filteredDataSet;
    private MenuClickListener menuClickListener;

    public MenuAdapter(MenuClickListener menuClickListener) {
        this(new ArrayList<Menu>(), menuClickListener);
    }

    public MenuAdapter(List<Menu> dataSet, MenuClickListener menuClickListener) {
        this.dataSet = dataSet;
        this.filteredDataSet = dataSet;
        this.menuClickListener = menuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu menu = filteredDataSet.get(holder.getAdapterPosition());

        holder.imageView.setImageBitmap(BitmapUtil.base64StringToBitmap(menu.getBase64ImageStr()));
        holder.title.setText(menu.getTitle());

        if (!TextUtils.isEmpty(menu.getDescription())) {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(menu.getDescription());
        } else {
            holder.description.setVisibility(View.GONE);
        }
    }

    public void setDataSet(List<Menu> dataSet) {
        this.dataSet = dataSet;
        this.filteredDataSet = dataSet;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filteredDataSet == null ? 0 : filteredDataSet.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchTerm = charSequence.toString().trim().toLowerCase();

                final List<Menu> filteredList = new ArrayList<>();
                if (searchTerm.isEmpty()) {
                    filteredList.addAll(new ArrayList<>(dataSet));
                } else {
                    for (Menu tour : dataSet) {
                        if (tour.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                            filteredList.add(tour);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredDataSet = (List<Menu>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView description;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_background);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuClickListener.onClick(filteredDataSet.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuClickListener.onLongClick(filteredDataSet.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }

    public interface MenuClickListener {
        void onClick(Menu menu);

        void onLongClick(Menu menu);
    }
}
