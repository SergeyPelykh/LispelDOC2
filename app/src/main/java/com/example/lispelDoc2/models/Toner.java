package com.example.lispelDoc2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lispelDoc2.dao.proxyDAO.TonerProxyDAO;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.RepositoryEnum;
import com.example.lispelDoc2.interfaces.SavingObject;

import java.util.ArrayList;

@LispelCreateRepository(
        dao = TonerProxyDAO.class,
        entity = com.example.lispelDoc2.models.Toner.class)
@Entity(tableName = "toner_table")
public class Toner implements SavingObject {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @LispelAddValueByUser(number = 1,
            name = "название",
            name_hint = "название",
            name_title = "название",
            input_type = 1)
    private String name;
    @LispelAddValueByUser(number = 2,
            name = "полное название",
            name_hint = "полное название",
            name_title = "полное название",
            input_type = 1)
    private String fullName;
    @LispelAddValueByUser(number = 3,
            name = "производитель",
            name_hint = "производитель",
            name_title = "производитель",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispeldoc2.models.StringValue",
            repository_title= "tonerVendor")
    private String vendor;
    @LispelAddValueByUser(number = 4,
            name = "поставщик",
            name_hint = "поставщик",
            name_title = "поставщик",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispeldoc2.models.StringValue",
            repository_title= "provider")
    private String provider;
    @LispelAddValueByUser(number = 5,
            name = "совместимые картриджи",
            name_hint = "совместимые картриджи",
            name_title = "совместимые картриджи",
            input_type = 1)
    private String compatibility;

    @LispelAddValueByUser(number = 6,
            name = "вес упаковки",
            name_hint = "вес упаковки",
            name_title = "вес упаковки",
            input_type = 1)
    private long weight;


    public Toner() {
    }


    public Toner(ArrayList<String>arr) {
        this.name = arr.get(0);
        this.fullName = arr.get(1);
        this.vendor = arr.get(2);
        this.provider = arr.get(3);
        this.compatibility = arr.get(4);
        this.weight = Long.parseLong(arr.get(5));
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getDescription() {
        return this.name;
    }

}
