package com.example.lispelDoc2.uiServices;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class FieldSetViews {
    String inscriptionText;
    String fieldName;
    TextView name;
    EditText editText;
    TextView inscription;
    ListView listView;
    ImageButton imageButton;

    public String getFieldName() {
        return fieldName;
    }

    public TextView getName() {
        return name;
    }

    public FieldSetViews(String inscriptionText, String fieldName, TextView name, EditText editText, TextView inscription, ListView listView, ImageButton imageButton) {
        this.inscriptionText = inscriptionText;
        this.fieldName = fieldName;
        this.name = name;
        this.editText = editText;
        this.inscription = inscription;
        this.listView = listView;
        this.imageButton = imageButton;
    }
    public void removeFocus(Activity activity){
        if (name.getVisibility() == View.INVISIBLE) {
            name.setVisibility(View.VISIBLE);
            name.setText(editText.getText());
            editText.setVisibility(View.INVISIBLE);
            inscription.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            imageButton.setVisibility(View.INVISIBLE);
            hideKeyboard(activity, editText);
        }
    }

    private void hideKeyboard(Context context, EditText input) {
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }
}
