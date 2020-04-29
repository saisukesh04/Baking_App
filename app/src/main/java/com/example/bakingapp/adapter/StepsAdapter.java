package com.example.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Steps;

import java.util.List;

import static com.example.bakingapp.RecipeStepFragment.replaceRecipeStep;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<Steps> stepsData;
    private Context context;

    public StepsAdapter(Context context, List<Steps> stepsData) {
        this.context = context;
        this.stepsData = stepsData;
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.description_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, final int position) {
        final Steps steps = stepsData.get(position);
        assert steps != null;
        holder.descriptionText.setText(steps.getShortDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceRecipeStep(((FragmentActivity) v.getContext()), position, stepsData, true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepsData != null? stepsData.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView descriptionText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionText = itemView.findViewById(R.id.descriptionText);
        }
    }
}
