package com.example.lispeldoc2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lispeldoc2.R;
import com.example.lispeldoc2.interfaces.LispelAddValueByUser;
import com.example.lispeldoc2.models.Field;
import com.example.lispeldoc2.models.PrintUnit;
import com.example.lispeldoc2.uiServices.FieldSetViews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateNewEntityDialogActivity extends AppCompatActivity {

    ArrayList<FieldSetViews> allFieldsOnLayout = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_entity_dialog);

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
        java.lang.reflect.Field[] fields = PrintUnit.class.getDeclaredFields();
        for (java.lang.reflect.Field f : fields) {
            if (f.isAnnotationPresent(LispelAddValueByUser.class)) {
                annotation = (LispelAddValueByUser) f.getAnnotation(LispelAddValueByUser.class);
                Field field = new Field();
                field.setName(annotation.name());
                field.setHint(annotation.name_hint());
                field.setInscription(annotation.name_title());
                field.setInputType(annotation.input_type());
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
        fieldTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldTextView.setVisibility(View.INVISIBLE);
                titleTextView.setVisibility(View.GONE);
                inputEditText.setVisibility(View.VISIBLE);

                for (FieldSetViews f : allFieldsOnLayout) {
                    if (f != fieldSetViews){
                        f.removeFocus(CreateNewEntityDialogActivity.this);
                    }
                }


                showKeyboard(context, inputEditText);
                inputEditText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                keyCode == KeyEvent.KEYCODE_ENTER) {
//                            if (repositoryService == null || fieldName.equals("address")) {
//                                field.setText(input.getText());
//                            }
                            fieldTextView.setText(inputEditText.getText());
                            imageButton.setVisibility(View.INVISIBLE);
                            inputEditText.setVisibility(View.INVISIBLE);
                            inputEditText.setText("");
                            listView.setVisibility(View.GONE);
                            fieldTextView.setVisibility(View.VISIBLE);
                            titleTextView.setVisibility(View.VISIBLE);
                            //insertInGlobalFieldsMap(fieldObject,field);
                            hideKeyboard(context, inputEditText);
                            return true;
                        }
                        return false;
                    }
                });
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