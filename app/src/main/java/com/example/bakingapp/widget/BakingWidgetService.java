package com.example.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.bakingapp.R;
import com.example.bakingapp.database.IngredientsDatabase;
import com.example.bakingapp.model.Ingredients;

import java.util.ArrayList;
import java.util.List;

public class BakingWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetViewsFactory(getApplicationContext(), intent);
    }

    class BakingWidgetViewsFactory implements RemoteViewsFactory {

        private Context context;
        private int appWidgetId;
        private List<Ingredients> ingredientsList = new ArrayList<>();
        IngredientsDatabase mDatabase;


        public BakingWidgetViewsFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            mDatabase = IngredientsDatabase.getInstance(context);
            ingredientsList.clear();
            List<Ingredients> ingredients = mDatabase.IngredientDao().loadAllIngredients();
            ingredientsList.addAll(ingredients);
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientsList != null? ingredientsList.size(): 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_row_item);
            Ingredients ingredient = ingredientsList.get(position);
            remoteView.setTextViewText(R.id.widget_ingredient_name, ingredient.getIngredient());
            remoteView.setTextViewText(R.id.widget_ingredient_quantity, ingredient.getQuantity() + ingredient.getMeasure());
            return remoteView;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
