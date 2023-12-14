


package com.example.lispelDoc2.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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
import android.widget.Toast;

import com.example.lispelDoc2.R;
import com.example.lispelDoc2.dao.ClientDAO;
import com.example.lispelDoc2.dao.OrderUnitDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.Repository;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.Client;
import com.example.lispelDoc2.models.Field;
import com.example.lispelDoc2.models.OrderUnit;
import com.example.lispelDoc2.uiServices.FieldSetViews;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class NewOrderActivity extends AppCompatActivity {

    ArrayList<FieldSetViews> allFieldsOnLayout = new ArrayList<>();
    MutableLiveData<String> clientLiveData = new MutableLiveData<>();
    MutableLiveData<String> orderUnitLiveData = new MutableLiveData<>();
    ArrayList<String> clientOrderUnits = new ArrayList<>();
    Client client = null;
    OrderUnit orderUnit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        //keyboard not slide up all views
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        AppCompatButton cancelButton = findViewById(R.id.cancel_add_entity_in_base_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ArrayList<ListView> baseOptionListViews = new ArrayList<>();
        baseOptionListViews.add(findViewById(R.id.add_entity_field_ListView));
//        baseOptionListViews.add(findViewById(R.id.add_entity_field2_ListView));
//        baseOptionListViews.add(findViewById(R.id.add_entity_field3_ListView));
//        baseOptionListViews.add(findViewById(R.id.add_entity_field4_ListView));
//        baseOptionListViews.add(findViewById(R.id.add_entity_field5_ListView));
//        baseOptionListViews.add(findViewById(R.id.add_entity_field6_ListView));

        ArrayList<ImageButton> addImageButtons = new ArrayList<>();
        addImageButtons.add(findViewById(R.id.add_entity_field_imageButton));
//        addImageButtons.add(findViewById(R.id.add_entity_field2_imageButton));
//        addImageButtons.add(findViewById(R.id.add_entity_field3_imageButton));
//        addImageButtons.add(findViewById(R.id.add_entity_field4_imageButton));
//        addImageButtons.add(findViewById(R.id.add_entity_field5_imageButton));
//        addImageButtons.add(findViewById(R.id.add_entity_field6_imageButton));

        ArrayList<TextView> visionTextViews = new ArrayList<>();
        visionTextViews.add(findViewById(R.id.add_entity_field_textView));
//        visionTextViews.add(findViewById(R.id.add_entity_field2_textView));
//        visionTextViews.add(findViewById(R.id.add_entity_field3_textView));
//        visionTextViews.add(findViewById(R.id.add_entity_field4_textView));
//        visionTextViews.add(findViewById(R.id.add_entity_field5_textView));
//        visionTextViews.add(findViewById(R.id.add_entity_field6_textView));

        ArrayList<TextView> inscriptionTextViews = new ArrayList<>();
        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field_TextView));
//        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field2_TextView));
//        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field3_TextView));
//        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field4_TextView));
//        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field5_TextView));
//        inscriptionTextViews.add(findViewById(R.id.inscription_add_entity_field6_TextView));

        ArrayList<EditText> inputEditTexts = new ArrayList<>();
        inputEditTexts.add(findViewById(R.id.add_entity_field_editText));
