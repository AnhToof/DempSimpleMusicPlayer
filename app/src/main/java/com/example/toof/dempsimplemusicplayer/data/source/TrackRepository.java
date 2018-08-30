package com.example.toof.dempsimplemusicplayer.data.source;

import android.content.Context;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import java.util.List;

public class TrackRepository {
    private static TrackRepository mInstance;
    private TrackDataSource.LocalDataSource mLocalDataSource;

    public TrackRepository(TrackDataSource.LocalDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static TrackRepository getmInstance(TrackDataSource.LocalDataSource mLocalDataSource) {
        if (mInstance == null) {
            mInstance = new TrackRepository(mLocalDataSource);
        }
        return mInstance;
    }

    public List<Track> getData(Context mContext) {
        List<Track> tracks;
        tracks = mLocalDataSource.getData(mContext);
        return tracks;
    }
}
