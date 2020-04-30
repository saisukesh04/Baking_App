package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bakingapp.adapter.RecipesAdapter;
import com.example.bakingapp.data.RetrofitObjectJSON;
import com.example.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recipeRecyclerView;
    private static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recipeRecyclerView.setLayoutManager(gridLayoutManager);

        getRetrofitData();
    }

    private void getRetrofitData() {

        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitObjectJSON service = retrofit.create(RetrofitObjectJSON.class);
            Call<List<Recipe>> call = service.getRecipesJson();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    List<Recipe> recipes = response.body();
                    recipeRecyclerView.setAdapter(new RecipesAdapter(getApplicationContext(), recipes));
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.i("Info: ", t.getMessage());
                }
            });
        }else{
            Toast.makeText(this, "Please check your INTERNET connection!", Toast.LENGTH_LONG).show();
        }
    }
}
