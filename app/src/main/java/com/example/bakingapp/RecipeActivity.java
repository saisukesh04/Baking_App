package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.bakingapp.database.IngredientsDatabase;
import com.example.bakingapp.model.Ingredients;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.Steps;
import com.example.bakingapp.widget.BakingWidget;

import java.util.List;

import static com.example.bakingapp.RecipeStepFragment.exoPlayer;
import static com.example.bakingapp.RecipeStepFragment.videoURL;

public class RecipeActivity extends AppCompatActivity {

    public static boolean DualPane;
    static List<Steps> steps;
    public static List<Ingredients> ingredients;
    FragmentManager viewStepsManager;
    IngredientsDatabase mDatabase;
    private Recipe recipe;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if(findViewById(R.id.view_step_container) == null){
            DualPane = false;
        }else{
            DualPane = true;
        }

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            DualPane = true;
        }

        mDatabase = IngredientsDatabase.getInstance(getApplicationContext());

        Intent adapterIntent = getIntent();
        recipe = (Recipe) adapterIntent.getSerializableExtra("recipe");

        assert recipe != null;
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();

        refreshWidgets();

        ViewStepsFragment viewStepsFragment = new ViewStepsFragment();
        replaceFragment(viewStepsFragment);
    }

    private void refreshWidgets() {

        mDatabase.IngredientDao().deleteAll();
        for (int i = 0; i < ingredients.size(); i++) {
            mDatabase.IngredientDao().insertIngredients(ingredients.get(i));
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplication());
        int ids[] = appWidgetManager.getAppWidgetIds(new ComponentName(getApplication(), BakingWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.list_view_widget);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.baking_widget);
        remoteViews.setTextViewText(R.id.appwidget_text, recipe.getName());
        appWidgetManager.partiallyUpdateAppWidget(ids, remoteViews);
    }

    private void replaceFragment(Fragment fragment) {

        viewStepsManager = getSupportFragmentManager();
        viewStepsManager.beginTransaction().add(R.id.recipe_steps_container, fragment).addToBackStack("my_Fragment").commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(videoURL!=null) {
            if (!videoURL.isEmpty()) {
                exoPlayer.setPlayWhenReady(false);
            }
        }
        if(viewStepsManager.getBackStackEntryCount() == 0){
            finish();
        }
    }
}
