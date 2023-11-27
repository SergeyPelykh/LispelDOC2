package com.example.lispelDoc2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lispelDoc2.dao.proxyDAO.StringValueProxyDAO;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.SavingObject;

import java.util.ArrayList;
@LispelCreateRepository(
        dao = StringValueProxyDAO.class,
        entity = com.example.lispelDoc2.models.StringValue.class)
@Entity(tableName = "string_value_table")
public class StringValue implements SavingObject {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    @LispelAddValueByUser(number = 1,
    name = "значение",
    name_hint = "значение",
    name_title = "значение",
    input_type = 1)
    private String name;

    public StringValue() {
    }

    public StringValue(ArrayList<String> arr) {
        this.name =arr.get(0) ;
        //this.title = arr.get(1);
    }

    public StringValue(String title, String name) {
        this.title = title;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }


    @Override
    public String getDescription() {
        return this.name;
    }




    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
