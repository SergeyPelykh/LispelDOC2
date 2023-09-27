package com.example.lispeldoc2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lispeldoc2.interfaces.ListedFields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Entity(tableName = "printUnit_table")
public class PrintUnit implements ListedFields {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Date dateOfCreate;
    private String partName;
    private String vendor;
    private String model;
    private String originality;
    private String number;
    private String oldNumbers;

    public void changeNumber(String newNumber){
        this.oldNumbers = this.oldNumbers + "|" + this.number;
        this.number = newNumber;
    }

    public PrintUnit() {
        this.dateOfCreate = new Date();
        this.number = "LS24";
    }


    @Override
    public ArrayList<String> getListOfFields() {
        return fields;
    }

    @Override
    public String getDescription() {
        return partName + " " + vendor + " " + model;
    }

    private static final ArrayList<String> fields = new ArrayList<>(
            Arrays.asList("number", "partName", "vendor", "model", "originality"));
}
