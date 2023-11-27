package com.example.lispelDoc2.models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.lispelDoc2.dao.proxyDAO.StickerProxyDAO;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.utilities.StringConstants;

import java.util.ArrayList;
import java.util.Date;

@LispelCreateRepository(
        dao = StickerProxyDAO.class,
        entity = com.example.lispelDoc2.models.Sticker.class)
@Entity(tableName = "sticker_table",
indices = {@Index(value = {"number"}, unique = true)})
public class Sticker implements SavingObject {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @LispelAddValueByUser(
            number = 1,
            name = "номер стикера",
            name_hint = "номер стикера",
            name_title = "номер стикера",
            input_type = 3)
    private String number;
    private Date dateOfCreate;

    public Sticker() {
    }

    public Sticker(ArrayList<String> arr) {
        this.number = StringConstants.companyID + arr.get(0);
        this.dateOfCreate = new Date();
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (!number.contains(StringConstants.companyID)){
            this.number = StringConstants.companyID + number;
        } else {
            this.number = number;
        }
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return this.number;
    }
}
