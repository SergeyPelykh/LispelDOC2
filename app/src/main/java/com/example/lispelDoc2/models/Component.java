package com.example.lispelDoc2.models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.lispelDoc2.dao.proxyDAO.ComponentProxyDAO;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.RepositoryEnum;
import com.example.lispelDoc2.interfaces.SavingObject;

import java.util.ArrayList;

@LispelCreateRepository(
        dao = ComponentProxyDAO.class,
        entity = com.example.lispelDoc2.models.Component.class)
@Entity(tableName = "component_table",
        indices = {@Index(value = {"aliasName"}, unique = true)})
public class Component implements SavingObject {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @LispelAddValueByUser(
            number = 1,
            name = "название компонента",
            name_hint = "название компонента",
            name_title = "название компонента",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.StringValue",
            repository_title = "компонент")
    private String componentName;
    @LispelAddValueByUser(
            number = 2,
            name = "имя",
            name_hint = "имя",
            name_title = "имя",
            input_type = 1)
    private String aliasName;
    @LispelAddValueByUser(
            number = 3,
            name = "полное имя",
            name_hint = "полное имя",
            name_title = "полное имя",
            input_type = 1)
    private String fullName;
    @LispelAddValueByUser(
            number = 4,
            name = "производитель",
            name_hint = "производитель",
            name_title = "производитель",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.StringValue",
            repository_title = "vendor")
    private String vendor;
    @LispelAddValueByUser(
            number = 5,
            name = "поставщик",
            name_hint = "поставщик",
            name_title = "поставщик",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.StringValue",
            repository_title = "provider")
    private String provider;
    @LispelAddValueByUser(
            number = 6,
            name = "совместимые модели",
            name_hint = "совместимые модели",
            name_title = "совместимые модели",
            input_type = 1)
    private String compatibility;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return componentName + " " + aliasName;
    }

    @Override
    public void setTitle(String title) {
        this.componentName = title;
    }


    public Component() {
    }

    public Component(ArrayList<String> arr){
        this.componentName = arr.get(0);
        this.aliasName = arr.get(1);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }
}

