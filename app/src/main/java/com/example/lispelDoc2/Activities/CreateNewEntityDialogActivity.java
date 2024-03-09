package com.example.lispelDoc2.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispelDoc2.R;
import com.example.lispelDoc2.dao.StickerDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.Repository;
import com.example.lispelDoc2.interfaces.RepositoryEnum;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.Field;
import com.example.lispelDoc2.models.StringValue;
import com.example.lispelDoc2.uiServices.FieldSetViews;
import com.example.lispelDoc2.utilities.StringConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLClientInfoException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intentReturn = new Intent();
                if (!mapFields.isEmpty() && reviseInputField(visionTextViews, inscriptionTextViews, mapFields.size())) {
                    ArrayList<String> dataForCreateEntity = new ArrayList<>();
                    for (int i = 1; i <= mapFields.size(); i++) {
                        if (mapFields.get(i).getResultId().equals("")) {
                            dataForCreateEntity.add(visionTextViews.get(i - 1).getText().toString());
                            //System.out.println(visionTextViews.get(i - 1).getText().toString());
                        } else {
                            dataForCreateEntity.add(mapFields.get(i).getResultId());
                        }
                    }

                    dataForCreateEntity.stream().forEach(s -> System.out.println(s));

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
                        if (!intent.getStringExtra("repositoryTitle").equals("")) {
                            savingObject.setTitle(intent.getStringExtra("repositoryTitle"));
                            repository = createRepository(intent.getStringExtra("nameEntityClass"), intent.getStringExtra("repositoryTitle"));
                        } else {
                            repository = createRepository(intent.getStringExtra("nameEntityClass"));
                        }

                        if (repository != null) {
                            repository.insert(savingObject).observe(CreateNewEntityDialogActivity.this, x -> {
                                if (x == null) {
                                    new AlertDialog.Builder(CreateNewEntityDialogActivity.this)
                                            .setTitle("дублирование:")
                                            .setMessage("такой объект уже есть в базе")
                                            .show();
                                } else {
                                    intentReturn.putExtra("createdEntityWithId", x.toString());
                                    intentReturn.putExtra(intent.getStringExtra("createEntity"), x.toString());
                                    intentReturn.putExtra("classNameInsertedEntity", (intent.getStringExtra("nameEntityClass")));
                                    setResult(RESULT_OK, intentReturn);
                                    onBackPressed();
                                }
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
                    field.setLinkedValueName(annotationStringValueRepository.repository_title());
                    field.setTemplateValue(annotationStringValueRepository.template_value());
                    field.setHasTemplateValue(annotationStringValueRepository.hasTemplate_value());

                    if (annotationStringValueRepository.base() == RepositoryEnum.SAVE_IN_BASE
                            || annotationStringValueRepository.base() == RepositoryEnum.SAVE_IN_BASE_AND_NOT_TO_DISPLAY) {
                        field.setWriteInBase(true);
                    }
                    if (annotationStringValueRepository.base() == RepositoryEnum.READ_FROM_BASE_AND_EDIT) {
                        field.setFromBaseAndEdit(true);
                    }
                    if (annotationStringValueRepository.base() == RepositoryEnum.SAVE_IN_BASE_AND_NOT_TO_DISPLAY) {
                        field.setDisplayOptionsWhenCreate(false);
                    }
                    if (annotationStringValueRepository.base() == RepositoryEnum.READ_FROM_BASE_AND_CREATE_MULTI_VALUE) {
                        field.setMultiValue(true);
                    }

                        Repository repository = null;

                    //Class repositoryClass = annotationStringValueRepository.repository();
                    if (!annotationStringValueRepository.repository_title().equals("")) {
                        repository = createRepository(annotationStringValueRepository.class_entity(), annotationStringValueRepository.repository_title());
                    } else {
                        repository = createRepository(annotationStringValueRepository.class_entity());
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

        titleTextView.setVisibility(View.VISIBLE);

        Repository repository = field.getDataSource();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateNewEntityDialogActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());

        if (getIntent().getStringExtra(field.getName()) != null) {
            fieldTextView.setText(getIntent().getStringExtra(field.getName()));

        } else {

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

                    if (field.getName().equals("номер стикера") && field.isHasTemplateValue()) {
                        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(CreateNewEntityDialogActivity.this);
                        StickerDAO stickerDAO = lispelRoomDatabase.stickerDAO();
                        stickerDAO.getLastEntity().observe(CreateNewEntityDialogActivity.this, lastEntity -> {
                            if (lastEntity != null) {
                                inputEditText.setText(lastEntity.getNumber().substring(2));
                                inputEditText.setSelection(inputEditText.getText().length());
                            }
                        });
                    }

                    listView.setAdapter(adapter);

                    if (repository != null) {
                        if (field.isMultiValue()) {
                            LiveData<List<String>> allEntity = repository.getNameAllEntitiesByProperty(CreateNewEntityDialogActivity.this,
                                    fieldTextView.getText().toString());
                            allEntity.observe(CreateNewEntityDialogActivity.this, x -> {
                                        allEntity.removeObservers(CreateNewEntityDialogActivity.this);
                                        if (x.size() != 0) {
                                            adapter.clear();
                                            adapter.addAll(x);
                                            if (field.isDisplayOptionsWhenCreate()) {
                                                listView.setVisibility(View.VISIBLE);
                                            }
                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                    if (field.isFromBaseAndEdit()) {
                                                        inputEditText.setText(x.get(position) + " ");
                                                        inputEditText.setSelection(inputEditText.getText().length());
//                                                    } else {
//                                                        fieldTextView.setText(x.get(position));
//                                                        hideKeyboard(context, inputEditText);
//                                                        inputEditText.setVisibility(View.INVISIBLE);
//                                                        imageButton.setVisibility(View.INVISIBLE);
//                                                        titleTextView.setVisibility(View.VISIBLE);
//                                                        fieldTextView.setVisibility(View.VISIBLE);
//                                                    }
//                                                    listView.setVisibility(View.GONE);
                                                }
                                            });
                                        }
                                    }
                            );


                        } else {
                            LiveData<List<String>> allEntity = repository.getNameAllEntitiesByProperty(CreateNewEntityDialogActivity.this,
                                    fieldTextView.getText().toString());
                            allEntity.observe(CreateNewEntityDialogActivity.this, x -> {
                                        allEntity.removeObservers(CreateNewEntityDialogActivity.this);
                                        if (x.size() != 0) {
                                            adapter.clear();
                                            adapter.addAll(x);
                                            if (field.isDisplayOptionsWhenCreate()) {
                                                listView.setVisibility(View.VISIBLE);
                                            }
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
                                if (field.isFromBaseAndEdit() || field.isMultiValue()) {
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
        }

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
                            } else {
                                Toast.makeText(getApplicationContext(), "database not created", Toast.LENGTH_SHORT).show();
                            }
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
                        intent.putExtra("repositoryTitle", field.getLinkedValueName());
                    }
                    startForResult.launch(intent);
                }
            });
        }


        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (repository != null) {
                    if (field.isMultiValue()) {
                        LiveData<List<String>> allEntity = repository.getNameAllEntities(CreateNewEntityDialogActivity.this);
                        allEntity.observe(CreateNewEntityDialogActivity.this, x -> {
                            if (x.size() != 0) {
                                ArrayList<String> stringArrayList = (ArrayList<String>) x.stream().filter(item -> !inputEditText.getText().toString().contains(item)).collect(Collectors.toList());
                                if (field.isDisplayOptionsWhenCreate()) {
                                    listView.setVisibility(View.VISIBLE);
                                }
                                adapter.clear();
                                adapter.addAll(stringArrayList);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        inputEditText.setText(inputEditText.getText() + " " + stringArrayList.get(position) + " ");
                                        inputEditText.setSelection(inputEditText.getText().length());
                                    }
                                });
                            } else {
                                adapter.clear();
                                listView.setVisibility(View.GONE);
                            }
                            allEntity.removeObservers(CreateNewEntityDialogActivity.this);
                        });

                    } else {
                        LiveData<List<String>> allEntity = repository.getNameAllEntitiesByProperty(CreateNewEntityDialogActivity.this, s.toString());
                        allEntity.observe(CreateNewEntityDialogActivity.this, x -> {
                            if (x.size() != 0) {
                                if (field.isDisplayOptionsWhenCreate()) {
                                    listView.setVisibility(View.VISIBLE);
                                }
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