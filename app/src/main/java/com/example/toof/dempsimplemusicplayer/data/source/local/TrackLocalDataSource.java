package com.example.toof.dempsimplemusicplayer.data.source.local;

import android.content.Context;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.data.source.TrackDataSource;
import java.util.List;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static TrackLocalDataSource sInstance;
    private DataLocalDataSource mDataLocalDataSource;

    private TrackLocalDataSource(Context context) {
        mDataLocalDataSource = new GetDataLocal(context);
    }

    public static TrackLocalDataSource getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TrackLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public List<Track> getData() {
        return mDataLocalDataSource.getListTrackLocal();
    }
}
