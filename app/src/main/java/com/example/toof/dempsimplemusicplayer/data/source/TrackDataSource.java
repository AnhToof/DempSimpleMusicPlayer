package com.example.toof.dempsimplemusicplayer.data.source;

import com.example.toof.dempsimplemusicplayer.data.model.Track;
import java.util.List;

public interface TrackDataSource {
    interface LocalDataSource {
        List<Track> getData();
    }
}
