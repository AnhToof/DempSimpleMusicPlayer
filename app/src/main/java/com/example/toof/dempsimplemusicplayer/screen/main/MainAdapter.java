package com.example.toof.dempsimplemusicplayer.screen.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.toof.dempsimplemusicplayer.R;
import com.example.toof.dempsimplemusicplayer.data.model.Track;
import com.example.toof.dempsimplemusicplayer.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Track> mTracks;
    private Context mContext;
    private OnItemRecyclerViewClickListener<Track> mListener;

    public MainAdapter(Context context) {
        mContext = context;
        mTracks = new ArrayList<>();
    }

    public void setOnItemRecyclerViewClickListener(
            OnItemRecyclerViewClickListener<Track> onItemRecyclerViewClickListener) {
        mListener = onItemRecyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_layout_track, viewGroup, false);
        return new ViewHolder(mContext, view, mTracks, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindViewData(mTracks.get(i));
    }

    public void updateData(List<Track> tracks) {
        mTracks.clear();
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextViewTitle;
        private TextView mTextViewArtist;
        private TextView mTextViewDuration;
        private Context mContext;
        private List<Track> mTracks;
        private OnItemRecyclerViewClickListener<Track> mListener;

        public ViewHolder(Context context, View view, List<Track> tracks,
                OnItemRecyclerViewClickListener<Track> listener) {
            super(view);
            mContext = context;
            mListener = listener;
            mTracks = tracks;
            view.setOnClickListener(this);
            mTextViewTitle = view.findViewById(R.id.text_title);
            mTextViewArtist = view.findViewById(R.id.text_artist);
            mTextViewDuration = view.findViewById(R.id.text_duration);
        }

        public void bindViewData(Track track) {
            mTextViewTitle.setText(track.getTrackName());
            mTextViewArtist.setText(track.getArtist());
            mTextViewDuration.setText(track.getDuration());
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClickListener(mTracks.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
