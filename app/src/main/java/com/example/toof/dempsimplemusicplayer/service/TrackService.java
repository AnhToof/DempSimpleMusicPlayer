package com.example.toof.dempsimplemusicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackService extends Service
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private List<Track> mTracks = new ArrayList<>();
    private IBinder mIBinder = new TrackBinder();
    private int mPos = 0;
    private boolean mIsPause;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        initPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void initPlayer() {
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    public void setTrackList(List<Track> trackList) {
        mTracks = trackList;
    }

    public void setTrack(int pos) {
        mPos = pos;
    }

    public int getCurrentPos() {
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void seek(int currentPos) {
        mMediaPlayer.seekTo(currentPos);
    }

    public void play() {
        if (mIsPause) {
            mIsPause = false;
            mMediaPlayer.seekTo(getCurrentPos());
            mMediaPlayer.start();
        } else {
            mMediaPlayer.reset();
            try {
                mMediaPlayer.setDataSource(mTracks.get(mPos).getPath());
                mMediaPlayer.prepare();
            } catch (IOException e) {
                Log.e("MUSIC SERVICE", "Error setting data source", e);
            }
            mMediaPlayer.start();
        }
    }

    public void pause() {
        mIsPause = true;
        mMediaPlayer.pause();
    }

    public void next() {
        mPos++;
        if (mPos >= mTracks.size()) mPos = 0;
        play();
    }

    public void previous() {
        mPos--;
        if (mPos < 0) mPos = mTracks.size() - 1;
        play();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) mMediaPlayer.release();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (mMediaPlayer.getCurrentPosition() > 0) {
            mMediaPlayer.reset();
            next();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mMediaPlayer.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
    }

    public class TrackBinder extends Binder {
        public TrackService getServices() {
            return TrackService.this;
        }
    }
}
