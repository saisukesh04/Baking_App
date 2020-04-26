package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.bakingapp.model.Ingredients;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.Steps;

import java.io.Serializable;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    static List<Steps> steps;
    static List<Ingredients> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Log.i("Info: ","Reached Recipe Activity.");
        Intent adapterIntent = getIntent();
        Recipe recipe = (Recipe) adapterIntent.getSerializableExtra("recipe");

        assert recipe != null;
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();

        ViewStepsFragment viewStepsFragment = new ViewStepsFragment();
        FragmentManager viewStepsManager = getSupportFragmentManager();
        viewStepsManager.beginTransaction().add(R.id.recipe_steps_container, viewStepsFragment).commit();

    }
}
