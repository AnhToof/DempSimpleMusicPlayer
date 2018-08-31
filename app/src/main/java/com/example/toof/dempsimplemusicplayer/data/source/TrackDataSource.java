package com.example.toof.dempsimplemusicplayer.data.source;

import android.content.Context;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.data.source.local.OnGetDataListener;

public interface TrackDataSource {
    interface LocalDataSource {
        void getData(Context context, OnGetDataListener<Track> listener);
    }
}
