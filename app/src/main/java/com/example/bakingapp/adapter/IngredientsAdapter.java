package com.example.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredients;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<Ingredients> ingredientsData;
    private Context context;

    public IngredientsAdapter(Context context, List<Ingredients> ingredientsData) {
        this.context = context;
        this.ingredientsData = ingredientsData;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredients_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        Ingredients ingredients = ingredientsData.get(position);
        holder.ingredientText.setText(ingredients.getIngredient());
        String quantity = ingredients.getQuantity() + " " + ingredients.getMeasure();
        holder.quantityText.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return ingredientsData != null? ingredientsData.size(): 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder{

        TextView ingredientText, quantityText;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientText = itemView.findViewById(R.id.ingredientText);
            quantityText = itemView.findViewById(R.id.quantityText);
        }
    }
}