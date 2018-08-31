package com.example.toof.dempsimplemusicplayer.data.source;

import android.content.Context;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.data.source.local.OnGetDataListener;

public class TrackRepository {
    private static TrackRepository sInstance;
    private TrackDataSource.LocalDataSource mLocalDataSource;

    private TrackRepository(TrackDataSource.LocalDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static TrackRepository getsInstance(TrackDataSource.LocalDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new TrackRepository(localDataSource);
        }
        return sInstance;
    }

    public void getData(Context context, OnGetDataListener<Track> listener) {
        mLocalDataSource.getData(context, listener);
    }
}
