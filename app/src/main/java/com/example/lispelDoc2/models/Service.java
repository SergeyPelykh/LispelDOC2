package com.example.lispelDoc2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lispelDoc2.dao.proxyDAO.ServiceProxyDAO;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.RepositoryEnum;
import com.example.lispelDoc2.interfaces.SavingObject;

import java.util.ArrayList;
import java.util.Date;
@LispelCreateRepository(
        dao = ServiceProxyDAO.class,
        entity = com.example.lispelDoc2.models.Service.class)
@Entity(tableName = "service_table")
public class Service implements SavingObject {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Date dateOfCreate;

    @LispelAddValueByUser(number = 1,
            name = "название",
            name_hint = "название",
            name_title = "название",
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.StringValue",
            repository_title = "ServiceName",
            input_type = 1)
    private String name;
    @LispelAddValueByUser(number = 2,
            name = "printUnit",
            name_hint = "printUnit",
            name_title = "printUnit",
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.PrintUnit",
            input_type = 1)
    private String orderUnitSticker;
    @LispelAddValueByUser(number = 3,
            name = "деталь",
            name_hint = "деталь",
            name_title = "деталь",
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.Component",
            input_type = 1)
    private String componentName;
    @LispelAddValueByUser(number = 4,
            name = "количество",
            name_hint = "количество",
            name_title = "количество",
            input_type = 1)
    private Integer amount;
    @LispelAddValueByUser(number = 5,
            name = "стоимость",
            name_hint = "стоимость",
            name_title = "стоимость",
            input_type = 3)
    private Integer price;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return this.name + " " + this.orderUnitSticker;
    }

    public Service() {
    }

    public Service(ArrayList<String> arr) {
        this.dateOfCreate = new Date();
        this.name = arr.get(0);
        this.orderUnitSticker = arr.get(1);
        this.componentName = arr.get(2);
        this.amount = Integer.parseInt(arr.get(3));
        this.price = Integer.parseInt(arr.get(4));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderUnitSticker() {
        return orderUnitSticker;
    }

    public void setOrderUnitSticker(String orderUnitSticker) {
        this.orderUnitSticker = orderUnitSticker;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
