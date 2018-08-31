package com.example.toof.dempsimplemusicplayer.screen.main;

import android.content.Context;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.data.source.TrackRepository;
import com.example.toof.dempsimplemusicplayer.data.source.local.OnGetDataListener;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private TrackRepository mRepository;
    private Context mContext;

    public MainPresenter(Context context, TrackRepository repository) {
        mContext = context;
        mRepository = repository;
    }

    @Override
    public void getData() {
        mRepository.getData(mContext, new OnGetDataListener<Track>() {
            @Override
            public void onSuccess(List<Track> list) {
                mView.onGetDataSuccess(list);
            }

            @Override
            public void onError(String error) {
                mView.onGetDataError(error);
            }
        });
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
