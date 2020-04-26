package com.example.bakingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakingapp.adapter.StepsAdapter;

import static com.example.bakingapp.RecipeActivity.steps;

public class ViewStepsFragment extends Fragment {

    RecyclerView stepsRecyclerView;

    public ViewStepsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_steps, container, false);

        stepsRecyclerView = view.findViewById(R.id.stepsRecyclerView);
        TextView defaultIngredientsText = view.findViewById(R.id.defaultIngredientsText);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(new StepsAdapter(getActivity(),steps));

        defaultIngredientsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Page Under Construction",Toast.LENGTH_SHORT).show();
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                replaceFragment(ingredientsFragment);
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager viewStepsManager = getActivity().getSupportFragmentManager();
        viewStepsManager.beginTransaction().replace(R.id.recipe_steps_container, fragment).addToBackStack("my_Fragment").commit();
    }
}
