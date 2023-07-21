package com.openclassrooms.entrevoisins.details_screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;



import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ViewDetailsNeighbourActivity;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DetailsScreenTest {

    @Rule
    public ActivityScenarioRule<ListNeighbourActivity> mActivityRule =
            new ActivityScenarioRule<>(ListNeighbourActivity.class);

    @Before
    public void setup() {
        // Initialisation des intents
        Intents.init();
    }

    @After
    public void cleanup() {
        // Fermeture des intents
        Intents.release();
    }

    @Test
    public void clickNeighbourItem_shouldLaunchViewDetailsActivity() {
        // Clique sur le premier élément de la liste des voisins
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Vérifie que l'écran de détails est lancé
        intended(hasComponent(ViewDetailsNeighbourActivity.class.getName()));
    }

}