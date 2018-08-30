package com.example.toof.dempsimplemusicplayer.data.model;

public class Track {
    private String mTrackName;
    private String mArtist;
    private String mPath;
    private String mDuration;

    public Track(String trackName, String artist, String path, String duration) {
        mTrackName = trackName;
        mArtist = artist;
        mPath = path;
        mDuration = duration;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String trackName) {
        mTrackName = trackName;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }
}
