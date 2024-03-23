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
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.lispelDoc2.R;
import com.example.lispelDoc2.dao.OrderDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.models.Order;
import com.example.lispelDoc2.uiServices.OrderAdapter;
import com.example.lispelDoc2.uiServices.ServicesListViewAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

//        orderDAO.getAllEntities().observe(ListOfOrdersActivity.this, got -> {
//            got.stream().forEach(g -> System.out.println(g.getFinalPrice()));
//        });
        //System.out.println(orderDAO.getAllEntities().getValue()+ "++++");
        LiveData<List<Order>> orderMutableLiveData = orderDAO.getAllEntitiesOrderByDesc();
        orderMutableLiveData.observe(ListOfOrdersActivity.this, gotList -> {
            if (!gotList.isEmpty()) {
//                ObjectMapper objectMapper = new ObjectMapper();

//                try {
//                    File path = Environment.getExternalStoragePublicDirectory(
//                            Environment.DIRECTORY_DOCUMENTS);
//                    String name = new SimpleDateFormat("HH_mm_dd_MM_yyyy").format(new Date()) + ".txt";
//                    File file = new File(path, name);
////                    FileOutputStream fileOutputStream = new FileOutputStream(file);
////                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
////
////                    bufferedWriter.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(gotList));
////
////                    bufferedWriter.close();
////                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
////                    outputStreamWriter.flush();
////                    outputStreamWriter.close();
////                    Toast.makeText(this, "save in file " + name, Toast.LENGTH_SHORT).show();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//
//
//                try {
//                    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(gotList));
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
                RecyclerView ordersRecyclerView = findViewById(R.id.orders_RecyclerView);
                OrderAdapter recyclerAdapter = new OrderAdapter(this, gotList);
                ordersRecyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}