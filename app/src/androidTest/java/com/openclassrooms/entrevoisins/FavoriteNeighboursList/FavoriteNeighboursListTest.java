package com.openclassrooms.entrevoisins.FavoriteNeighboursList;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class FavoriteNeighboursListTest {

    @Rule
    public ActivityScenarioRule<ListNeighbourActivity> mActivityRule =
            new ActivityScenarioRule<>(ListNeighbourActivity.class);

    @Test
    public void favoritesTab_shouldOnlyDisplayFavorites() {

        //Clic sur l'onglet des favoris
        onView(withContentDescription("Favorites"))
                .perform(click());

        //Vérifie que la liste des favoris est vide
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(0));

        //Clic sur l'onglet my neighbours
        onView(withContentDescription("My neighbours"))
                .perform(click());

        //Clic sur l'élément à la position 3 de la liste (par exemple)
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(3, click()));

        //Clic sur le bouton de mise en favori
        onView(withId(R.id.favoriteButton))
                .perform(click());

        //Retour à la liste générale des voisins
        pressBack();

        //Clic sur l'onglet des favoris
        onView(withContentDescription("Favorites"))
                .perform(click());

        //Vérifie que la liste des favoris contient un voisin (celui qui vient d'être mis en favori)
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(1));
    }
}

