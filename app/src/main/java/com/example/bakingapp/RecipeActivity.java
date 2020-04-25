package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ViewStepsFragment viewStepsFragment = new ViewStepsFragment();
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        FragmentManager stepsFragment = getSupportFragmentManager();
        stepsFragment.beginTransaction().add(R.id.recipe_steps_container,viewStepsFragment).commit();
        FragmentManager detailFragment = getSupportFragmentManager();
        detailFragment.beginTransaction().add(R.id.view_step_container,recipeStepFragment).commit();
    }
}
