package com.example.lispelDoc2.dao.proxyDAO;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lispelDoc2.dao.StickerDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.Sticker;
import com.example.lispelDoc2.utilities.LiveDataListConverter;

import java.util.List;

public class StickerProxyDAO implements LispelDAO {
    private final StickerDAO stickerDAO;

    public StickerProxyDAO(Application application) {
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.stickerDAO = lispelRoomDatabase.stickerDAO();
    }

    @Override
    public Long insert(SavingObject savingObject) {
        return stickerDAO.insert((Sticker) savingObject);
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner) {
        return LiveDataListConverter.convert(owner, stickerDAO.getAllEntities());
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntitiesByProperty(LifecycleOwner owner, String s) {
        return LiveDataListConverter.convert(owner, stickerDAO.getAllEntitiesByName(s));
    }

    @Override
    public LiveData<? extends SavingObject> getEntityById(Long id) {
        return stickerDAO.getEntityById(id);
    }
}
