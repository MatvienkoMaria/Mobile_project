package com.mobile.project.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    List<String> listOfGroups;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.listOfGroups = objects;
    }
}
