package com.example.lispelDoc2.models;

import android.app.Application;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.lispelDoc2.dao.proxyDAO.ClientProxyDAO;
import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.LispelCreateRepository;
import com.example.lispelDoc2.interfaces.Repository;
import com.example.lispelDoc2.interfaces.RepositoryEnum;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.repository.ClientRepository;
import com.example.lispelDoc2.utilities.Convert;

import java.util.ArrayList;

@LispelCreateRepository(
        dao = ClientProxyDAO.class,
        entity = com.example.lispelDoc2.models.Client.class
)
@Entity(tableName = "client_table",
        indices = {@Index(value = {"name"}, unique = true)})

public class Client implements SavingObject {
    public Repository getRepository(Application application, String title) {
        return new ClientRepository(application);
    }

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @LispelAddValueByUser(
            number = 1,
            name = "name",
            name_hint = "имя",
            name_title = "имя",
            input_type = 1)
    private String name;
    @LispelAddValueByUser(
            number = 2,
            name = "full name",
            name_hint = "полное имя",
            name_title = "полное имя",
            input_type = 1)
    private String fullName;
    @LispelAddValueByUser(number = 3,
            name = "address",
            name_title = "адрес",
            name_hint = "адрес",
            base = RepositoryEnum.READ_FROM_BASE_AND_EDIT,
            class_entity = "com.example.lispelDoc2.models.StringValue",
            repository_title = "street",
            input_type = 1)
    private String address;
    @LispelAddValueByUser(number = 4,
            name = "phone",
            name_hint = "телефон",
            name_title = "телефон",
            input_type = 3)
    private String phone;
    @LispelAddValueByUser(number = 5,
            name = "client type",
            name_hint = "тип клиента",
            name_title = "тип клиента",
            input_type = 8192,
            base = RepositoryEnum.READ_FROM_BASE,
            class_entity = "com.example.lispelDoc2.models.StringValue",
            repository_title = "clientType")
    private String type;
    @TypeConverters({Convert.class})
    @LispelAddValueByUser(number = 6,
            name = "номер стикера",
            name_hint = "номер стикера",
            name_title = "номер стикера",
            base = RepositoryEnum.SAVE_IN_BASE_AND_NOT_TO_DISPLAY,
            class_entity = "com.example.lispelDoc2.models.Sticker",
            input_type = 1)
    private ArrayList<String> numbers;

    public Client() {
    }

    public Client(ArrayList<String> arr) {

        this.name = arr.get(0);
        this.fullName = arr.get(1);
        this.address = arr.get(2);
        this.phone = arr.get(3);
        this.type = arr.get(4);
        addNumber(arr.get(5));
    }

    public SavingObject createEntityFromList(ArrayList<String> arr) {
        Client client = new Client();
        client.name = arr.get(0);
        client.fullName = arr.get(1);
        client.address = arr.get(2);
        client.phone = arr.get(3);
        client.type = arr.get(4);
        return client;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return name + " " + numbers;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<String> numbers) {
        this.numbers = numbers;
    }

    public void addNumber(String number) {
        if (this.numbers == null) {
            this.numbers = new ArrayList<String>();
        }
        this.numbers.add(number);
    }
}
