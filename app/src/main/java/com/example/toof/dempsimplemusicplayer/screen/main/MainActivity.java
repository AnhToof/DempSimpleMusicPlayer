package com.example.toof.dempsimplemusicplayer.screen.main;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.toof.dempsimplemusicplayer.R;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.data.source.TrackRepository;
import com.example.toof.dempsimplemusicplayer.data.source.local.TrackLocalDataSource;
import com.example.toof.dempsimplemusicplayer.service.TrackService;
import com.example.toof.dempsimplemusicplayer.utils.Helper;
import com.example.toof.dempsimplemusicplayer.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MainContract.View, View.OnClickListener, OnItemRecyclerViewClickListener<Track>,
        SeekBar.OnSeekBarChangeListener {

    public static int MY_REQUEST_CODE_READ_STORAGE = 123;
    private ImageButton mButtonPlay;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrev;
    private SeekBar mSeekBar;
    private TextView mTextViewPlayingTime;
    private TextView mTextViewDuration;
    private RecyclerView mRecyclerView;
    private TrackService mTrackService;
    private List<Track> mTracks;
    private Intent mPlayIntent;
    private MainAdapter mAdapter;
    private boolean mIsMusicBound;
    private boolean isPause = false;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TrackService.TrackBinder binder = (TrackService.TrackBinder) iBinder;
            mTrackService = binder.getServices();
            mTrackService.setTrackList(mTracks);
            Toast.makeText(MainActivity.this, R.string.connection_connected, Toast.LENGTH_LONG)
                    .show();
            mIsMusicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIsMusicBound = false;
            Toast.makeText(MainActivity.this, R.string.connection_disconnected, Toast.LENGTH_LONG)
                    .show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermission();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_tracks);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MainAdapter(this);
        mAdapter.setOnItemRecyclerViewClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        mButtonNext = findViewById(R.id.button_next);
        mButtonPlay = findViewById(R.id.button_play_pause);
        mButtonPrev = findViewById(R.id.button_previous);
        mButtonPlay.setOnClickListener(this);
        mButtonPrev.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);

        mTextViewDuration = findViewById(R.id.text_duration);
        mTextViewPlayingTime = findViewById(R.id.text_playing_time);

        mSeekBar = findViewById(R.id.seek_duration);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void initData() {
        if (mPlayIntent == null) {
            mPlayIntent = new Intent(this, TrackService.class);
            bindService(mPlayIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }

        TrackLocalDataSource trackLocalDataSource = TrackLocalDataSource.getmInstance();
        TrackRepository trackRepository = TrackRepository.getmInstance(trackLocalDataSource);
        MainContract.Presenter presenter = new MainPresenter(trackRepository);
        presenter.setView(this);
        mTracks = new ArrayList<>();
        mTracks = presenter.getData(this);
        mAdapter.updateData(mTracks);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, MY_REQUEST_CODE_READ_STORAGE);
            } else {
                initData();
            }
        } else {
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case 007:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                    initData();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsMusicBound) {
            stopService(mPlayIntent);
            mTrackService = null;
        }
    }

    @Override
    public void setTotalDuration(int duration) {
        mTextViewDuration.setText(Helper.formatDate(duration, "mm:ss"));
        mSeekBar.setMax(duration);
    }

    @Override
    public void updateTimeTrack() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextViewPlayingTime.setText(
                        Helper.formatDate(mTrackService.getCurrentPos(), "mm:ss"));
                mSeekBar.setProgress(mTrackService.getCurrentPos());
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_play_pause:
                if (mTrackService.isPlaying()) {
                    mButtonPlay.setImageResource(R.drawable.ic_play);
                    mTrackService.pause();
                } else {
                    mButtonPlay.setImageResource(R.drawable.ic_pause);
                    mTrackService.play();
                }
                setTotalDuration(mTrackService.getDuration());
                updateTimeTrack();
                break;
            case R.id.button_next:
                mTrackService.next();
                setTotalDuration(mTrackService.getDuration());
                updateTimeTrack();
                break;
            case R.id.button_previous:
                mTrackService.previous();
                setTotalDuration(mTrackService.getDuration());
                updateTimeTrack();
                break;
        }
    }

    @Override
    public void onItemClickListener(Track item, int position) {
        mButtonPlay.setImageResource(R.drawable.ic_pause);
        mTrackService.setTrack(position);
        mTrackService.play();
        setTotalDuration(mTrackService.getDuration());
        updateTimeTrack();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mTrackService.seek(seekBar.getProgress());
    }
}
