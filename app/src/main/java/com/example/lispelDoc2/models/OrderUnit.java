package com.example.lispelDoc2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lispelDoc2.dao.proxyDAO.OrderUnitProxyDAO;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.RepositoryEnum;
import com.example.lispelDoc2.interfaces.SavingObject;

import java.util.ArrayList;

@Entity(tableName = "orderUnit_table")
@LispelCreateRepository(
        dao = OrderUnitProxyDAO.class,
        entity = com.example.lispelDoc2.models.OrderUnit.class)
public class OrderUnit implements SavingObject {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @LispelAddValueByUser(number = 1,
            name = "устройство",
            name_hint = "устройство",
            name_title = "устройство",
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.PrintUnit",
            input_type = 1)
    private String printUnitName;
    @LispelAddValueByUser(number = 2,
            name = "номер стикера",
            name_hint = "номер стикера",
            name_title = "номер стикера",
            base = RepositoryEnum.SAVE_IN_BASE_AND_NOT_TO_DISPLAY,
            class_entity = "com.example.lispelDoc2.models.Sticker",
            input_type = 1)
    private String stickerNumber;

    @LispelAddValueByUser(number = 3,
            name = "владелец",
            name_hint = "владелец",
            name_title = "владелец",
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.Client",
            input_type = 1)
    private String ownerName;

    public OrderUnit() {
    }

    public OrderUnit(ArrayList<String> arr) {
        this.printUnitName = arr.get(0);
        this.stickerNumber = arr.get(1);
        this.ownerName = arr.get(2);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrintUnitName() {
        return printUnitName;
    }

    public void setPrintUnitName(String printUnitName) {
        this.printUnitName = printUnitName;
    }

    public String getStickerNumber() {
        return stickerNumber;
    }

    public void setStickerNumber(String stickerNumber) {
        this.stickerNumber = stickerNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getDescription() {
        return this.stickerNumber;
    }

}
