package com.example.lispelDoc2.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import com.example.lispelDoc2.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SaveBaseToJsonHelper {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static MutableLiveData<String> baseToJsonToFile(Context context, RoomDatabase database, File file) throws InvocationTargetException, IllegalAccessException {
        MutableLiveData<String> resultLiveData = new MutableLiveData<>();
        Dialog requestForSaving = new AlertDialog.Builder(context).setMessage("Save database in file?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        File directoryForSavingDatabase = new File(file, "backup_" + new SimpleDateFormat("dd_MM_yyyy_HH_mm").format(new Date()));
                        if (!directoryForSavingDatabase.exists()) {
                            directoryForSavingDatabase.mkdir();
                        }
                        List<Method> daoList = Arrays.stream(database.getClass().getMethods()).filter(f -> f.getName().contains("DAO")).collect(Collectors.toList());
                        ObjectMapper objectMapper = new ObjectMapper();
                        for (Method method : daoList) {
                            Object daoObject = null;
                            try {
                                daoObject = method.invoke(database);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            assert daoObject != null;
                            Method getAllEntitiesMethodFromDao = Arrays.stream(daoObject.getClass().getDeclaredMethods()).filter(f -> f.getName().contains("getAllEntities")).findFirst().get();
                            LiveData<List<Object>> allEntities = null;
                            try {
                                allEntities = (LiveData<List<Object>>) getAllEntitiesMethodFromDao.invoke(daoObject);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }

                            assert allEntities != null;
                            allEntities.observe((LifecycleOwner) context, gotList -> {
                                File fileForSavingTableOfDatabase = new File(directoryForSavingDatabase, method.getName() + ".txt");
                                try (FileOutputStream fileOutputStream = new FileOutputStream(fileForSavingTableOfDatabase);
                                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream))) {
                                    String jsonForSaving = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(gotList);
                                    bufferedWriter.write(jsonForSaving);
                                    System.out.println("create file " + fileForSavingTableOfDatabase.getName());
                                } catch (JsonProcessingException | FileNotFoundException e) {
                                    e.printStackTrace();
                                    System.out.println("Parsing JSON error");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                }).create();
        requestForSaving.show();
        return resultLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static MutableLiveData<String> fileToJsonToBase(Context context, RoomDatabase database, File directory) throws InvocationTargetException, IllegalAccessException {
        MutableLiveData<String> resultLiveData = new MutableLiveData<>();
        Dialog selectFileForRecovery = new AlertDialog.Builder(context).setView(R.layout.stickers_item_dialog).show();
        AppCompatButton button = selectFileForRecovery.findViewById(R.id.recycling_button);
        button.setVisibility(View.GONE);
        ListView listViewOfFolders = selectFileForRecovery.findViewById(R.id.services_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                new ArrayList<>());
        listViewOfFolders.setAdapter(adapter);
        List<File> files = Arrays.stream(directory.listFiles()).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        List<String> folders = files.stream().map(File::getName).collect(Collectors.toList());
        listViewOfFolders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog requestForSaving = new AlertDialog.Builder(context).setMessage("Recover database from this file?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<File> daoFilesList = Arrays.stream(files.get(position).listFiles()).collect(Collectors.toList());
                                ObjectMapper objectMapper = new ObjectMapper();
                                daoFilesList.stream().forEach(daoFile -> {
                                    try {
                                        Object daoMethod = database.getClass().getMethod(daoFile.getName().substring(0, daoFile.getName().indexOf('.'))).invoke(database);


                                        if (daoFile.getName().equals("componentDAO.txt")) {
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {

//                                                    try {
//                                                        System.out.println(Arrays.stream(daoMethod.getClass().getMethods()).filter(method -> method.getName().contains("getCountEntities")).findFirst().get().invoke(daoMethod));
//                                                        Arrays.stream(daoMethod.getClass().getMethods()).filter(method -> method.getName().contains("deleteAll")).findFirst().get().invoke(daoMethod);
//                                                    } catch (IllegalAccessException | InvocationTargetException e) {
//                                                        e.printStackTrace();
//                                                    }


                                                    assert daoMethod != null;
                                                    Class<?> classEntity = Arrays.stream(daoMethod.getClass().getMethods()).filter(method -> method.getName().contains("insert")).findFirst().get().getParameters()[0].getType();
                                                    Class clazz = Array.newInstance(classEntity, 0).getClass();
                                                    Class<Object[]> arrayOfEntities = clazz.getClass().cast(clazz);

                                                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(daoFile))) {
                                                        StringBuilder stringBuilder = new StringBuilder();
                                                        String line;
                                                        while ((line = bufferedReader.readLine()) != null) {
                                                            stringBuilder.append(line);
                                                        }
                                                        Arrays.stream(objectMapper.readValue(stringBuilder.toString(), arrayOfEntities)).forEach(component ->
                                                                new Thread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        System.out.println("in method  Arrays.stream(objectMapper.readValue(stringBuilder.toString(), arrayOfEntities)).forEach(component ->");
                                                                        try {
                                                                            System.out.println(Arrays.stream(daoMethod.getClass().getMethods()).filter(method -> method.getName().contains("insert")).findFirst().get().invoke(daoMethod, component));
                                                                        } catch (IllegalAccessException | InvocationTargetException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }).start());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            }).start();
                                        }
                                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        }).show();
            }
        });
        adapter.clear();
        adapter.addAll(folders);
        return resultLiveData;
    }
}
