package com.example.lispelDoc2.utilities;

import androidx.lifecycle.MutableLiveData;

import com.example.lispelDoc2.models.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapServicesToLIst {
    public static Map<String, List<Service>> dataMap;
    public static List<ServicesMapItem> getListFromMapServices(Map<String,List<Service>> dataMap) {
        ArrayList<ServicesMapItem> arrayList = new ArrayList<>();
        for (String sticker: dataMap.keySet()) {
            if (!dataMap.get(sticker).isEmpty()){
                arrayList.add(new ServicesMapItem(sticker, dataMap.get(sticker)));
            }

        }
        return arrayList;
    }

    public static List<ServicesMapItem> getNotEmptyListFromMapServices(Map<String,List<Service>> dataMap) {
        ArrayList<ServicesMapItem> arrayList = new ArrayList<>();
        for (String sticker: dataMap.keySet()) {
            if (dataMap.get(sticker) != null){
                arrayList.add(new ServicesMapItem(sticker, dataMap.get(sticker)));
            }

        }
        return arrayList;
    }
    public static boolean isEqualMaps ( Map<String,List<Service>> map1, Map<String,List<Service>> map2){
        if (map1.size() != map2.size()) {
            return false;
        }
        for (String key: map1.keySet()) {
            if (map1.get(key).size() != map2.get(key).size()) {
                return false;
            }
        }
        if (map1.keySet() != map2.keySet()) {
            return false;
        }
        return true;
    }
}
