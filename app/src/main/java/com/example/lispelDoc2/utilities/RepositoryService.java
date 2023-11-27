package com.example.lispelDoc2.utilities;

import androidx.lifecycle.LiveData;

import com.example.lispelDoc2.interfaces.GetListOfFields;

import java.util.ArrayList;
import java.util.List;

public interface RepositoryService {
    LiveData<List<String>> findAllByStringField(String field);
    LiveData<? extends GetListOfFields> findByStringField(String field);
    Long insert(GetListOfFields getListOfFields);
    Long insertNewEntityInBase(ArrayList<String> fields);
    ArrayList<String> getListOfFields();
}
