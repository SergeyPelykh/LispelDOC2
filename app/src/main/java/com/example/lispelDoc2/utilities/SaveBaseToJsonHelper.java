package com.example.lispelDoc2.utilities;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SaveBaseToJsonHelper {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static MutableLiveData<String> baseToJson(Context context, RoomDatabase database, File file) throws InvocationTargetException, IllegalAccessException {
        MutableLiveData<String> resultLiveData = new MutableLiveData<>();
        File directoryForSavingDatabase = new File(file, "backup_" + new SimpleDateFormat("dd_MM_yyyy_HH_mm").format(new Date()));
        if (!directoryForSavingDatabase.exists()) {
            directoryForSavingDatabase.mkdir();
        }
        List<Method> daoList = Arrays.stream(database.getClass().getMethods()).filter(f -> f.getName().contains("DAO")).collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        for (Method method : daoList) {
            Object daoObject = method.invoke(database);
            assert daoObject != null;
            Method getAllEntitiesMethodFromDao = Arrays.stream(daoObject.getClass().getDeclaredMethods()).filter(f -> f.getName().contains("getAllEntities")).findFirst().get();
            LiveData<List<Object>> allEntities = (LiveData<List<Object>>) getAllEntitiesMethodFromDao.invoke(daoObject);

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

        return resultLiveData;
    }
}
