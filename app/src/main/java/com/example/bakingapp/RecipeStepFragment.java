package com.example.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.bakingapp.model.Steps;
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
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import static com.example.bakingapp.RecipeActivity.DualPane;

public class RecipeStepFragment extends Fragment {

    static SimpleExoPlayer exoPlayer;
    private List<Steps> steps;
    private int position;
    static String videoURL;

    public RecipeStepFragment(List<Steps> steps, int position) {
        this.position = position;
        this.steps = steps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        Button prevRecipeBtn = view.findViewById(R.id.prevRecipeBtn);
        final Button nextRecipeBtn = view.findViewById(R.id.nextRecipeBtn);

        TextView fullDescription = view.findViewById(R.id.fullDescription);
        final PlayerView exoPlayerView = view.findViewById(R.id.exoPlayerView);

        Log.i("Info: ", "Screen Changed");

        if(position == steps.size()-1){
            prevRecipeBtn.setEnabled(true);
            nextRecipeBtn.setEnabled(false);
        }else if(position == 0){
            prevRecipeBtn.setEnabled(false);
            nextRecipeBtn.setEnabled(true);
        }else{
            prevRecipeBtn.setEnabled(true);
            nextRecipeBtn.setEnabled(true);
        }

        videoURL = steps.get(position).getVideoURL();
        fullDescription.setText(steps.get(position).getDescription());

        if(!videoURL.isEmpty()) {
            Uri videoUrl = Uri.parse(videoURL);

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
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

        prevRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceRecipeStep(getActivity(),position - 1, steps, false);
                if(!videoURL.isEmpty()) {
                    exoPlayer.setPlayWhenReady(false);
                }
            }
        });

        nextRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceRecipeStep(getActivity(),position + 1, steps, false);
                if(!videoURL.isEmpty()) {
                    exoPlayer.setPlayWhenReady(false);
                }
            }
        });

        return view;
    }

    public static void replaceRecipeStep(FragmentActivity context, int position, List<Steps> stepsData, boolean addToBackStack) {
        if (DualPane) {
            FragmentManager viewStepsManager = context.getSupportFragmentManager();
            viewStepsManager.beginTransaction().replace(R.id.view_step_container, new RecipeStepFragment(stepsData, position)).commit();
        }else{
            FragmentManager viewStepsManager = context.getSupportFragmentManager();
            if(addToBackStack) {
                viewStepsManager.beginTransaction().replace(R.id.recipe_steps_container, new RecipeStepFragment(stepsData, position)).addToBackStack("my_Fragment").commit();
            }else{
                viewStepsManager.beginTransaction().replace(R.id.recipe_steps_container, new RecipeStepFragment(stepsData, position)).commit();
            }
        }
    }

}
