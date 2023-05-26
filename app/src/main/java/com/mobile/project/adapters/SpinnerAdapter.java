package com.mobile.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mobile.project.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    List<String> namesOfGroups;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
        this.namesOfGroups = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.spinner_dropdown, parent, false);
        TextView textView = view.findViewById(R.id.spinnerDropdownText);
        textView.setText(namesOfGroups.get(position));
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.spinner_header, parent, false);
        TextView textView = view.findViewById(R.id.spinnerHeaderText);
        textView.setText(namesOfGroups.get(position));
        return view;
    }
}
