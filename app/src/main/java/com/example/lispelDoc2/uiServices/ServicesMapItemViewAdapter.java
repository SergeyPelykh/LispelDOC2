package com.example.lispelDoc2.uiServices;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispelDoc2.Activities.NewOrderActivity;
import com.example.lispelDoc2.R;
import com.example.lispelDoc2.models.Service;
import com.example.lispelDoc2.utilities.MapServicesToLIst;
import com.example.lispelDoc2.utilities.ServicesMapItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicesMapItemViewAdapter extends ArrayAdapter<ServicesMapItem> {
    private Context context;
    private MutableLiveData<List<ServicesMapItem>> innerListLiveData = new MutableLiveData<>();

    public ServicesMapItemViewAdapter(@NonNull Context context, ArrayList<ServicesMapItem> dataList) {
        super(context, R.layout.recycler_view_item, dataList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ServicesMapItem data = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recycler_map_service_view_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.printUnit_number_TextView);



        MutableLiveData<List<Service>> printUnitServicesLiveData = new MutableLiveData<>();
        printUnitServicesLiveData.postValue(data.getServicesList());

        ListView servicesListView = convertView.findViewById(R.id.printUnit_services_ListView);
        ServicesListViewAdapter adapter = new ServicesListViewAdapter(context, new ArrayList<>());
        servicesListView.setAdapter(adapter);
        adapter.clear();
        adapter.addAll(data.getServicesList());
        adapter.innerUpdateDataList(printUnitServicesLiveData);

        printUnitServicesLiveData.observe((LifecycleOwner) context, gotListServices -> {
            adapter.clear();
            if (!gotListServices.isEmpty()) {
                textView.setText(data.getSticker());
                adapter.addAll(gotListServices);
            } else {
                List<ServicesMapItem> tempListLiveData = innerListLiveData.getValue();
                tempListLiveData.remove(data);
                innerListLiveData.postValue(tempListLiveData);
            }
            ResizeListView.changeHeightListView(servicesListView);
        });


        return convertView;
    }


    public MutableLiveData<List<ServicesMapItem>> getInnerListLiveData() {
        return innerListLiveData;
    }

    public void setInnerListLiveData(MutableLiveData<List<ServicesMapItem>> innerListLiveData) {
        this.innerListLiveData = innerListLiveData;
    }
}