//        inputEditTexts.add(findViewById(R.id.add_entity_field2_editText));
//        inputEditTexts.add(findViewById(R.id.add_entity_field3_editText));
//        inputEditTexts.add(findViewById(R.id.add_entity_field4_editText));
//        inputEditTexts.add(findViewById(R.id.add_entity_field5_editText));
//        inputEditTexts.add(findViewById(R.id.add_entity_field6_editText));

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


        ClientDAO clientDAO = LispelRoomDatabase.getDatabase(getApplicationContext()).clientDAO();
        OrderUnitDAO orderUnitDAO = LispelRoomDatabase.getDatabase(getApplicationContext()).orderUnitDAO();

        clientLiveData.observe(NewOrderActivity.this, insertedValue -> {
            clientDAO.getEntityByName(insertedValue).observe(NewOrderActivity.this, foundedObject -> {
                if (foundedObject != null) {
                        client = foundedObject;
                        visionTextViews.get(0).setText(client.getName());
                        clientOrderUnits.clear();
                }
            });

        });


        Field field = new Field();
        field.setName("Заказчик");
        field.setHint("Заказчик");
        field.setInscription("Заказчик");
        field.setInputType(1);
        field.setWriteInBase(true);
        field.setNameEntityClass("com.example.lispelDoc2.models.Client");
        field.setDataSource(createRepository("com.example.lispelDoc2.models.Client"));

        inputFieldService(NewOrderActivity.this,
                visionTextViews.get(0),
                inscriptionTextViews.get(0),
                inputEditTexts.get(0),
                baseOptionListViews.get(0),
                addImageButtons.get(0),
                field,
                clientLiveData,
                NewOrderActivity.this,
                allFieldsOnLayout);






    }





    @SuppressLint("WrongConstant")
    private void inputFieldService(Context context,
                                   TextView fieldTextView,
                                   TextView titleTextView,
                                   EditText inputEditText,
                                   ListView listView,
                                   ImageButton imageButton,
                                   Field field,
                                   MutableLiveData<String> resultLiveData,
                                   LifecycleOwner thisActivity,
                                   ArrayList<FieldSetViews> allFieldsOnLayout
                                   ) {
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(NewOrderActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData().getStringExtra(field.getName()) != null) {
                            Repository resultRepository;
                            if (!field.getLinkedValueName().equals("")) {
                                resultRepository = createRepository(field.getNameEntityClass(), field.getLinkedValueName());
                            } else {
                                resultRepository = createRepository(field.getNameEntityClass());
                            }
                            if (!(resultRepository == null)) {
                                resultRepository.getEntityById(thisActivity, Long.parseLong(result.getData().getStringExtra(field.getName())))
                                        .observe(thisActivity, x -> {
                                            fieldTextView.setText(x.getDescription());
                                            resultLiveData.postValue(x.getDescription());
                                            hideKeyboard(context, inputEditText);
                                            inputEditText.setVisibility(View.INVISIBLE);
                                            imageButton.setVisibility(View.INVISIBLE);
                                            adapter.clear();
                                            listView.setVisibility(View.GONE);
                                            titleTextView.setVisibility(View.VISIBLE);
                                            fieldTextView.setVisibility(View.VISIBLE);
                                        });
                            } else {
                                Toast.makeText(getApplicationContext(), "database not created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );


        View.OnClickListener imageButtonOnClickListener = v -> {
            Intent intent = new Intent(context, CreateNewEntityDialogActivity.class);
            String nameEntityClass = field.getNameEntityClass();
            if (!nameEntityClass.equals("")) {
                intent.putExtra("nameEntityClass", nameEntityClass);
                intent.putExtra("createEntity", field.getName());
                intent.putExtra("repositoryTitle", field.getLinkedValueName());
            }
            startForResult.launch(intent);
        };


        fieldTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (FieldSetViews f : allFieldsOnLayout) {
                    if (f != fieldSetViews) {
                        f.removeFocus(NewOrderActivity.this);
                    }
                }
                inputEditText.setText(fieldTextView.getText());
                fieldTextView.setVisibility(View.INVISIBLE);
                titleTextView.setVisibility(View.GONE);
                inputEditText.setVisibility(View.VISIBLE);
                inputEditText.setSelection(inputEditText.getText().length());


                listView.setAdapter(adapter);

                if (repository != null) {
                    LiveData<List<String>> allEntity = repository.getNameAllEntitiesByProperty(thisActivity,
                            fieldTextView.getText().toString());
                    allEntity.observe(thisActivity, x -> {
                                allEntity.removeObservers(NewOrderActivity.this);
                                adapter.clear();
                                adapter.addAll(x);
                                listView.setVisibility(View.VISIBLE);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if (field.isFromBaseAndEdit()) {
                                            inputEditText.setText(x.get(position) + " ");
                                            inputEditText.setSelection(inputEditText.getText().length());
                                        } else {
                                            hideKeyboard(context, inputEditText);
                                            inputEditText.setVisibility(View.INVISIBLE);
                                            imageButton.setVisibility(View.INVISIBLE);
                                            titleTextView.setVisibility(View.VISIBLE);
                                            fieldTextView.setVisibility(View.VISIBLE);
                                        }
                                        listView.setVisibility(View.GONE);
                                        resultLiveData.postValue(x.get(position));
                                    }
                                });

                            }
                    );
                    if (field.isWriteInBase()) {
                        imageButton.setVisibility(View.VISIBLE);
                    }
                }


                showKeyboard(context, inputEditText);
                inputEditText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                keyCode == KeyEvent.KEYCODE_ENTER) {
                            if (repository == null) {
                                fieldTextView.setText(inputEditText.getText());
                            }
                            if (field.isFromBaseAndEdit()) {
                                fieldTextView.setText(inputEditText.getText());
                            }
                            if (inputEditText.getText().length() == 0) {
                                fieldTextView.setText("");
                                //clientLiveData.postValue("");
                            }
                            imageButton.setVisibility(View.INVISIBLE);
                            inputEditText.setVisibility(View.INVISIBLE);
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


        if (field.isWriteInBase()) {
            imageButton.setOnClickListener(imageButtonOnClickListener);
        }


        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (repository != null) {
                    LiveData<List<String>> allEntity = repository.getNameAllEntitiesByProperty(thisActivity, s.toString());
                    allEntity.observe(thisActivity, x -> {
                        if (x.size() != 0) {
                            listView.setVisibility(View.VISIBLE);
                            adapter.clear();
                            adapter.addAll(x);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (field.isFromBaseAndEdit()) {
                                        inputEditText.setText(x.get(position) + " ");
                                        inputEditText.setSelection(inputEditText.getText().length());
                                    } else {
                                        fieldTextView.setText(x.get(position));
                                        hideKeyboard(context, inputEditText);
                                        inputEditText.setVisibility(View.INVISIBLE);
                                        imageButton.setVisibility(View.INVISIBLE);
                                        titleTextView.setVisibility(View.VISIBLE);
                                        fieldTextView.setVisibility(View.VISIBLE);
                                    }
                                    listView.setVisibility(View.GONE);
                                    resultLiveData.postValue(x.get(position));
                                }
                            });
                        } else {
                            adapter.clear();
                            listView.setVisibility(View.GONE);
                        }
                        allEntity.removeObservers(NewOrderActivity.this);
                        if (inputEditText.getText().length() > 0) {
                            imageButton.setBackground(getDrawable(R.drawable.ic_baseline_close_24));
                            imageButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    inputEditText.setText("");
                                    if (field.isWriteInBase()) {
                                        imageButton.setBackground(getDrawable(R.drawable.ic_baseline_add_24));
                                        imageButton.setOnClickListener(imageButtonOnClickListener);
                                    }
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private Repository createRepository(String... args) {
        String entityName = args[0];
        String titleForStringValueDAO = "";
        if (args.length > 1) {
            titleForStringValueDAO = args[1];
        }
        try {
            Object entity = Class
                    .forName(entityName)
                    .getConstructor()
                    .newInstance();
            LispelCreateRepository annotation = entity
                    .getClass()
                    .getAnnotation(LispelCreateRepository.class);
            LispelDAO dao = (LispelDAO) annotation.dao().getConstructor(Application.class)
                    .newInstance(getApplication());

            if (!titleForStringValueDAO.equals("")) {
                dao.setTitle(titleForStringValueDAO);
            }

            Repository repository = new Repository() {

                @Override
                public LiveData<Long> insert(SavingObject savingObject) {
                    MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mutableLiveData.postValue(dao.insert(savingObject));
                            } catch (SQLiteConstraintException e) {
                                mutableLiveData.postValue(null);
                            }

                        }
                    }).start();
                    return mutableLiveData;
                }

                @Override
                public LiveData<List<String>> getNameAllEntities(LifecycleOwner owner) {
                    MutableLiveData<List<String>> result = new MutableLiveData<>();
                    ArrayList<String> arrayList = new ArrayList();
                    dao.getAllEntities(owner).observe(owner, x -> {
                        for (SavingObject p : x) {
                            arrayList.add(p.getDescription());
                        }
                        result.postValue(arrayList);
                    });
                    return result;
                }

                @Override
                public LiveData<List<String>> getNameAllEntitiesByProperty(LifecycleOwner owner, String property) {
                    MutableLiveData<List<String>> result = new MutableLiveData<>();
                    ArrayList<String> arrayList = new ArrayList();
                    if (!property.equals("")) {
                        dao.getAllEntitiesByProperty(owner, "%" + property + "%").observe(owner, x -> {
                            for (SavingObject p : x) {
                                arrayList.add(p.getDescription());
                            }
                            result.postValue(arrayList);
                        });
                        return result;
                    } else {
                        dao.getAllEntities(owner).observe(owner, x -> {
                            for (SavingObject p : x) {
                                arrayList.add(p.getDescription());
                            }
                            result.postValue(arrayList);
                        });
                        return result;
                    }
                }

                @Override
                public LiveData<SavingObject> getEntityById(LifecycleOwner owner, Long id) {
                    MutableLiveData<SavingObject> result = new MutableLiveData<>();
                    dao.getEntityById(id).observe(owner, x -> {
                        result.postValue((SavingObject) x);
                    });
                    return result;
                }

            };
            return repository;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NullPointerException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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