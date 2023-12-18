package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import com.openclassrooms.entrevoisins.databinding.ViewNeighbourDetailsBinding;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

/**
 * Activity to display detailed information about a specific neighbor.
 * Allows the user to view neighbor details, such as name, avatar, address, phone number, and about me text.
 * The activity also provides a button to mark the neighbor as a favorite and navigate back to the previous screen.
 */
public class ViewDetailsNeighbourActivity extends AppCompatActivity {

    private NeighbourApiService neighbourApiService;

    public Neighbour neighbour;
    private boolean isFavorite;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the neighbor API service
        neighbourApiService = DI.getNeighbourApiService();

        // Using ViewBinding to inflate the layout
        ViewNeighbourDetailsBinding binding = ViewNeighbourDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve the selected neighbor from the Intent
        Intent intent = getIntent();
        Neighbour neighbour = (Neighbour) intent.getSerializableExtra("neighbour");

        if (neighbour != null) {

            // Retrieve neighbor data
            String name = neighbour.getName();
            String avatarUrl = neighbour.getAvatarUrl();
            String address = neighbour.getAddress();
            String phoneNumber = neighbour.getPhoneNumber();
            String aboutMe = neighbour.getAboutMe();

            // Display neighbor data
            Glide.with(this)
                    .load(avatarUrl)
                    .into(binding.avatarUrlImageView);

            binding.name1TextView.setText(name);
            binding.name2TextView.setText(name);
            binding.addressTextView.setText(address);
            binding.phoneNumberTextView.setText(phoneNumber);
            binding.aboutMeTextView.setText(aboutMe);

            // Associate neighbor name with facebook url
            String facebookUrl = "https://www.facebook.com/%s";
            String facebookUrlWithNeighbourName = String.format(facebookUrl, name);
            binding.socialNetwork.setText(facebookUrlWithNeighbourName);
        }

        // Configure the back button
        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Finish the activity to return to the previous screen
                finish();
            }
        });

        // Configuring Views
        ImageButton favoriteButton = binding.favoriteButton;

        // Retrieve and set the initial favorite status
        isFavorite = neighbour.isFavorite();
        setFavoriteButtonAppearance(favoriteButton, isFavorite);

        // Configuring the click listener for the favorite button
        binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the favorite status and update the UI
                isFavorite = !isFavorite;
                setFavoriteButtonAppearance(favoriteButton, isFavorite);
                neighbour.setFavorite(isFavorite);

                // Update the list of favorite neighbors in the API service
                List<Neighbour> favoriteNeighbours = neighbourApiService.getFavoriteNeighbours();
                if (isFavorite) {

                    neighbourApiService.addFavoriteNeighbour(neighbour);
                    neighbourApiService.setFavoriteNeighbours(favoriteNeighbours);

                } else {

                    neighbourApiService.removeFavoriteNeighbour(neighbour);
                    neighbourApiService.setFavoriteNeighbours(favoriteNeighbours);

                }

                setFavoriteButtonAppearance(favoriteButton, isFavorite);

            }
        });
    }

    /**
     * Sets the appearance of the favorite button based on the favorite status.
     *
     * @param button     The ImageButton for marking the neighbor as a favorite.
     * @param isFavorite A boolean indicating whether the neighbor is marked as a favorite.
     */
    private void setFavoriteButtonAppearance(ImageButton button, boolean isFavorite) {
        if (isFavorite) {
            // Set the color filter to indicate that the neighbor is a favorite
            int color = ContextCompat.getColor(this, R.color.favorite_yellow);
            button.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            // Clear the color filter when the neighbor is not a favorite
            button.clearColorFilter();
        }
    }
}
