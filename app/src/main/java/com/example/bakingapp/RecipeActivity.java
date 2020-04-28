package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bakingapp.model.Ingredients;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.Steps;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    static List<Steps> steps;
    public static List<Ingredients> ingredients;
    FragmentManager viewStepsManager;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Toast.makeText(this, "Large screen",Toast.LENGTH_LONG).show();
        }

        Log.i("Info: ","Activity Created");
        Intent adapterIntent = getIntent();
        Recipe recipe = (Recipe) adapterIntent.getSerializableExtra("recipe");

        assert recipe != null;
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();

        ViewStepsFragment viewStepsFragment = new ViewStepsFragment();
        replaceFragment(viewStepsFragment);
    }

    private void replaceFragment(Fragment fragment) {

        viewStepsManager = getSupportFragmentManager();
        viewStepsManager.beginTransaction().add(R.id.recipe_steps_container, fragment).addToBackStack("my_Fragment").commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(viewStepsManager.getBackStackEntryCount() == 0){
            finish();
        }
    }
}
