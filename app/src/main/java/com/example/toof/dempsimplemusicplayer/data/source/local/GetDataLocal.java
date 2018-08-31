package com.example.toof.dempsimplemusicplayer.data.source.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.example.toof.dempsimplemusicplayer.R;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GetDataLocal {
    private Context mContext;
    private OnGetDataListener<Track> mListener;

    GetDataLocal(Context context, OnGetDataListener<Track> listener) {
        mContext = context;
        mListener = listener;
    }

    public void getData() {
        List<Track> tracks = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            mListener.onError(mContext.getString(R.string.load_file_error));
        } else if (!cursor.moveToFirst()) {
            mListener.onError(mContext.getString(R.string.data_not_found));
        } else {
            do {
                String title = cursor.getString(
                        cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String artist =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String duration =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                Track track = new Track(title, artist, url, duration);
                tracks.add(track);
            } while (cursor.moveToNext());
            cursor.close();
        }
        Collections.sort(tracks, new Comparator<Track>() {
            @Override
            public int compare(Track track, Track t1) {
                return track.getTrackName().compareTo(t1.getTrackName());
            }
        });
        mListener.onSuccess(tracks);
    }



}
