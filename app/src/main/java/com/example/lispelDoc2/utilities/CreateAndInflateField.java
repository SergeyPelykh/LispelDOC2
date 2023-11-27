//package com.example.lispelDoc2.utilities;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Application;
//import android.content.Context;
//import android.content.Intent;
//import android.database.sqlite.SQLiteConstraintException;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.lispelDoc2.Activities.CreateNewEntityDialogActivity;
//import com.example.lispelDoc2.interfaces.LispelCreateRepository;
//import com.example.lispelDoc2.interfaces.LispelDAO;
//import com.example.lispelDoc2.interfaces.Repository;
//import com.example.lispelDoc2.interfaces.SavingObject;
//import com.example.lispelDoc2.models.Field;
//import com.example.lispelDoc2.uiServices.FieldSetViews;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CreateAndInflateField {
//    @SuppressLint("WrongConstant")
//    public static void createAndInflateField(Activity activity,
//                                             Application application,
//                                             Context context,
//                                             TextView fieldTextView,
//                                             TextView titleTextView,
//                                             EditText inputEditText,
//                                             ListView listView,
//                                             ImageButton imageButton,
//                                             Field field){
//            FieldSetViews fieldSetViews = new FieldSetViews(
//                    field.getInscription(),
//                    field.getName(),
//                    fieldTextView,
//                    inputEditText,
//                    titleTextView,
//                    listView,
//                    imageButton);
//            allFieldsOnLayout.add(fieldSetViews);
//
//            fieldTextView.setVisibility(field.getNameTextViewVisibility());
//            fieldTextView.setHint(field.getHint());
//            titleTextView.setVisibility(field.getInscriptionTextViewVisibility());
//            titleTextView.setText(field.getInscription());
//            inputEditText.setInputType(field.getInputType());
//
//            Repository repository = field.getDataSource();
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
//                    android.R.layout.simple_list_item_1,
//                    new ArrayList<>());
//
//            fieldTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    for (FieldSetViews f : allFieldsOnLayout) {
//                        if (f != fieldSetViews) {
//                            f.removeFocus(activity);
//                        }
//                    }
//                    inputEditText.setText(fieldTextView.getText());
//                    fieldTextView.setVisibility(View.INVISIBLE);
//                    titleTextView.setVisibility(View.GONE);
//                    inputEditText.setVisibility(View.VISIBLE);
//                    inputEditText.setSelection(inputEditText.getText().length());
//
//
//                    listView.setAdapter(adapter);
//
//                    if (repository != null) {
//                        LiveData<List<String>> allEntity = repository.getNameAllEntitiesByProperty((LifecycleOwner) activity,
//                                fieldTextView.getText().toString());
//                        allEntity.observe((LifecycleOwner) activity, x -> {
//                                    allEntity.removeObservers((LifecycleOwner) activity);
//                                    if (x.size() != 0) {
//                                        adapter.clear();
//                                        adapter.addAll(x);
//                                        listView.setVisibility(View.VISIBLE);
//                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                if (field.isFromBaseAndEdit()) {
//                                                    inputEditText.setText(x.get(position) + " ");
//                                                    inputEditText.setSelection(inputEditText.getText().length());
//                                                } else {
//                                                    fieldTextView.setText(x.get(position));
//                                                    hideKeyboard(context, inputEditText);
//                                                    inputEditText.setVisibility(View.INVISIBLE);
//                                                    imageButton.setVisibility(View.INVISIBLE);
//                                                    titleTextView.setVisibility(View.VISIBLE);
//                                                    fieldTextView.setVisibility(View.VISIBLE);
//                                                }
//                                                listView.setVisibility(View.GONE);
//                                            }
//                                        });
//                                    }
//                                }
//                        );
//                        if (field.isWriteInBase()) {
//                            imageButton.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//
//                    showKeyboard(context, inputEditText);
//                    inputEditText.setOnKeyListener(new View.OnKeyListener() {
//                        @Override
//                        public boolean onKey(View v, int keyCode, KeyEvent event) {
//                            if (event.getAction() == KeyEvent.ACTION_DOWN &&
//                                    keyCode == KeyEvent.KEYCODE_ENTER) {
//                                if (repository == null) {
//                                    fieldTextView.setText(inputEditText.getText());
//                                }
//                                if (field.isFromBaseAndEdit()) {
//                                    fieldTextView.setText(inputEditText.getText());
//                                }
//                                imageButton.setVisibility(View.INVISIBLE);
//                                inputEditText.setVisibility(View.INVISIBLE);
//                                listView.setVisibility(View.GONE);
//                                fieldTextView.setVisibility(View.VISIBLE);
//                                titleTextView.setVisibility(View.VISIBLE);
//                                hideKeyboard(context, inputEditText);
//                                return true;
//                            }
//                            return false;
//                        }
//                    });
//                }
//            });
//
//            ActivityResultLauncher<Intent> startForResult = application.registerForActivityResult(
//                    new ActivityResultContracts.StartActivityForResult(),
//                    new ActivityResultCallback<ActivityResult>() {
//                        @Override
//                        public void onActivityResult(ActivityResult result) {
//                            if (result.getResultCode() == Activity.RESULT_OK && result.getData().getStringExtra(field.getName()) != null) {
//                                Repository resultRepository;
//                                if (!field.getLinkedValueName().equals("")){
//                                    resultRepository = createRepository(application, field.getNameEntityClass(), field.getLinkedValueName());
//                                } else {
//                                    resultRepository = createRepository(application, field.getNameEntityClass());
//                                }
//                                if (!(resultRepository == null)) {
//                                    resultRepository.getEntityById((LifecycleOwner) activity, Long.parseLong(result.getData().getStringExtra(field.getName())))
//                                            .observe((LifecycleOwner) activity, x -> {
//                                                fieldTextView.setText(x.getDescription());
//                                                hideKeyboard(context, inputEditText);
//                                                inputEditText.setVisibility(View.INVISIBLE);
//                                                imageButton.setVisibility(View.INVISIBLE);
//                                                adapter.clear();
//                                                listView.setVisibility(View.GONE);
//                                                titleTextView.setVisibility(View.VISIBLE);
//                                                fieldTextView.setVisibility(View.VISIBLE);
//                                            });
//                                }else {
//                                    Toast.makeText(context, "database not created",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    }
//            );
//
//            if (field.isWriteInBase()) {
//                imageButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(activity, CreateNewEntityDialogActivity.class);
//                        String nameEntityClass = field.getNameEntityClass();
//                        if (!nameEntityClass.equals("")) {
//                            intent.putExtra("nameEntityClass", nameEntityClass);
//                            intent.putExtra("createEntity", field.getName());
//                            intent.putExtra("repositoryTitle", field.getLinkedValueName());
//                        }
//                        startForResult.launch(intent);
//                    }
//                });
//            }
//
//
//            inputEditText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (repository != null) {
//                        LiveData<List<String>> allEntity = repository.getNameAllEntitiesByProperty((LifecycleOwner) activity, s.toString());
//                        allEntity.observe((LifecycleOwner) activity, x -> {
//                            if (x.size() != 0) {
//                                listView.setVisibility(View.VISIBLE);
//                                adapter.clear();
//                                adapter.addAll(x);
//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                        if (field.isFromBaseAndEdit()) {
//                                            inputEditText.setText(x.get(position) + " ");
//                                            inputEditText.setSelection(inputEditText.getText().length());
//                                        } else {
//                                            fieldTextView.setText(x.get(position));
//                                            hideKeyboard(context, inputEditText);
//                                            inputEditText.setVisibility(View.INVISIBLE);
//                                            imageButton.setVisibility(View.INVISIBLE);
//                                            titleTextView.setVisibility(View.VISIBLE);
//                                            fieldTextView.setVisibility(View.VISIBLE);
//                                        }
//                                        listView.setVisibility(View.GONE);
//                                    }
//                                });
//                            } else {
//                                adapter.clear();
//                                listView.setVisibility(View.GONE);
//                            }
//                            allEntity.removeObservers((LifecycleOwner) activity);
//                        });
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
//        }
//
//    private static void showKeyboard(Context context, EditText input) {
//        input.requestFocus();
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
//    }
//
//    private static void hideKeyboard(Context context, EditText input) {
//        input.requestFocus();
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
//    }
//
//    private static Repository createRepository(Application application, String ... args) {
//        String entityName = args[0];
//        String titleForStringValueDAO = "";
//        if (args.length > 1) {
//            titleForStringValueDAO = args[1];
//        }
//        try {
//            Object entity = Class
//                    .forName(entityName)
//                    .getConstructor()
//                    .newInstance();
//            LispelCreateRepository annotation = entity
//                    .getClass()
//                    .getAnnotation(LispelCreateRepository.class);
//            LispelDAO dao = (LispelDAO) annotation.dao().getConstructor(Application.class)
//                    .newInstance(application);
//
//            if (!titleForStringValueDAO.equals("")){
//                dao.setTitle(titleForStringValueDAO);
//            }
//
//            Repository repository = new Repository() {
//
//                @Override
//                public LiveData<Long> insert(SavingObject savingObject) {
//                    MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                mutableLiveData.postValue(dao.insert(savingObject));
//                            } catch (SQLiteConstraintException e) {
//                                mutableLiveData.postValue(null);
//                            }
//
//                        }
//                    }).start();
//                    return mutableLiveData;
//                }
//
//                @Override
//                public LiveData<List<String>> getNameAllEntities(LifecycleOwner owner) {
//                    MutableLiveData<List<String>> result = new MutableLiveData<>();
//                    ArrayList<String> arrayList = new ArrayList();
//                    dao.getAllEntities(owner).observe(owner, x -> {
//                        for (SavingObject p : x) {
//                            arrayList.add(p.getDescription());
//                        }
//                        result.postValue(arrayList);
//                    });
//                    return result;
//                }
//
//                @Override
//                public LiveData<List<String>> getNameAllEntitiesByProperty(LifecycleOwner owner, String property) {
//                    MutableLiveData<List<String>> result = new MutableLiveData<>();
//                    ArrayList<String> arrayList = new ArrayList();
//                    if (!property.equals("")) {
//                        dao.getAllEntitiesByProperty(owner, "%" + property + "%").observe(owner, x -> {
//                            for (SavingObject p : x) {
//                                arrayList.add(p.getDescription());
//                            }
//                            result.postValue(arrayList);
//                        });
//                        return result;
//                    } else {
//                        dao.getAllEntities(owner).observe(owner, x -> {
//                            for (SavingObject p : x) {
//                                arrayList.add(p.getDescription());
//                            }
//                            result.postValue(arrayList);
//                        });
//                        return result;
//                    }
//                }
//
//                @Override
//                public LiveData<SavingObject> getEntityById(LifecycleOwner owner, Long id) {
//                    MutableLiveData<SavingObject> result = new MutableLiveData<>();
//                    dao.getEntityById(id).observe(owner, x -> {
//                        result.postValue((SavingObject) x);
//                    });
//                    return result;
//                }
//
//            };
//            return repository;
//        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NullPointerException | NoSuchMethodException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//}
//
//
