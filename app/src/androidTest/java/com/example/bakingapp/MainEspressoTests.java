package com.example.bakingapp;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.bakingapp.RecipeActivity.DualPane;

@RunWith(AndroidJUnit4.class)
public class MainEspressoTests {

    Random rand = new Random();
    int random = rand.nextInt(4);

    @Rule
    public IntentsTestRule<MainActivity> mIntentRule = new IntentsTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = mIntentRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void testSelectItem() {
        onView(withId(R.id.recipeRecyclerView)).perform(actionOnItemAtPosition(random, click()));
        intended(hasComponent(RecipeActivity.class.getName()));

        ActivityTestRule<RecipeActivity> mActivityRule = new ActivityTestRule<>(RecipeActivity.class);
        onView(withId(R.id.recipe_steps_container)).check(matches(isDisplayed()));
        if (DualPane) {
            onView(withId(R.id.defaultIngredientsText)).perform(click());
            onView(withId(R.id.view_step_container)).check(matches(isDisplayed()));
        }
        onView(withId(R.id.stepsRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.stepsRecyclerView)).perform(actionOnItemAtPosition(random, click()));
        onView(withId(R.id.fullDescription)).check(matches(isDisplayed()));
    }

    @After
    public void unRegisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
