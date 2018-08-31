package com.example.toof.dempsimplemusicplayer.data.source.local;

import android.content.Context;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.data.source.TrackDataSource;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static TrackLocalDataSource sInstance;

    public static TrackLocalDataSource getsInstance() {
        if (sInstance == null) {
            sInstance = new TrackLocalDataSource();
        }
        return sInstance;
    }

    @Override
    public void getData(Context context, OnGetDataListener<Track> listener) {
        GetDataLocal getDataLocal = new GetDataLocal(context, listener);
        getDataLocal.getData();
    }
}
