package com.example.lispelDoc2.models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.lispelDoc2.dao.proxyDAO.PrintUnitProxyDAO;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.RepositoryEnum;
import com.example.lispelDoc2.interfaces.SavingObject;

import java.util.ArrayList;

@LispelCreateRepository(
        dao = PrintUnitProxyDAO.class,
        entity = com.example.lispelDoc2.models.PrintUnit.class)
@Entity(tableName = "printUnit_table",
        indices = {@Index(value = {"model"}, unique = true)})
public class PrintUnit implements SavingObject{
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @LispelAddValueByUser(number = 1,
            name = "тип",
            name_hint = "тип",
            name_title = "тип",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.StringValue",
            repository_title = "PrintUnitType")
    private String partName;
    @LispelAddValueByUser(number = 2,
            name = "производитель",
            name_hint = "производитель",
            name_title = "производитель",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.StringValue",
            repository_title = "vendor")
    private String vendor;
    @LispelAddValueByUser(number = 3,
            name = "модель",
            name_hint = "модель",
            name_title = "модель",
            input_type = 1)
    private String model;
    @LispelAddValueByUser(number = 4,
            name = "совместимость",
            name_hint = "совместимость",
            name_title = "совместимость",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.StringValue",
            repository_title = "originality")
    private String originality;

    public PrintUnit() {
    }

    public PrintUnit(ArrayList<String> arr) {
        this.partName = arr.get(0);
        this.vendor = arr.get(1);
        this.model = arr.get(2);
        this.originality = arr.get(3);
    }
    public Long getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return vendor + "|" + model;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOriginality() {
        return originality;
    }

    public void setOriginality(String originality) {
        this.originality = originality;
    }
}
