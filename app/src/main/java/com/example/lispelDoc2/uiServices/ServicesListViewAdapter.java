package com.example.lispelDoc2.uiServices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lispelDoc2.R;

import java.util.ArrayList;

public class ServicesListViewAdapter extends ArrayAdapter<String> {
    public ServicesListViewAdapter(@NonNull Context context, ArrayList<String> dataList) {
        super(context, R.layout.recycler_view_item, dataList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String data = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recycler_view_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.date_TextView);
        textView.setText(data);
        return convertView;
    }
}
