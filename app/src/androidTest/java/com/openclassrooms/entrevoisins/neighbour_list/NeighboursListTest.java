
package com.openclassrooms.entrevoisins.neighbour_list;

import androidx.test.espresso.contrib.RecyclerViewActions;


import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ViewDetailsNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

import java.util.ArrayList;
import java.util.List;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    // Use of ActivityScenarioRule recommended in recent versions of the espresso framework.
    public ActivityScenarioRule<ListNeighbourActivity> mActivityRule =
            new ActivityScenarioRule(ListNeighbourActivity.class);

    @Before
    // Changes made to be able to get activity from ActivityScenarioRule.
    public void setUp() {
        mActivityRule.getScenario().onActivity(activity -> {
            mActivity = activity;
            assertThat(mActivity, notNullValue());
        });
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.list_neighbours));

        // Allows to target only the view displayed on the screen (because I use the same view for my 2 fragments).
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
}

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {

        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));

        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we click on an item, the details screen is launched
     */

    @Before
    public void setup() {

        // Initialization of intents.
        Intents.init();
    }

    @After
    public void cleanup() {

        // Closing intents.
        Intents.release();
    }
    @Test
    public void clickNeighbourItem_shouldLaunchViewDetailsActivity() {

        // Click on the first item in the list of neighbors.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the details screen is launched.
        intended(hasComponent(ViewDetailsNeighbourActivity.class.getName()));
    }

     /**
      * When we click on an item, the details screen is launched and the neighbour is not null
      */

    @Test
    public void viewDetailsActivity_neighbourNotNull() {

        // Nom attendu du voisin
        String expectedName = "Caroline";

        // Click on the first item in the list of neighbors.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the details screen is launched.
        intended(hasComponent(ViewDetailsNeighbourActivity.class.getName()));

        // Vérifiez que les TextView contiennent le nom du voisin attendu
        onView(withId(R.id.name1TextView))
                .check(matches(withText(expectedName)));

        onView(withId(R.id.name2TextView))
                .check(matches(withText(expectedName)));
    }

    /**
     * When you put a neighbor in favorite, it is displayed in the favorites tab
     */
    @Test
    public void favoritesTab_shouldOnlyDisplayFavorites() {

        // Click on the favorites tab.
        onView(withContentDescription("Favorites"))
                .perform(click());

        // Check that the favorites list is empty.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(0));

        // Click on the my neighbors tab.
        onView(withContentDescription("My neighbours"))
                .perform(click());

        // Click on the element at position 3 of the list (for example).
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(3, click()));

        // Click on the favorite button.
        onView(withId(R.id.favoriteButton))
                .perform(click());

        // Back to the general list of neighbors.
        pressBack();

        // Click on the favorites tab.
        onView(withContentDescription("Favorites"))
                .perform(click());

        // Checks that the list of favorites contains a neighbor (the one that has just been favorited).
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(1));
    }
}