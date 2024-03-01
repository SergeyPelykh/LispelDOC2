package com.example.lispelDoc2.utilities;

import com.example.lispelDoc2.models.Service;

import java.util.List;

public class ServicesMapItem {
    private String sticker;
    private List<Service> servicesList;

    public ServicesMapItem(String sticker, List<Service> servicesList) {
        this.sticker = sticker;
        this.servicesList = servicesList;
    }

    public String getSticker() {
        return sticker;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }

    public List<Service> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<Service> servicesList) {
        this.servicesList = servicesList;
    }
}
