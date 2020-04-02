package com.schoolproject.traveltour.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private Context mContext;
    private List<Menu> dataSet;
    private MenuClickListener menuClickListener;

    public MenuAdapter(Context mContext, MenuClickListener menuClickListener) {
        this(mContext, new ArrayList<Menu>(), menuClickListener);
    }

    public MenuAdapter(Context mContext, List<Menu> dataSet, MenuClickListener menuClickListener) {
        this.mContext = mContext;
        this.dataSet = dataSet;
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
        Menu menu = dataSet.get(holder.getAdapterPosition());

        if (!TextUtils.isEmpty(menu.getBase64ImageStr())) {
            holder.imageView.setImageBitmap(BitmapUtil.base64StringToBitmap(menu.getBase64ImageStr()));
        } else if (!TextUtils.isEmpty(menu.getImageUrl())) {
            holder.imageView.setImageResource(mContext.getResources().getIdentifier(menu.getImageUrl(),
                    "raw",
                    mContext.getPackageName()));
        }
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
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
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
                    menuClickListener.onClick(dataSet.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuClickListener.onLongClick(dataSet.get(getAdapterPosition()));
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
