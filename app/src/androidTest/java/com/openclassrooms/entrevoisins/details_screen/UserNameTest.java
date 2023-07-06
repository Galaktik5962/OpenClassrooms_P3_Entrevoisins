package com.openclassrooms.entrevoisins.details_screen;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ViewDetailsNeighbourActivity;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

import android.content.Intent;

@RunWith(AndroidJUnit4.class)
public class UserNameTest {

    @Rule
    public ActivityScenarioRule<ViewDetailsNeighbourActivity> mActivityRule =
            new ActivityScenarioRule<>(ViewDetailsNeighbourActivity.class);

    @Test
    public void userDetailsTextView_shouldNotBeEmpty() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour neighbour = getRandomNeighbour(neighbours);

        mActivityRule.getScenario().onActivity(activity -> {
            Intent intent = new Intent(activity, ViewDetailsNeighbourActivity.class);
            intent.putExtra("neighbour", neighbour);
            activity.startActivity(intent);

            onView(withId(R.id.name1TextView))
                    .check(matches(not(withText(isEmptyOrNullString()))));

            onView(withId(R.id.name2TextView))
                    .check(matches(not(withText(isEmptyOrNullString()))));
        });
    }

    private Neighbour getRandomNeighbour(List<Neighbour> neighbours) {
        Random random = new Random();
        int index = random.nextInt(neighbours.size());
        return neighbours.get(index);
    }
}
