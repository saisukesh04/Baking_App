package com.example.bakingapp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.bakingapp.model.Ingredients;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private List<Ingredients> ingredients;

    public ViewModel(@NonNull Application application) {
        super(application);
        IngredientsDatabase ingredientsDatabase = IngredientsDatabase.getInstance(this.getApplication());
        ingredients = ingredientsDatabase.IngredientDao().loadAllIngredients();
    }

    public List<Ingredients> getIngredient() {
        return ingredients;
    }
}
