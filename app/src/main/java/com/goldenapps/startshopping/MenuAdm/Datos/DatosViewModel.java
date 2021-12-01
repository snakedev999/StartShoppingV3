package com.goldenapps.startshopping.MenuAdm.Datos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DatosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DatosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}