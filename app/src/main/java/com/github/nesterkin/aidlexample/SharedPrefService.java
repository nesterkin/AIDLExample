package com.github.nesterkin.aidlexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * @author Nesterkin Alexander on 02.07.2018
 */
public class SharedPrefService extends Service {

    private static final String PREF_NAME = "pref_name";
    private static final String PREF_KEY = "pref_key";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IDataInterface.Stub() {
            @Override
            public String getData() throws RemoteException {
                return getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(PREF_KEY, "def value");
            }

            @Override
            public void setData(String data) throws RemoteException {
                getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(PREF_KEY, data).apply();
            }
        };
    }
}