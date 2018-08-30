package com.example.toof.dempsimplemusicplayer.screen.main;

import android.content.Context;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.data.source.TrackRepository;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private TrackRepository mRepository;

    public MainPresenter(TrackRepository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public List<Track> getData(Context mContext) {
        List<Track> tracks;
        tracks = mRepository.getData(mContext);
        Collections.sort(tracks, new Comparator<Track>() {
            @Override
            public int compare(Track track, Track t1) {
                return track.getTrackName().compareTo(t1.getTrackName());
            }
        });
        return tracks;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }
}
