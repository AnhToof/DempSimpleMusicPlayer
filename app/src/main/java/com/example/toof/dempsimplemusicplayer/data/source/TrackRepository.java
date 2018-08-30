package com.example.toof.dempsimplemusicplayer.data.source;

import com.example.toof.dempsimplemusicplayer.data.model.Track;
import java.util.List;

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

    public List<Track> getData() {
        return mLocalDataSource.getData();
    }
}
