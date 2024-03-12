package com.example.lispelDoc2.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.lispelDoc2.R;
import com.example.lispelDoc2.dao.OrderDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.models.Order;
import com.example.lispelDoc2.uiServices.OrderAdapter;
import com.example.lispelDoc2.uiServices.ServicesListViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListOfOrdersActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                    }
                }
            }
    );

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_orders);


        AppCompatButton createNewOrderButton = findViewById(R.id.create_new_order_button);
        createNewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfOrdersActivity.this, NewOrderActivity.class);
                intent.putExtra("nameEntityClass", "com.example.lispelDoc2.models.Order");
                startForResult.launch(intent);
            }
        });


        ListView ordersListView = findViewById(R.id.orders_ListView);
        ArrayAdapter adapter = new ArrayAdapter(ListOfOrdersActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());
        ordersListView.setAdapter(adapter);

        OrderDAO orderDAO = LispelRoomDatabase.getDatabase(ListOfOrdersActivity.this).orderDAO();
        LiveData<List<Order>> orderMutableLiveData = orderDAO.getAllEntities();
        orderMutableLiveData.observe(ListOfOrdersActivity.this, gotList -> {
            if (!gotList.isEmpty()) {
//                adapter.clear();
//                List<String> listOfOrders = gotList.stream().map(order -> order.getId() + "").collect(Collectors.toList());
//                adapter.addAll(listOfOrders);
                RecyclerView ordersRecyclerView = findViewById(R.id.orders_RecyclerView);
                OrderAdapter recyclerAdapter = new OrderAdapter(this, gotList);
                ordersRecyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}