package com.example.lispelDoc2.utilities;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispelDoc2.interfaces.SavingObject;

import java.util.ArrayList;
import java.util.List;

public class LiveDataListConverter {
    public static <T> LiveData<List<SavingObject>> convert (LifecycleOwner owner, LiveData<List<T>> param) {
        MutableLiveData<List<SavingObject>> result = new MutableLiveData<>();
        ArrayList<SavingObject> arr = new ArrayList<>();
        param.observe(owner, x -> {
            if (!x.isEmpty()){
                for (T t : x) {
                    arr.add((SavingObject) t);
                }
            }
            result.postValue(arr);
        });
        return result;
    }
}
