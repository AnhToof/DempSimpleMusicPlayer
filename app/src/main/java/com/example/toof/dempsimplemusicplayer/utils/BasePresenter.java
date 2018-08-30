package com.example.toof.dempsimplemusicplayer.utils;

public interface BasePresenter<T> {
    void onStart();

    void onStop();

    void setView(T view);
}
