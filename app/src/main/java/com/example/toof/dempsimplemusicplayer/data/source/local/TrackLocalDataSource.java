package com.example.toof.dempsimplemusicplayer.data.source.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import com.example.toof.dempsimplemusicplayer.R;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.data.source.TrackDataSource;
import java.util.ArrayList;
import java.util.List;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static TrackLocalDataSource mInstance;

    public static TrackLocalDataSource getmInstance() {
        if (mInstance == null) mInstance = new TrackLocalDataSource();
        return mInstance;
    }

    @Override
    public List<Track> getData(Context mContext) {
        List<Track> tracks = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Toast.makeText(mContext, R.string.load_file_error, Toast.LENGTH_LONG).show();
        } else if (!cursor.moveToFirst()) {
            Toast.makeText(mContext, R.string.data_not_found, Toast.LENGTH_LONG).show();
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
        return tracks;
    }
}
