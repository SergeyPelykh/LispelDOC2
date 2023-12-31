


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
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.lispelDoc2.dao.StickerDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.Repository;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.Client;
import com.example.lispelDoc2.models.Field;
import com.example.lispelDoc2.models.OrderUnit;
import com.example.lispelDoc2.models.PrintUnit;
import com.example.lispelDoc2.models.Sticker;
import com.example.lispelDoc2.uiServices.FieldSetViews;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
        baseOptionListViews.add(findViewById(R.id.stickers_of_client_ListView));
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
        StickerDAO stickerDAO = LispelRoomDatabase.getDatabase(getApplicationContext()).stickerDAO();


        ArrayAdapter<String> adapterStickers = new ArrayAdapter<>(NewOrderActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());
        baseOptionListViews.get(1).setAdapter(adapterStickers);




        clientLiveData.observe(NewOrderActivity.this, insertedValue -> {
            adapterStickers.clear();
            clientDAO.getEntityByName(insertedValue).observe(NewOrderActivity.this, foundedObject -> {
                if (foundedObject != null) {
                    adapterStickers.clear();
                    client = foundedObject;
                    visionTextViews.get(0).setText(client.getName());
                    clientOrderUnits.clear();
                    ArrayList<String> stickers = foundedObject.getNumbers();
                    if (stickers != null && !stickers.isEmpty()) {
                        adapterStickers.addAll(stickers);
                        baseOptionListViews.get(1).setVisibility(View.VISIBLE);
                    }
                    adapterStickers.add("добавить устройство");
                    baseOptionListViews.get(1).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if ( position == baseOptionListViews.get(1).getAdapter().getCount() - 1){
                                Intent intent = new Intent(NewOrderActivity.this, CreateNewEntityDialogActivity.class);
                                    intent.putExtra("nameEntityClass", "com.example.lispelDoc2.models.OrderUnit");
                                    intent.putExtra("repositoryTitle", "OrderUnit");
                                    intent.putExtra("владелец", client.getName());
                                startForResult.launch(intent);

                            }else {
                                Dialog dialog = new Dialog(NewOrderActivity.this);
                                dialog.setContentView(R.layout.stickers_item_dialog);
                                dialog.show();
                                TextView textView = (TextView) dialog.findViewById(R.id.title_textview);
                                textView.setText(stickers.get(position));
                                AppCompatButton recyclingButton = (AppCompatButton) dialog.findViewById(R.id.recycling_button);
                                recyclingButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                        Dialog dialogRecycling = new Dialog(NewOrderActivity.this);
                                        dialogRecycling.setContentView(R.layout.recycling_dialog);
                                        dialogRecycling.show();
                                        ListView tonerListView = (ListView)dialogRecycling.findViewById(R.id.toner_listView);
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(NewOrderActivity.this,
                                                android.R.layout.simple_list_item_1,
                                                new ArrayList<>());
                                        tonerListView.setAdapter(adapter);
                                        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("toner1", "toner1", "toner1", "toner1", "toner1", "toner1", "toner1"));
                                        adapter.addAll(arrayList);
                                        TextView stickerNumberTextView = (TextView) dialogRecycling.findViewById(R.id.title_textview);
                                        stickerNumberTextView.setText(stickers.get(position));
                                    }
                                });

//                                new AlertDialog.Builder(NewOrderActivity.this)
//                                        .setTitle(stickers.get(position))
//                                        .setPositiveButton("заправить", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
//                                            }
//                                        })
//                                        .setNeutralButton("восстановить", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
//                                            }
//                                        })
//                                        .setNeutralButton("восстановить", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
//                                            }
//                                        })
//                                        .setNegativeButton("почистить", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
//                                            }
//                                        })
//                                        .show();
//                                Intent intent = new Intent(NewOrderActivity.this, CreateNewEntityDialogActivity.class);
//                                intent.putExtra("nameEntityClass", "com.example.lispelDoc2.models.Service");
//                                intent.putExtra("repositoryTitle", "Service");
//                                intent.putExtra("printUnit", stickers.get(position));
//                                startForResult.launch(intent);
                            }
                        }
                    });

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

                                        //System.out.println("**************** insert in LiveData from fieldTextView OnClickListener " + x.get(position).substring(0, x.get(position).indexOf(" ")));
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
                            //System.out.println("****************************** get allEntity");
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
                                        //fieldTextView.setText(x.get(position));
                                        hideKeyboard(context, inputEditText);
                                        inputEditText.setVisibility(View.INVISIBLE);
                                        imageButton.setVisibility(View.INVISIBLE);
                                        titleTextView.setVisibility(View.VISIBLE);
                                        fieldTextView.setVisibility(View.VISIBLE);
                                    }
                                    listView.setVisibility(View.GONE);
                                    resultLiveData.postValue(x.get(position));
                                    //System.out.println("**************** insert in LiveData from onTextChanged");
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

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        System.out.println("***************************************************");
                        if (result.getData().getStringExtra("classNameInsertedEntity").equals("com.example.lispelDoc2.models.Sticker")) {
                            if (client != null) {
                                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                StickerDAO stickerDAO = LispelRoomDatabase.getDatabase(getApplicationContext()).stickerDAO();
                                LiveData<Sticker> liveData = stickerDAO.getEntityById(Long.parseLong(result.getData().getStringExtra("createdEntityWithId")));
                                       liveData.observe(NewOrderActivity.this, x -> {
                                            System.out.println("client had stickers " + client.getNumbers());
                                            client.addNumber(x.getNumber());
                                            System.out.println("client have stickers now " + client.getNumbers());
                                            ClientDAO clientDAO = LispelRoomDatabase.getDatabase(getApplicationContext()).clientDAO();
                                            LispelRoomDatabase.databaseWriteExecutor.execute(() -> {
                                                clientDAO.updateEntity(client);
                                            });
                                            liveData.removeObservers(NewOrderActivity.this);

                                        });
                            }
                        }
                        if (result.getData().getStringExtra("classNameInsertedEntity").equals("com.example.lispelDoc2.models.OrderUnit")) {
                            if (client != null) {
                                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                OrderUnitDAO orderUnitDAO = LispelRoomDatabase.getDatabase(getApplicationContext()).orderUnitDAO();
                                LiveData<OrderUnit> liveData = orderUnitDAO.getEntityById(Long.parseLong(result.getData().getStringExtra("createdEntityWithId")));
                                liveData.observe(NewOrderActivity.this, x -> {
                                    System.out.println("client had stickers " + client.getNumbers());
                                    client.addNumber(x.getStickerNumber());
                                    System.out.println("client have stickers now " + client.getNumbers());
                                    ClientDAO clientDAO = LispelRoomDatabase.getDatabase(getApplicationContext()).clientDAO();
                                    LispelRoomDatabase.databaseWriteExecutor.execute(() -> {
                                        clientDAO.updateEntity(client);
                                    });
                                    liveData.removeObservers(NewOrderActivity.this);

                                });
                            }
                        }
                    }
                }
            });


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