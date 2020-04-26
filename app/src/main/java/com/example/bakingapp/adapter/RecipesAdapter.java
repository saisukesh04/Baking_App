package com.example.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.RecipeActivity;
import com.example.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> data;
    private Context context;

    public RecipesAdapter(Context context, List<Recipe> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.ViewHolder holder, int position) {

        final Recipe recipe = data.get(position);
        TypedArray recipeImages = context.getResources().obtainTypedArray(R.array.images);
        holder.recipeName.setText(recipe.getName());
        if(recipe.getImage().isEmpty()){
            Picasso.get().load(recipeImages.getResourceId(position,0)).into(holder.recipeImageView);
        }else{
            Picasso.get().load(recipe.getImage()).into(holder.recipeImageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recipeIntent = new Intent(context, RecipeActivity.class);
                recipeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                recipeIntent.putExtra("recipe", recipe);
                context.startActivity(recipeIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data != null? data.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView recipeImageView;
        TextView recipeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.recipeImageView);
            recipeName = itemView.findViewById(R.id.recipeName);
        }
    }
}
