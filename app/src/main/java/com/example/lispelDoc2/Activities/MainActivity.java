package com.example.lispelDoc2.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.room.Database;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.lispelDoc2.R;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateFieldObject;
import com.example.lispelDoc2.models.Field;
import com.example.lispelDoc2.models.PrintUnit;
import com.example.lispelDoc2.models.StringValue;
import com.example.lispelDoc2.repository.PrintUnitRepository;
import com.example.lispelDoc2.repository.StringValueRepository;
import com.example.lispelDoc2.utilities.Convert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainActivity extends AppCompatActivity {


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
    private int anInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        PrintUnitRepository printUnitRepository = new PrintUnitRepository(getApplication());
        //FieldRepository fieldRepository = new FieldRepository(getApplication());
        AppCompatButton testButton = findViewById(R.id.testButton);
        AppCompatButton testButton2 = findViewById(R.id.testButton2);
        AppCompatButton testButton3 = findViewById(R.id.testButton3);
        AppCompatButton testButton4 = findViewById(R.id.testButton4);
        AppCompatButton testButton5 = findViewById(R.id.testButton5);
        TextView mainTextView = findViewById(R.id.main_textview);
        mainTextView.setMovementMethod(new ScrollingMovementMethod());

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PrintUnit printUnit;
//                for (int i = 0; i < 10; i++){
//                    printUnit = new PrintUnit();
//                    printUnit.setModel(i + "a");
//                    printUnit.setVendor("Hp");
//                    printUnitRepository.insert(printUnit);
//                }
//                printUnitRepository.getNameAllEntities(MainActivity.this).observe(MainActivity.this, x -> {
//                    for (String p : x) {
//                        addNewLineToTextView(mainTextView, p);
//                    }
//                });
                StringValueRepository stringValueRepository = new StringValueRepository(getApplication(),
                        "street");
                stringValueRepository.getAllEntities().observe(MainActivity.this, x -> {
                    if (x.size() == 0) {
                        String line = "";
                        try (
                                BufferedReader bufferedReader = new BufferedReader(
                                        new InputStreamReader(getAssets().open("streets.txt")))) {
                            while ((line = bufferedReader.readLine()) != null) {
                                stringValueRepository.insert(new StringValue("street", line));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        addNewLineToTextView(mainTextView, "in stringValueRepository inserted "
                                + x.size() + " entities");
                    }
                });
                StringValueRepository stringValueRepository1 = new StringValueRepository(getApplication(), "clientType");
                stringValueRepository1.getAllEntities().observe(MainActivity.this, x -> {
                    if (x.size() == 0) {
                        String line = "";
                        try (
                                BufferedReader bufferedReader = new BufferedReader(
                                        new InputStreamReader(getAssets().open("typeOfClient.txt")))) {
                            while ((line = bufferedReader.readLine()) != null) {
                                stringValueRepository1.insert(new StringValue("clientType", line));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        addNewLineToTextView(mainTextView, "in stringValueRepository inserted "
                                + x.size() + " entities");
                    }
                });


            }
        });

        testButton2.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                Object object = new PrintUnit();
                if (object.getClass().isAnnotationPresent(LispelCreateFieldObject.class)) {
                    LispelCreateFieldObject annotation = (LispelCreateFieldObject) object
                            .getClass()
                            .getAnnotation(LispelCreateFieldObject.class);
                    try {
                        assert annotation != null;
                        PrintUnitRepository printUnitRepository = (PrintUnitRepository) annotation
                                .repository_class()
                                .getConstructor(Application.class)
                                .newInstance(getApplication());
                        LiveData<List<PrintUnit>> liveData = printUnitRepository.getAllEntities();
                        liveData.observe(MainActivity.this, x -> {
                            if (x != null) {
                                for (PrintUnit s : x) {
                                    addNewLineToTextView(mainTextView, s.getId().toString());
                                }
                                addNewLineToTextView(mainTextView, x.size() + " entities in base ");
                                liveData.removeObservers(MainActivity.this);
                            }
                        });
                    } catch (IllegalAccessException |
                            NoSuchMethodException |
                            InstantiationException |
                            InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    addNewLineToTextView(mainTextView, "entity`s class not annotated");
                }
            }
        });

        testButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewOrderActivity.class);
                intent.putExtra("nameEntityClass", "com.example.lispelDoc2.models.Order");
                startForResult.launch(intent);
            }
        });

        testButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewEntityDialogActivity.class);
                intent.putExtra("nameEntityClass", "com.example.lispelDoc2.models.Order");
                startForResult.launch(intent);
            }
        });

        testButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewOrderActivity.class);
                intent.putExtra("nameEntityClass", "com.example.lispelDoc2.models.Order");
                startForResult.launch(intent);
            }
        });

    }

    private void addNewLineToTextView(TextView textView, String text) {
        textView.setText(textView.getText() + "\n" + text);
    }

    private void displayField(Object object, TextView textView) {
        java.lang.reflect.Field[] fields = object.getClass().getDeclaredFields();
        addNewLineToTextView(textView, object.getClass().getSimpleName());
        //FieldRepository fieldRepository = new FieldRepository(getApplication());
        for (java.lang.reflect.Field f : fields) {
            if (f.isAnnotationPresent(LispelAddValueByUser.class)) {
                LispelAddValueByUser annotation = (LispelAddValueByUser) f.getAnnotation(LispelAddValueByUser.class);
                Field field = new Field();
                field.setName(annotation.name());
                field.setHint(annotation.name_hint());
                field.setInscription(annotation.name_title());
                field.setInputType(annotation.input_type());
                //fieldRepository.insert(field);
            }
        }
//        if (object.getClass().isAnnotationPresent(CreateFieldObject.class)){
//            CreateFieldObject annotation = (CreateFieldObject)object.getClass().getAnnotation(CreateFieldObject.class);
//            addNewLineToTextView(textView, annotation.name()+ "");
//        }
    }
}