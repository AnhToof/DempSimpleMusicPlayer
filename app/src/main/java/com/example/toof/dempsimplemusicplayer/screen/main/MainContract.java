package com.example.toof.dempsimplemusicplayer.screen.main;

import android.content.Context;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.utils.BasePresenter;
import java.util.List;

public interface MainContract {

    interface View {
        void setTotalDuration(int duration);

        void updateTimeTrack();
    }

    interface Presenter extends BasePresenter<View> {
        List<Track> getData(Context mContext);

    }
}
