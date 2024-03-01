package com.example.lispelDoc2.uiServices;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.MutableLiveData;

import com.example.lispelDoc2.R;
import com.example.lispelDoc2.models.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServicesListViewAdapter extends ArrayAdapter<Service> {
    ArrayList<Service> dataList;
    MutableLiveData<List<Service>> dataListLiveData;
    public ServicesListViewAdapter(@NonNull Context context, ArrayList<Service> dataList) {
        super(context, R.layout.recycler_view_item, dataList);
        this.dataListLiveData = new MutableLiveData<>();
        //this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Service data = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recycler_view_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.date_TextView);
        String result = data.getName() + " " + data.getComponentName();
        if (data.getName().contains("Заправка")) {
            result = result + " " + data.getAmount() + " гр";
        }
        textView.setText(result);

        TextView priceTextView = (TextView) convertView.findViewById(R.id.price_TextView);
        priceTextView.setText(data.getPrice() + " руб");
        priceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog editPriceDialog = new Dialog(parent.getContext());
                editPriceDialog.setContentView(R.layout.edit_price_dialog);
                editPriceDialog.show();
                EditText priceEditText = (EditText) editPriceDialog.findViewById(R.id.price_edittext);
                priceEditText.setText(getItem(position).getPrice().toString());
                priceEditText.requestFocus();
                editPriceDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


                AppCompatButton submitButton = (AppCompatButton) editPriceDialog.findViewById(R.id.submit_button);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataList = (ArrayList<Service>) dataListLiveData.getValue();
                        Service service = dataList.remove(position);
                        service.setPrice(Integer.parseInt(priceEditText.getText().toString()));
                        dataList.add(position, service);
                        dataListLiveData.postValue(dataList);
                        priceEditText.setText("");
                        editPriceDialog.cancel();
                    }
                });
            }
        });

        AppCompatImageButton imageButton = (AppCompatImageButton) convertView.findViewById(R.id.delete_ImageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList = (ArrayList<Service>) dataListLiveData.getValue();
                if (dataList != null && !dataList.isEmpty()){
                    dataList.remove(position);
                    dataListLiveData.postValue(dataList);
                    System.out.println("delete item");
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void innerUpdateDataList(MutableLiveData<List<Service>> dataListListener) {
        this.dataListLiveData = dataListListener;
    }


    private void showKeyboard(Context context, EditText input) {
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard(Context context, EditText input) {
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }


}
