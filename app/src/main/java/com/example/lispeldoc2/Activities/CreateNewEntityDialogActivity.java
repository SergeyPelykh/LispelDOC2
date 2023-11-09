package com.example.lispeldoc2.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispeldoc2.R;
import com.example.lispeldoc2.interfaces.LispelAddValueByUser;
import com.example.lispeldoc2.interfaces.LispelCreateRepository;
import com.example.lispeldoc2.interfaces.LispelDAO;
import com.example.lispeldoc2.interfaces.Repository;
import com.example.lispeldoc2.interfaces.RepositoryEnum;
import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.models.Field;
import com.example.lispeldoc2.repository.StringValueRepository;
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

        Intent intent = getIntent();

        LispelAddValueByUser annotationStringValueRepository;
        Map<Integer, Field> mapFields = new HashMap<>();

        AppCompatButton cancelButton = findViewById(R.id.cancel_add_entity_in_base_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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


        Object o = null;
        try {
            o = Class.forName(getIntent().getStringExtra("nameEntityClass")).getConstructor().newInstance();

        } catch (IllegalAccessException |
                InvocationTargetException |
                InstantiationException |
                NoSuchMethodException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        AppCompatButton saveButton = findViewById(R.id.add_entity_in_base_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReturn = new Intent();
                if (!mapFields.isEmpty() && reviseInputField(visionTextViews, inscriptionTextViews, mapFields.size())) {
                    ArrayList<String> dataForCreateEntity = new ArrayList<>();
                    for (int i = 1; i <= mapFields.size(); i++) {
                        if (mapFields.get(i).getResultId().equals("")) {
                            dataForCreateEntity.add(visionTextViews.get(i - 1).getText().toString());
                        } else {
                            dataForCreateEntity.add(mapFields.get(i).getResultId());
                        }
                    }
                    SavingObject savingObject = null;
                    try {
                        savingObject = (SavingObject) Class
                                .forName(getIntent().getStringExtra("nameEntityClass"))
                                .getConstructor(new Class[]{ArrayList.class})
                                .newInstance(dataForCreateEntity);
                    } catch (IllegalAccessException |
                            InvocationTargetException |
                            InstantiationException |
                            NoSuchMethodException |
                            ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (savingObject != null) {
                        Repository repository = null;
                        if (savingObject.getRepository(getApplication(), "") != null) {
                            repository = savingObject.getRepository(getApplication(), "");
                        } else {
//                            repository = new Repository() {
//                                Object o;
//
//                                {
//                                    try {
//                                        o = Class.forName(intent.getStringExtra("nameEntityClass")).getConstructor().newInstance();
//                                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                LispelCreateRepository annotation = o.getClass().getAnnotation(LispelCreateRepository.class);
//                                LispelDAO dao;
//
//                                {
//                                    try {
//                                        dao = (LispelDAO) annotation.dao().getConstructor(Application.class)
//                                                .newInstance(getApplication());
//                                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public LiveData<Long> insert(SavingObject savingObject) {
//                                    MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mutableLiveData.postValue(dao.insert(savingObject));
//                                        }
//                                    }).start();
//                                    return mutableLiveData;
//                                }
//
//                                @Override
//                                public LiveData<List<String>> getNameAllEntities(LifecycleOwner owner) {
//                                    MutableLiveData<List<String>> result = new MutableLiveData<>();
//                                    ArrayList<String> arrayList = new ArrayList();
//                                    dao.getAllEntities(owner).observe(owner, x -> {
//                                        for (SavingObject p : x) {
//                                            arrayList.add(p.getDescription());
//                                        }
//                                        result.postValue(arrayList);
//                                    });
//                                    return result;
//                                }
//
//                                @Override
//                                public LiveData<List<String>> getNameAllEntitiesByProperty(LifecycleOwner owner, String property) {
//                                    MutableLiveData<List<String>> result = new MutableLiveData<>();
//                                    ArrayList<String> arrayList = new ArrayList();
//                                    if (!property.equals("")) {
//                                        dao.getAllEntitiesByModel(owner, "%" + property + "%").observe(owner, x -> {
//                                            for (SavingObject p : x) {
//                                                arrayList.add(p.getDescription());
//                                            }
//                                            result.postValue(arrayList);
//                                        });
//                                        return result;
//                                    } else {
//                                        dao.getAllEntities(owner).observe(owner, x -> {
//                                            for (SavingObject p : x) {
//                                                arrayList.add(p.getDescription());
//                                            }
//                                            result.postValue(arrayList);
//                                        });
//                                        return result;
//                                    }
//                                }
//
//                                @Override
//                                public LiveData<SavingObject> getEntityById(LifecycleOwner owner, Long id) {
//                                    return null;
//                                }
//                            };
                            repository = createRepository(intent.getStringExtra("nameEntityClass"));
                        }
                        if (repository != null) {
                            repository.insert(savingObject).observe(CreateNewEntityDialogActivity.this, x -> {
                                intentReturn.putExtra(intent.getStringExtra("createEntity"), x.toString());
                                setResult(RESULT_OK, intentReturn);
                                onBackPressed();
                            });
                        }

                    }
                }
            }
        });

        if (o != null) {
            java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();
            for (java.lang.reflect.Field f : fields) {
                if (f.isAnnotationPresent(LispelAddValueByUser.class)) {
                    annotationStringValueRepository = (LispelAddValueByUser) f.getAnnotation(LispelAddValueByUser.class);
                    Field field = new Field();
                    field.setName(annotationStringValueRepository.name());
                    field.setHint(annotationStringValueRepository.name_hint());
                    field.setInscription(annotationStringValueRepository.name_title());
                    field.setInputType(annotationStringValueRepository.input_type());
                    field.setNameEntityClass(annotationStringValueRepository.class_entity());

                    if (annotationStringValueRepository.base() == RepositoryEnum.SAVE_IN_BASE) {
                        field.setWriteInBase(true);
                    }
                    if (annotationStringValueRepository.base() == RepositoryEnum.READ_FROM_BASE_AND_EDIT) {
                        field.setFromBaseAndEdit(true);
                    }


                    Repository repository = null;

                    Class repositoryClass = annotationStringValueRepository.repository();
                    if (!repositoryClass.isInstance(Object.class)) {
                        if (repositoryClass.isAssignableFrom(StringValueRepository.class)) {
                            repository = new StringValueRepository(getApplication(), annotationStringValueRepository.repository_title());
                        } else {
                            try {
                                repository = (Repository) annotationStringValueRepository.repository()
                                        .getConstructor(Application.class)
                                        .newInstance(getApplication());
                            } catch (IllegalAccessException |
                                    InstantiationException |
                                    InvocationTargetException |
                                    SecurityException |
                                    NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (annotationStringValueRepository.class_entity().equals("com.example.lispeldoc2.models.Toner")) {
//                        repository = new Repository() {
//                            Object o;
//
//                            {
//                                try {
//                                    o = Class.forName(f.getAnnotation(LispelAddValueByUser.class).class_entity()).getConstructor().newInstance();
//                                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            LispelCreateRepository annotationLispelCreateRepository = o.getClass().getAnnotation(LispelCreateRepository.class);
//                            LispelDAO dao;
//
//                            {
//                                try {
//                                    dao = (LispelDAO) annotationLispelCreateRepository.dao().getConstructor(Application.class)
//                                            .newInstance(getApplication());
//                                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public LiveData<Long> insert(SavingObject savingObject) {
//                                MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mutableLiveData.postValue(dao.insert(savingObject));
//                                    }
//                                }).start();
//                                return mutableLiveData;
//                            }
//
//                            @Override
//                            public LiveData<List<String>> getNameAllEntities(LifecycleOwner owner) {
//                                MutableLiveData<List<String>> result = new MutableLiveData<>();
//                                ArrayList<String> arrayList = new ArrayList();
//                                dao.getAllEntities(owner).observe(owner, x -> {
//                                    for (SavingObject p : x) {
//                                        arrayList.add(p.getDescription());
//                                    }
//                                    result.postValue(arrayList);
//                                });
//                                return result;
//                            }
//
//                            @Override
//                            public LiveData<List<String>> getNameAllEntitiesByProperty(LifecycleOwner owner, String property) {
//                                MutableLiveData<List<String>> result = new MutableLiveData<>();
//                                ArrayList<String> arrayList = new ArrayList();
//                                if (!property.equals("")) {
//                                    dao.getAllEntitiesByModel(owner, "%" + property + "%").observe(owner, x -> {
//                                        for (SavingObject p : x) {
//                                            arrayList.add(p.getDescription());
//                                        }
//                                        result.postValue(arrayList);
//                                    });
//                                    return result;
//                                } else {
//                                    dao.getAllEntities(owner).observe(owner, x -> {
//                                        for (SavingObject p : x) {
//                                            arrayList.add(p.getDescription());
//                                        }
//                                        result.postValue(arrayList);
//                                    });
//                                    return result;
//                                }
//                            }
//
//                            @Override
//                            public LiveData<SavingObject> getEntityById(LifecycleOwner owner, Long id) {
//                                return null;
//                            }
//                        };
                        repository = createRepository("com.example.lispeldoc2.models.Toner");
                    }
                    field.setDataSource(repository);
                    mapFields.put(annotationStringValueRepository.number(), field);
                }

            }
        }
        int i = 0;
        for (int key : mapFields.keySet()) {
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
                if (!allFieldsOnLayout.isEmpty()) {
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
                    if (f != fieldSetViews) {
                        f.removeFocus(CreateNewEntityDialogActivity.this);
                    }
                }
                inputEditText.setText(fieldTextView.getText());
                fieldTextView.setVisibility(View.INVISIBLE);
                titleTextView.setVisibility(View.GONE);
                inputEditText.setVisibility(View.VISIBLE);
                inputEditText.setSelection(inputEditText.getText().length());


                listView.setAdapter(adapter);

                if (repository != null) {
                    LiveData<List<String>> allEntity = repository.getNameAllEntitiesByProperty(CreateNewEntityDialogActivity.this,
                            fieldTextView.getText().toString());
                    allEntity.observe(CreateNewEntityDialogActivity.this, x -> {
                                allEntity.removeObservers(CreateNewEntityDialogActivity.this);
                                if (x.size() != 0) {
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
                                                fieldTextView.setText(x.get(position));
                                                hideKeyboard(context, inputEditText);
                                                inputEditText.setVisibility(View.INVISIBLE);
                                                imageButton.setVisibility(View.INVISIBLE);
                                                titleTextView.setVisibility(View.VISIBLE);
                                                fieldTextView.setVisibility(View.VISIBLE);
                                            }
                                            listView.setVisibility(View.GONE);
                                        }
                                    });
                                }
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

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData().getStringExtra(field.getName()) != null) {
                            Repository resultRepository = createRepository(field.getNameEntityClass());
                            resultRepository.getEntityById(CreateNewEntityDialogActivity.this, Long.parseLong(result.getData().getStringExtra(field.getName())))
                                    .observe(CreateNewEntityDialogActivity.this, x -> {
                                        fieldTextView.setText(x.getDescription());
                                        hideKeyboard(context, inputEditText);
                                        inputEditText.setVisibility(View.INVISIBLE);
                                        imageButton.setVisibility(View.INVISIBLE);
                                        adapter.clear();
                                        listView.setVisibility(View.GONE);
                                        titleTextView.setVisibility(View.VISIBLE);
                                        fieldTextView.setVisibility(View.VISIBLE);
                                    });
                        }
                    }
                }
        );

        if (field.isWriteInBase()) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CreateNewEntityDialogActivity.this, CreateNewEntityDialogActivity.class);
                    String nameEntityClass = field.getNameEntityClass();
                    if (!nameEntityClass.equals("")) {
                        intent.putExtra("nameEntityClass", nameEntityClass);
                        intent.putExtra("createEntity", field.getName());
                    }
                    startForResult.launch(intent);
                }
            });
        }


        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (repository != null) {
                    LiveData<List<String>> allEntity = repository.getNameAllEntitiesByProperty(CreateNewEntityDialogActivity.this, s.toString());
                    allEntity.observe(CreateNewEntityDialogActivity.this, x -> {
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
                                }
                            });
                        } else {
                            adapter.clear();
                            listView.setVisibility(View.GONE);
                        }
                        allEntity.removeObservers(CreateNewEntityDialogActivity.this);
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean reviseInputField(ArrayList<TextView> visionTextViews, ArrayList<TextView> inscriptionTextViews, int fields) {
        for (int i = 0; i < fields; i++) {
            if (visionTextViews.get(i).getText().equals("")) {
                new AlertDialog.Builder(CreateNewEntityDialogActivity.this)
                        .setTitle("заполните поле:")
                        .setMessage(inscriptionTextViews.get(i).getText())
                        .show();
                return false;
            }
        }
        return true;
    }

    private Repository createRepository(String entityName) {
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

            Repository repository = new Repository() {

                @Override
                public LiveData<Long> insert(SavingObject savingObject) {
                    MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mutableLiveData.postValue(dao.insert(savingObject));
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
                        dao.getAllEntitiesByModel(owner, "%" + property + "%").observe(owner, x -> {
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
                    dao.getEntityById(owner, id).observe(owner, x -> {
                        result.postValue(x);
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