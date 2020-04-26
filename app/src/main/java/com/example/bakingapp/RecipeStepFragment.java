package com.example.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeStepFragment extends Fragment {

    PlayerView exoPlayerView;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        TextView fullDescription = view.findViewById(R.id.fullDescription);
        exoPlayerView = view.findViewById(R.id.exoPlayerView);

        fullDescription.setText(getArguments().getString("Description"));
        String videoURL = getArguments().getString("URL");

        if(!videoURL.isEmpty()) {
            Uri videoUrl = Uri.parse(getArguments().getString("URL"));

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            SimpleExoPlayer exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "RecipeVideo");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoUrl, dataSourceFactory, extractorsFactory, null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }else{
            ImageView videoErrorImage = view.findViewById(R.id.videoErrorImage);
            videoErrorImage.setVisibility(View.VISIBLE);
            exoPlayerView.setVisibility(View.GONE);
        }

        return view;
    }
}
