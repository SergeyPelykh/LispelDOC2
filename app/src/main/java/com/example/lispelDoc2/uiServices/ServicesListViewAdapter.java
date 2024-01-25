package com.example.lispelDoc2.uiServices;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.MutableLiveData;

import com.example.lispelDoc2.R;
import com.example.lispelDoc2.models.Service;

import java.util.ArrayList;
import java.util.List;

public class ServicesListViewAdapter extends ArrayAdapter<String> {
    ArrayList<Service> dataList;
    MutableLiveData<List<Service>> dataListLiveData;
    public ServicesListViewAdapter(@NonNull Context context, ArrayList<String> dataList) {
        super(context, R.layout.recycler_view_item, dataList);
        //this.dataList = dataList;
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

        AppCompatImageButton imageButton = (AppCompatImageButton) convertView.findViewById(R.id.delete_ImageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList = (ArrayList<Service>) dataListLiveData.getValue();
                dataList.remove(position);
                dataListLiveData.postValue(dataList);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void innerUpdateDataList(MutableLiveData<List<Service>> dataListListener) {
        this.dataListLiveData = dataListListener;
    }


}
