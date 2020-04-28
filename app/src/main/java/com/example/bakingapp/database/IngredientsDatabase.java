package com.example.bakingapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bakingapp.model.Ingredients;

@Database(entities = {Ingredients.class},version = 1, exportSchema = false)
public abstract class IngredientsDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "ingredient";
    private static IngredientsDatabase sInstance;

    public static IngredientsDatabase getInstance(Context context) {
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            IngredientsDatabase.class, IngredientsDatabase.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
            }
        }
        return sInstance;
    }

    public abstract IngredientsDao IngredientDao();
}
