package com.example.lispelDoc2.models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "order_table",
        indices = {@Index(value = {"clientId", "servicesIdList"}, unique = true)})
public class Order {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Date dateOfCreate;
    //    @LispelAddValueByUser(number = 1,
//            name = "клиент",
//            name_hint = "клиент",
//            name_title = "клиент",
//            input_type = 1,
//            base = RepositoryEnum.SAVE_IN_BASE,
//            class_entity = "com.example.lispelDoc2.models.Client"
//    )
    private Long clientId;
    //    @LispelAddValueByUser(number = 2,
//            name = "услуги",
//            name_hint = "услуги",
//            name_title = "услуги",
//            input_type = 1,
//            base = RepositoryEnum.SAVE_IN_BASE,
//            class_entity = "com.example.lispelDoc2.models.Component")
    private ArrayList<Long> servicesIdList;
    //    @LispelAddValueByUser(number = 3,
//            name = "итоговая цена",
//            name_hint = "итоговая цена",
//            name_title = "итоговая цена",
//            input_type = 1,
//            base = RepositoryEnum.SAVE_IN_BASE,
//            class_entity = "com.example.lispelDoc2.models.OrderUnit")
    private Long finalPrice;

    public Order() {
        this.dateOfCreate = new Date();
        this.servicesIdList = new ArrayList<>();
    }

    public void addService (Long id) {
        this.servicesIdList.add(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ArrayList<Long> getServicesIdList() {
        return servicesIdList;
    }

    public void setServicesIdList(ArrayList<Long> servicesIdList) {
        this.servicesIdList = servicesIdList;
    }

    public Long getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Long finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }
}
