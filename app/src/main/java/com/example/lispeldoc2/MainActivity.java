package com.example.lispeldoc2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lispeldoc2.models.Field;
import com.example.lispeldoc2.models.PrintUnit;
import com.example.lispeldoc2.repository.FieldRepository;
import com.example.lispeldoc2.repository.PrintUnitRepository;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrintUnitRepository printUnitRepository = new PrintUnitRepository(getApplication());
        FieldRepository fieldRepository = new FieldRepository(getApplication());
        AppCompatButton testButton = findViewById(R.id.testButton);
        AppCompatButton testButton2 = findViewById(R.id.testButton2);
        TextView mainTextView = findViewById(R.id.main_textview);
        mainTextView.setMovementMethod(new ScrollingMovementMethod());

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Field field = new Field();
                field.setName("some name " + new Date().toString());
                fieldRepository.insert(field).observe(MainActivity.this, x -> {
                    addNewLineToTextView(mainTextView, "insert in field repository with id = " + x);
                });
            }
        });

        testButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveData<List<PrintUnit>> liveData = printUnitRepository.getAllEntities();
                liveData.observe(MainActivity.this, x -> {
                    if (x != null) {
                        addNewLineToTextView(mainTextView, x.size() + " entities in base ");
                        liveData.removeObservers(MainActivity.this);
                    }
                });
            }
        });

    }
    private void addNewLineToTextView(TextView textView, String text){
        textView.setText(textView.getText() + "\n" + text);
    }
}