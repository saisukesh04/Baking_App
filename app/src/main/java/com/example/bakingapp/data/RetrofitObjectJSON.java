package com.example.bakingapp.data;

import com.example.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitObjectJSON {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipesJson();
}
