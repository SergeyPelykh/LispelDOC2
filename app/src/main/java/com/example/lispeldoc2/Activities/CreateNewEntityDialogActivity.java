package com.example.lispeldoc2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lispeldoc2.R;
import com.example.lispeldoc2.interfaces.LispelAddValueByUser;
import com.example.lispeldoc2.interfaces.LispelCreateFieldObject;
import com.example.lispeldoc2.interfaces.Repository;
import com.example.lispeldoc2.models.Field;
import com.example.lispeldoc2.models.Order;
import com.example.lispeldoc2.models.PrintUnit;
import com.example.lispeldoc2.repository.PrintUnitRepository;
import com.example.lispeldoc2.uiServices.FieldSetViews;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewEntityDialogActivity extends AppCompatActivity {

    ArrayList<FieldSetViews> allFieldsOnLayout = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_entity_dialog);

        //keyboard not slide up all views
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        ArrayList<ListView> baseOptionListViews = new ArrayList<>();
        baseOptionListViews.add(findViewById(R.id.add_entity_field_ListView));
        baseOptionListViews.add(findViewById(R.id.add_entity_field2_ListView));
        baseOptionListViews.add(findViewById(R.id.add_entity_field3_ListView));
        baseOptionListViews.add(findViewById(R.id.add_entity_field4_ListView));
        baseOptionListViews.add(findViewById(R.id.add_entity_field5_ListView));
        baseOptionListViews.add(findViewById(R.id.add_entity_field6_ListView));

        ArrayList<ImageButton> addImageButtons = new ArrayList<>();
        addImageButtons.add(findViewById(R.id.add_entity_field_imageButton));
        addImageButtons.add(findViewById(R.id.add_entity_field2_imageButton));
        addImageButtons.add(findViewById(R.id.add_entity_field3_imageButton));
        addImageButtons.add(findViewById(R.id.add_entity_field4_imageButton));
        addImageButtons.add(findViewById(R.id.add_entity_field5_imageButton));
        addImageButtons.add(findViewById(R.id.add_entity_field6_imageButton));

        ArrayList<TextView> visionTextViews = new ArrayList<>();
        visionTextViews.add(findViewById(R.id.add_entity_field_textView));
        visionTextViews.add(findViewById(R.id.add_entity_field2_textView));
        visionTextViews.add(findViewById(R.id.add_entity_field3_textView));
        visionTextViews.add(findViewById(R.id.add_entity_field4_textView));
        visionTextViews.add(findViewById(R.id.add_entity_field5_textView));
        visionTextViews.add(findViewById(R.id.add_entity_field6_textView));

        ArrayList<TextView> inscriptionTextViews = new ArrayList<>();
        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field_TextView));
        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field2_TextView));
        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field3_TextView));
        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field4_TextView));
        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field5_TextView));
        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field6_TextView));

        ArrayList<EditText> inputEditTexts = new ArrayList<>();
        inputEditTexts.add(findViewById(R.id.add_entity_field_editText));
        inputEditTexts.add(findViewById(R.id.add_entity_field2_editText));
        inputEditTexts.add(findViewById(R.id.add_entity_field3_editText));
        inputEditTexts.add(findViewById(R.id.add_entity_field4_editText));
        inputEditTexts.add(findViewById(R.id.add_entity_field5_editText));
        inputEditTexts.add(findViewById(R.id.add_entity_field6_editText));

        for (TextView t : visionTextViews) {
            t.setVisibility(View.INVISIBLE);
        }
        for (TextView t : inscriptionTextViews) {
            t.setVisibility(View.INVISIBLE);
        }
        for (EditText t : inputEditTexts) {
            t.setVisibility(View.INVISIBLE);
        }
        for (ImageButton i : addImageButtons) {
            i.setVisibility(View.INVISIBLE);
        }

        int i = 0;
        PrintUnit printUnit = new PrintUnit();
        LispelAddValueByUser annotation;
        Map<Integer, Field> mapFields = new HashMap<>();
        java.lang.reflect.Field[] fields = Order.class.getDeclaredFields();
        for (java.lang.reflect.Field f : fields) {
            if (f.isAnnotationPresent(LispelAddValueByUser.class)) {
                annotation = (LispelAddValueByUser) f.getAnnotation(LispelAddValueByUser.class);
                Field field = new Field();
                field.setName(annotation.name());
                field.setHint(annotation.name_hint());
                field.setInscription(annotation.name_title());
                field.setInputType(annotation.input_type());

                Repository repository = null;
                try {
                    repository = (Repository) annotation.repository()
                            .getConstructor(Application.class)
                            .newInstance(getApplication());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                field.setDataSource(repository);

                mapFields.put(annotation.number(), field);
            }
        }
        for (int key :mapFields.keySet()) {
            inputFieldService(CreateNewEntityDialogActivity.this,
                    visionTextViews.get(i),
                    inscriptionTextViews.get(i),
                    inputEditTexts.get(i),
                    baseOptionListViews.get(i),
                    addImageButtons.get(i),
                    mapFields.get(key));
            i++;
        }


        ConstraintLayout mainLayout = findViewById(R.id.addEntityLinearLayout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allFieldsOnLayout.isEmpty()){
                    for (FieldSetViews f : allFieldsOnLayout) {
                        f.removeFocus(CreateNewEntityDialogActivity.this);
                    }
                }
            }
        });
    }




    @SuppressLint("WrongConstant")
    private void inputFieldService(Context context,
                                   TextView fieldTextView,
                                   TextView titleTextView,
                                   EditText inputEditText,
                                   ListView listView,
                                   ImageButton imageButton,
                                   Field field) {
        FieldSetViews fieldSetViews = new FieldSetViews(
                field.getInscription(),
                field.getName(),
                fieldTextView,
                inputEditText,
                titleTextView,
                listView,
                imageButton);
        allFieldsOnLayout.add(fieldSetViews);

        fieldTextView.setVisibility(field.getNameTextViewVisibility());
        fieldTextView.setHint(field.getHint());
        titleTextView.setVisibility(field.getInscriptionTextViewVisibility());
        titleTextView.setText(field.getInscription());
        inputEditText.setInputType(field.getInputType());

        Repository repository = field.getDataSource();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateNewEntityDialogActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());



        fieldTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (FieldSetViews f : allFieldsOnLayout) {
                    if (f != fieldSetViews){
                        f.removeFocus(CreateNewEntityDialogActivity.this);
                    }
                }

                fieldTextView.setVisibility(View.INVISIBLE);
                titleTextView.setVisibility(View.GONE);
                inputEditText.setVisibility(View.VISIBLE);


                listView.setAdapter(adapter);
                repository.getNameAllEntities(CreateNewEntityDialogActivity.this).observe(
                        CreateNewEntityDialogActivity.this, x -> {
                            adapter.addAll(x);

                            listView.setVisibility(View.VISIBLE);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    fieldTextView.setText(x.get(position));
                                    //inputEditText.setText("");
                                    hideKeyboard(context, inputEditText);
                                    listView.setVisibility(View.GONE);
                                    inputEditText.setVisibility(View.INVISIBLE);
                                    imageButton.setVisibility(View.INVISIBLE);
                                    titleTextView.setVisibility(View.VISIBLE);
                                    fieldTextView.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                );



                showKeyboard(context, inputEditText);
                inputEditText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                keyCode == KeyEvent.KEYCODE_ENTER) {
                            fieldTextView.setText(inputEditText.getText());
                            imageButton.setVisibility(View.INVISIBLE);
                            inputEditText.setVisibility(View.INVISIBLE);
                            //inputEditText.setText("");
                            listView.setVisibility(View.GONE);
                            fieldTextView.setVisibility(View.VISIBLE);
                            titleTextView.setVisibility(View.VISIBLE);
                            hideKeyboard(context, inputEditText);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (repository != null) {
                    repository.getNameAllEntitiesByProperty(CreateNewEntityDialogActivity.this, s.toString())
                            .observe(CreateNewEntityDialogActivity.this, x -> {
                                if (x.size() != 0){
                                    listView.setVisibility(View.VISIBLE);
                                    adapter.clear();
                                    adapter.addAll(x);

                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            fieldTextView.setText(x.get(position));
                                            //inputEditText.setText("");
                                            hideKeyboard(context, inputEditText);
                                            listView.setVisibility(View.GONE);
                                            inputEditText.setVisibility(View.INVISIBLE);
                                            imageButton.setVisibility(View.INVISIBLE);
                                            titleTextView.setVisibility(View.VISIBLE);
                                            fieldTextView.setVisibility(View.VISIBLE);
                                        }
                                    });

                                } else {
                                    adapter.clear();
                                    listView.setVisibility(View.GONE);
                                }

                            });
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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