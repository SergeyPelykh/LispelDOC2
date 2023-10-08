package com.example.lispeldoc2.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.lispeldoc2.R;
import com.example.lispeldoc2.interfaces.LispelAddValueByUser;
import com.example.lispeldoc2.interfaces.LispelCreateFieldObject;
import com.example.lispeldoc2.models.Field;
import com.example.lispeldoc2.models.PrintUnit;
import com.example.lispeldoc2.repository.FieldRepository;
import com.example.lispeldoc2.repository.PrintUnitRepository;

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
        //PrintUnitRepository printUnitRepository = new PrintUnitRepository(getApplication());
        FieldRepository fieldRepository = new FieldRepository(getApplication());
        AppCompatButton testButton = findViewById(R.id.testButton);
        AppCompatButton testButton2 = findViewById(R.id.testButton2);
        AppCompatButton testButton3 = findViewById(R.id.testButton3);
        AppCompatButton testButton4 = findViewById(R.id.testButton4);
        TextView mainTextView = findViewById(R.id.main_textview);
        mainTextView.setMovementMethod(new ScrollingMovementMethod());

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveData<List<Field>> liveData = fieldRepository.getAllEntities();
                liveData.observe(MainActivity.this, x -> {
                    if (x != null) {
                        for (Field f : x) {
                            addNewLineToTextView(mainTextView, f.getId() + " " + f.getName());
                        }
                        liveData.removeObservers(MainActivity.this);
                    }
                });
//                Field field = new Field();
//                field.setName("some name " + new Date().toString());
//                fieldRepository.insert(field).observe(MainActivity.this, x -> {
//                    addNewLineToTextView(mainTextView, "insert in field repository with id = " + x);
//                });
//
//                PrintUnit printUnit = new PrintUnit();
//                printUnit.setModel("HP85a");
//                printUnitRepository.insert(printUnit).observe(MainActivity.this, x -> {
//                    addNewLineToTextView(mainTextView, "insert in printUnit repository with id = " + x);
//                });
            }
        });

        testButton2.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                Object object = new Field();
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
                Object printUnit = new PrintUnit();
                displayField(printUnit, mainTextView);
            }
        });

        testButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewEntityDialogActivity.class);
                startForResult.launch(intent);
            }
        });

    }

    private void addNewLineToTextView(TextView textView, String text) {
        {
        }
        textView.setText(textView.getText() + "\n" + text);
    }

    private void displayField(Object object, TextView textView) {
        java.lang.reflect.Field[] fields = object.getClass().getDeclaredFields();
        addNewLineToTextView(textView, object.getClass().getSimpleName());
        FieldRepository fieldRepository = new FieldRepository(getApplication());
        for (java.lang.reflect.Field f : fields) {
            if (f.isAnnotationPresent(LispelAddValueByUser.class)) {
                LispelAddValueByUser annotation = (LispelAddValueByUser) f.getAnnotation(LispelAddValueByUser.class);
                Field field = new Field();
                field.setName(annotation.name());
                field.setHint(annotation.name_hint());
                field.setInscription(annotation.name_title());
                field.setInputType(annotation.input_type());
                fieldRepository.insert(field);
            }
        }
//        if (object.getClass().isAnnotationPresent(CreateFieldObject.class)){
//            CreateFieldObject annotation = (CreateFieldObject)object.getClass().getAnnotation(CreateFieldObject.class);
//            addNewLineToTextView(textView, annotation.name()+ "");
//        }
    }
}