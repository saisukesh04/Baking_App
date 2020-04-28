package com.example.bakingapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bakingapp.model.Ingredients;

import java.util.List;

@Dao
public interface IngredientsDao {

    @Query("SELECT * FROM ingredient")
    List<Ingredients> loadAllIngredients();

    @Insert
    void insertIngredients(Ingredients ingredients);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateIngredients(Ingredients ingredients);

    @Query("DELETE FROM ingredient")
    void deleteAll();
}

