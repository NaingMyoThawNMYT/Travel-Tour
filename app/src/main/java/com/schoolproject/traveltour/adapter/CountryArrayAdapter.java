package com.schoolproject.traveltour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.schoolproject.traveltour.model.Country;

import java.util.List;

public class CountryArrayAdapter extends ArrayAdapter<Country> {
    private List<Country> dataSet;

    public CountryArrayAdapter(@NonNull Context context, List<Country> dataSet) {
        super(context, 0, dataSet);
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Country country = dataSet.get(position);
        TextView tv = listItem.findViewById(android.R.id.text1);
        tv.setText(country.getName());

        return listItem;
    }
}
